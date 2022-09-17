(ns mcj.core
  (:gen-class)
  (:require [cats.monad.either :as either]))

(defn -main
  "I don't do a whole lot ... yet."
  [& argv]
  (println (first argv))
  (println (second argv))
  (println (nth argv 2)))

(defn get-command
  [op arg1 arg2]
  (either/right {:op (keyword op)
                 :arg1 (Double/parseDouble arg1)
                 :arg2 (Double/parseDouble arg2)}))
