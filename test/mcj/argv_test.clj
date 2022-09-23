(ns mcj.argv-test
  (:require [clojure.test :refer [deftest is]]
            [spy.core :as spy]
            [cats.monad.either :as e]
            [mcj.argv :refer [argv-command read-dot]]))

(deftest test-argv-command
  (is (= (e/left "No command given") (argv-command ())))
  (is (= (e/left "No arguments given") (argv-command '("add"))))
  (is (= (e/right '("use", "2", "")) (argv-command '("use" "2"))))
  (is (= (e/right '("add" "2" "3")) (argv-command '("add" "2" "3"))))
  (is (= (e/right '("add" :dot "3")) (argv-command '("add" "." "3"))))
  (is (= (e/right '("add" "2" :dot)) (argv-command '("add" "2" "."))))
  (is (= (e/left "Can't have two dot arguments") (argv-command '("add" "." ".")))))

(deftest test-read-dot
  (let [mock-read-line (spy/stub "10")]
    (is (= '("add" "2" "3") (read-dot mock-read-line '("add" "2" "3"))))
    (is (spy/not-called? mock-read-line))
    (is (= '("add" "10" "3") (read-dot mock-read-line '("add" :dot "3"))))
    (is (= '("add" "2" "10") (read-dot mock-read-line '("add" "2" :dot))))))
