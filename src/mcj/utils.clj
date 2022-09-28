(ns mcj.utils)

(defmacro buildtime-env [env-key] (System/getenv (name env-key)))
