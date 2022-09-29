(ns mcj.utils)

(defmacro buildtime-env [env-key] (System/getenv (name env-key)))

(defn in?
  "true if coll contains x (https://stackoverflow.com/a/3249777/5811761)"
  [coll x]
  (some #(= x %) coll))
