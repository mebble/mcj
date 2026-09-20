(ns dev
  (:require [clojure.pprint]))

(defn cats-prefer-map-display
  "The `cats.monad.either` lib needs to pick a method to display in the REPL. Otherwise we get an error like:
  Multiple methods in multimethod 'simple-dispatch' match dispatch value: class cats.monad.either.Right -> interface clojure.lang.IDeref and interface clojure.lang.IPersistentMap, and neither is preferred"
  []
  (prefer-method
   clojure.pprint/simple-dispatch
   clojure.lang.IPersistentMap
   clojure.lang.IDeref))

(defn init []
  (cats-prefer-map-display))
