(ns mcj.core
  (:gen-class)
  (:require [cats.core :as c]
            [mcj.argv :refer [parse-argv read-dot break-out]]
            [mcj.command :refer [parse-command execute]]
            [mcj.utils :refer [buildtime-env round-to]]))

(def ^:private configs {:PROJECT_URL (buildtime-env :PROJECT_URL)
                        :APP_VERSION (buildtime-env :APP_VERSION)})
(def ^:private default-places 2)

(defn- m-parse-command [x]
  (->> x
       (read-dot read-line)
       (apply parse-command)))

(defn- m-round [places n]
  (->> n
       (round-to places)
       (c/return)))

(defn- m-app [argv]
  (c/mlet [{places :places
            :as parsed
            :or {places default-places}} (parse-argv argv)]
          (c/->>= (c/return parsed)
                  (break-out configs)
                  (m-parse-command)
                  (execute)
                  (m-round places))))

(defn -main
  "Execute arithmetic expression from command line arguments"
  [& argv]
  (->> argv
       m-app
       c/extract
       println))
