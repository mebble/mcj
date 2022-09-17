(ns mcj.core-test
  (:require [clojure.test :refer :all]
            [mcj.core :refer :all]
            [cats.monad.either :as either]))

(deftest test-get-command
  (testing "Basic"
    (testing "All commands"
      (let [get-expected (fn [op] (either/right {:op op, :arg1 2.0, :arg2 3.0}))]
        (is (= (get-expected :add) (get-command "add" "2" "3")))
        (is (= (get-expected :sub) (get-command "sub" "2" "3")))
        (is (= (get-expected :mul) (get-command "mul" "2" "3")))
        (is (= (get-expected :div) (get-command "div" "2" "3")))))

    (testing "Negative arguments"
      (is (= (either/right {:op :add, :arg1 -2.0, :arg2 -3.0})
             (get-command "add" "-2" "-3"))))

    (testing "Fractional arguments"
      (is (= (either/right {:op :add, :arg1 2.5, :arg2 3.2})
             (get-command "add" "2.5" "3.2"))))

    (testing "Unknown command"
      (is (= (either/left "Unknown command foo")
             (get-command "foo" "2" "3"))))))
