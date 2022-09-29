(ns mcj.argv
  (:require [clojure.string :as s]
            [cats.monad.either :as e]
            [mcj.utils :refer [in?]]))

(defn- dot-or [s] (if (= "." s) :dot s))

(defn parse-argv [argv]
  (let [opstr (nth argv 0 "")
        arg1str (nth argv 1 "")
        arg2str (nth argv 2 "")
        a1 (dot-or arg1str)
        a2 (dot-or arg2str)]
    (cond
      (in? argv "-v")    (e/right {:version true})
      (s/blank? opstr)   (e/left "No command given")
      (s/blank? arg1str) (e/left "No arguments given")
      (= :dot a1 a2)     (e/left "Can't have two dot arguments")
      :else              (e/right {:cmd-str (list opstr a1 a2)}))))

(defn read-dot [read-line [op arg1 arg2]]
  (cond
    (= :dot arg1) (list op (read-line) arg2)
    (= :dot arg2) (list op arg1 (read-line))
    :else         (list op arg1 arg2)))
