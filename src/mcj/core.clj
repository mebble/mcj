(ns mcj.core
  (:gen-class)
  (:require [cats.core :as c]
            [mcj.argv :refer [parse-argv read-dot]]
            [mcj.command :refer [parse-command execute]]
            [mcj.utils :refer [buildtime-env]]))

(def project-url (buildtime-env :PROJECT_URL))
(def app-version (buildtime-env :APP_VERSION))

(defn -main
  "Execute arithmetic expression from command line arguments"
  [& argv]
  (as-> argv x
       (parse-argv x)
       (c/bind x #(->> %
                       :cmd-str
                       (read-dot read-line)
                       (apply parse-command)))
       (c/bind x execute)
       (c/extract x)
       (println x)))
