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

(defn get-command
  [opstr arg1 arg2]
  (let [op (keyword opstr)]
    (if (contains? operations op)
      (either/right {:op op
                     :arg1 (Double/parseDouble arg1)
                     :arg2 (Double/parseDouble arg2)})
      (either/left (str "Unknown command " opstr)))))
