(ns mcj.utils
  (:require [environ.core :refer [env]]))

(defmacro buildtime-env [env-key] (env env-key))
