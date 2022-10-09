(ns mcj.argv
  (:require [clojure.string :as s]
            [cats.core :as c]
            [cats.monad.either :as e]
            [mcj.utils :refer [in? parse-number]]))

(defn- get-places [argv]
  (if (in? argv "-d")
    (let [key-loc (.indexOf argv "-d")
          val-str (nth argv (inc key-loc) "")]
      (parse-number val-str "Do like dis: -d <integer>"))
    (e/right)))

(defn- dot-or [s] (if (= "." s) :dot (str s)))

(defn parse-argv [argv]
  (let [has-h (in? argv "-h")
        has-v (in? argv "-v")
        places (get-places argv)
        argv (remove #{"-h" "-v" "-d"} argv)
        [opstr arg1str arg2str] argv
        a1 (dot-or arg1str)
        a2 (dot-or arg2str)]
    (cond
      has-h    (e/right {:help true})
      has-v    (e/right {:version true})
      (s/blank? opstr)   (e/left "No command given")
      (s/blank? arg1str) (e/left "No arguments given")
      (= :dot a1 a2)     (e/left "Can't have two dot arguments")
      (e/left? places)   places
      :else              (let [places-val (c/extract places)]
                           (cond-> {:cmd-str (list opstr a1 a2)}
                             places-val (assoc :places (int places-val))
                             true e/right)))))

(defn read-dot [read-line [op arg1 arg2]]
  (cond
    (= :dot arg1) (list op (read-line) arg2)
    (= :dot arg2) (list op arg1 (read-line))
    :else         (list op arg1 arg2)))

(defn break-out [configs parsed-argv]
  (cond
    (:help parsed-argv)    (e/left "Help output")
    (:version parsed-argv) (e/left (:APP_VERSION configs))
    :else                  (e/right (:cmd-str parsed-argv))))
