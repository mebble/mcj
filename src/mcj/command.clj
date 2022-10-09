(ns mcj.command
  (:require [clojure.string :as s]
            [cats.monad.either :as e]
            [mcj.utils :refer [parse-number]]))

(def ^:private operations #{:add :sub :mul :div})

(defn parse-command
  [op-str arg1-str arg2-str]
  (let [op (keyword op-str)
        msg "Invalid argument "]
    (cond
      (not (contains? operations op))     (e/left (str "Unknown command " op-str))
      (some s/blank? [arg1-str arg2-str]) (e/left "Insufficient arguments")
      :else (let [arg1 (parse-number arg1-str (str msg arg1-str))
                  arg2 (parse-number arg2-str (str msg arg2-str))
                  left (e/first-left [arg1 arg2])]
              (if-not (nil? left)
                left
                (e/right {:op op
                          :arg1 (e/branch-right arg1 identity)
                          :arg2 (e/branch-right arg2 identity)}))))))

(defn execute
  [{op :op, arg1 :arg1, arg2 :arg2}]
  (case op
    :add (e/right (+ arg1 arg2))
    :sub (e/right (- arg1 arg2))
    :mul (e/right (* arg1 arg2))
    :div (if (== arg2 0)
           (e/left "Can't divide by zero")
           (e/right (/ arg1 arg2)))))
