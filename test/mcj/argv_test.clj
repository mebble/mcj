(ns mcj.argv-test
  (:require [clojure.test :refer [deftest is]]
            [spy.core :as spy]
            [cats.monad.either :as e]
            [mcj.argv :refer [parse-argv read-dot]]))

(deftest test-argv-command
  (is (= (e/left "No command given") (parse-argv ())))
  (is (= (e/left "No arguments given") (parse-argv '("add"))))
  (is (= (e/right '("use", "2", "")) (parse-argv '("use" "2"))))
  (is (= (e/right '("add" "2" "3")) (parse-argv '("add" "2" "3"))))
  (is (= (e/right '("add" :dot "3")) (parse-argv '("add" "." "3"))))
  (is (= (e/right '("add" "2" :dot)) (parse-argv '("add" "2" "."))))
  (is (= (e/left "Can't have two dot arguments") (parse-argv '("add" "." ".")))))

(deftest test-read-dot
  (let [mock-read-line (spy/stub "10")]
    (is (= '("add" "2" "3") (read-dot mock-read-line '("add" "2" "3"))))
    (is (spy/not-called? mock-read-line))
    (is (= '("add" "10" "3") (read-dot mock-read-line '("add" :dot "3"))))
    (is (= '("add" "2" "10") (read-dot mock-read-line '("add" "2" :dot))))))
