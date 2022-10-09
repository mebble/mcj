(ns mcj.utils
  (:require [cats.monad.either :as e]))

(defmacro buildtime-env [env-key] (System/getenv (name env-key)))

(defn in?
  "true if coll contains x (https://stackoverflow.com/a/3249777/5811761)"
  [coll x]
  (some #(= x %) coll))

(defn round-to
  "Round x to n decimal places
   Sources:
   - https://stackoverflow.com/a/4826827/5811761
   - https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html#setScale-int-java.math.RoundingMode-
   - https://docs.oracle.com/javase/8/docs/api/java/math/RoundingMode.html"
  [n x]
  (->> x
       bigdec
       (#(.setScale % n java.math.RoundingMode/HALF_UP))
       .doubleValue))

(defn parse-double [x err-msg]
  (try (e/right (Double/parseDouble x))
       (catch NumberFormatException _e (e/left err-msg))))
