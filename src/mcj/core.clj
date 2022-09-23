(ns mcj.core
  (:gen-class)
  (:require [cats.core :as c]
            [mcj.argv :refer [argv-command read-dot]]
            [mcj.command :refer [get-command execute]]))

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
