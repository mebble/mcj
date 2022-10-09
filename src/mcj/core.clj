(ns mcj.core
  (:gen-class)
  (:require [cats.core :as c]
            [mcj.argv :refer [parse-argv read-dot break-out]]
            [mcj.command :refer [parse-command execute]]
            [mcj.utils :refer [buildtime-env round-to]]))

(def ^:private configs {:PROJECT_URL (buildtime-env :PROJECT_URL)
                        :APP_VERSION (buildtime-env :APP_VERSION)})
(def ^:private default-places 2)

(defn- monadic-pipeline [argv]
  (c/mlet [{places :places :as argv :or {places default-places}} (parse-argv argv)]
          (c/fmap #(round-to places %) (c/->>= (c/return argv)
                                   (break-out configs)
                                   (#(->> %
                                          (read-dot read-line)
                                          (apply parse-command)))
                                   execute))))

(defn -main
  "Execute arithmetic expression from command line arguments"
  [& argv]
  (->> argv
       monadic-pipeline
       c/extract
       println))
