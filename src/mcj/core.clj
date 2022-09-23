(ns mcj.core
  (:gen-class)
  (:require [cats.core :as c]
            [cats.monad.either :as e]
            [clojure.string :as s]))

(def ^:private operations #{:add :sub :mul :div})

(defn- parse-arg [arg-str] (try (e/right (Double/parseDouble arg-str))
                                (catch NumberFormatException _e
                                  (e/left (str "Invalid argument " arg-str)))))

(defn get-command
  [op-str arg1-str arg2-str]
  (let [op (keyword op-str)]
    (if (contains? operations op)
      (if (some s/blank? [arg1-str arg2-str])
        (e/left "Insufficient arguments")
        (let [arg1 (parse-arg arg1-str)
              arg2 (parse-arg arg2-str)
              left (e/first-left [arg1 arg2])]
          (if (nil? left)
            (e/right {:op op
                      :arg1 (e/branch-right arg1 identity)
                      :arg2 (e/branch-right arg2 identity)})
            left)))
      (e/left (str "Unknown command " op-str)))))

(defn execute
  [{ op :op, arg1 :arg1, arg2 :arg2 }]
  (case op
    :add (e/right (+ arg1 arg2))
    :sub (e/right (- arg1 arg2))
    :mul (e/right (* arg1 arg2))
    :div (if (== arg2 0)
           (e/left "Can't divide by zero")
           (e/right (/ arg1 arg2)))))

(defn- dot-or [s] (if (= "." s) :dot s))

(defn argv-command [argv]
  (let [opstr (nth argv 0 "")
        arg1str (nth argv 1 "")
        arg2str (nth argv 2 "")]
    (if (s/blank? opstr)
      (e/left "No command given")
      (if (s/blank? arg1str)
        (e/left "No arguments given")
        (let [a1 (dot-or arg1str)
              a2 (dot-or arg2str)]
          (if (= :dot a1 a2)
            (e/left "Can't have two dot arguments")
            (e/right (list opstr a1 a2))))))))

(defn read-dot [read-line [op arg1 arg2]]
  (if (= :dot arg1)
    (list op (read-line) arg2)
    (if (= :dot arg2)
      (list op arg1 (read-line))
      (list op arg1 arg2))))

(defn -main
  "Execute arithmetic expression from command line arguments"
  [& argv]
  (as-> argv x
       (argv-command x)
       (c/bind x #(->> %
                       (read-dot read-line)
                       (apply get-command)))
       (c/bind x execute)
       (c/extract x)
       (println x)))
