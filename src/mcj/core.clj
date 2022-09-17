(ns mcj.core
  (:gen-class)
  (:require [cats.monad.either :as either]))

(defn -main
  "I don't do a whole lot ... yet."
  [& argv]
  (println (first argv))
  (println (second argv))
  (println (nth argv 2)))

(def operations #{:add :sub :mul :div})

(defn- parse-arg [arg-str] (try (either/right (Double/parseDouble arg-str))
                                (catch NumberFormatException _e
                                  (either/left (str "Invalid argument " arg-str)))))

(defn get-command
  [op-str arg1-str arg2-str]
  (let [op (keyword op-str)]
    (if (contains? operations op)
      (let [arg1 (parse-arg arg1-str)
            arg2 (parse-arg arg2-str)
            left (either/first-left [arg1 arg2])]
        (if (nil? left)
          (either/right {:op op
                         :arg1 (either/branch-right arg1 identity)
                         :arg2 (either/branch-right arg2 identity)})
          left))
      (either/left (str "Unknown command " op-str)))))

(defn execute
  [{ op :op, arg1 :arg1, arg2 :arg2 }]
  (either/right (case op
                  :add (+ arg1 arg2)
                  :sub (- arg1 arg2)
                  :mul (* arg1 arg2)
                  :div (/ arg1 arg2))))
