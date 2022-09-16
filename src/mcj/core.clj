(ns mcj.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& argv]
  (println (first argv))
  (println (second argv))
  (println (nth argv 2)))

(defn get-command
  [argv]
  { :op (keyword (first argv))
    :arg1 (Double/parseDouble (second argv))
    :arg2 (Double/parseDouble (nth argv 2))})
