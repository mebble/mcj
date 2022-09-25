(ns mcj.core
  (:gen-class)
  (:require [cats.core :as c]
            [mcj.argv :refer [parse-argv read-dot]]
            [mcj.command :refer [parse-command execute]]))

(defn -main
  "Execute arithmetic expression from command line arguments"
  [& argv]
  (as-> argv x
       (parse-argv x)
       (c/bind x #(->> %
                       (read-dot read-line)
                       (apply parse-command)))
       (c/bind x execute)
       (c/extract x)
       (println x)))
