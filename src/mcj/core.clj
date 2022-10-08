(ns mcj.core
  (:gen-class)
  (:require [cats.core :as c]
            [mcj.argv :refer [parse-argv read-dot break-out]]
            [mcj.command :refer [parse-command execute]]
            [mcj.utils :refer [buildtime-env round-to]]))

(def ^:private configs {:PROJECT_URL (buildtime-env :PROJECT_URL)
                        :APP_VERSION (buildtime-env :APP_VERSION)})

(defn- monadic-pipeline [argv]
  (c/fmap #(round-to 4 %) (c/->>= (parse-argv argv)
                                  (break-out configs)
                                  (#(->> %
                                         (read-dot read-line)
                                         (apply parse-command)))
                                  execute)))

(defn -main
  "Execute arithmetic expression from command line arguments"
  [& argv]
  (->> argv
       monadic-pipeline
       c/extract
       println))
