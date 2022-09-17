(ns mcj.core-test
  (:require [clojure.test :refer :all]
            [mcj.core :refer :all]
            [cats.monad.either :as either]))

(deftest test-get-command
  (testing "Basic happy path"
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
           (get-command "foo" "2" "3"))))

  (testing "Invalid argument"
    (is (= (either/left "Invalid argument abc")
           (get-command "add" "abc" "3")))
    (is (= (either/left "Invalid argument def")
           (get-command "add" "2" "def")))
    (is (= (either/left "Invalid argument abc")
           (get-command "add" "abc" "def")))))

(deftest test-execute
  (testing "Happy path"
    (is (= (either/right 5) (execute {:op :add, :arg1 2, :arg2 3 })))
    (is (= (either/right -1) (execute {:op :sub, :arg1 2, :arg2 3})))
    (is (= (either/right 6) (execute {:op :mul, :arg1 2, :arg2 3})))
    (is (= (either/right 2/3) (execute {:op :div, :arg1 2, :arg2 3}))))

  (testing "Argument is zero"
    (is (= (either/right 0) (execute {:op :mul, :arg1 0, :arg2 1})))
    (is (= (either/right 0) (execute {:op :div, :arg1 0, :arg2 1})))
    (is (= (either/left "Can't divide by zero") (execute {:op :div, :arg1 1, :arg2 0})))))
