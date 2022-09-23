(ns mcj.argv
  (:require [clojure.string :as s]
            [cats.monad.either :as e]))

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
