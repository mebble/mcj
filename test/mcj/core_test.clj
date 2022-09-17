(ns mcj.core-test
  (:require [clojure.test :refer :all]
            [mcj.core :refer :all]
            [cats.monad.either :as e]))

(deftest test-get-command
  (testing "Basic happy path"
    (let [get-expected (fn [op] (e/right {:op op, :arg1 2.0, :arg2 3.0}))]
      (is (= (get-expected :add) (get-command "add" "2" "3")))
      (is (= (get-expected :sub) (get-command "sub" "2" "3")))
      (is (= (get-expected :mul) (get-command "mul" "2" "3")))
      (is (= (get-expected :div) (get-command "div" "2" "3")))))

  (testing "Negative arguments"
    (is (= (e/right {:op :add, :arg1 -2.0, :arg2 -3.0})
           (get-command "add" "-2" "-3"))))

  (testing "Fractional arguments"
    (is (= (e/right {:op :add, :arg1 2.5, :arg2 3.2})
           (get-command "add" "2.5" "3.2"))))

  (testing "Unknown command"
    (is (= (e/left "Unknown command foo")
           (get-command "foo" "2" "3"))))

  (testing "Invalid argument"
    (is (= (e/left "Invalid argument abc")
           (get-command "add" "abc" "3")))
    (is (= (e/left "Invalid argument def")
           (get-command "add" "2" "def")))
    (is (= (e/left "Invalid argument abc")
           (get-command "add" "abc" "def"))))

  (testing "Missing argument"
    (let [expected (e/left "Insufficient arguments")]
      (testing "arg1"
        (is (= expected (get-command "add" nil "3")))
        (is (= expected (get-command "add" "" "3")))
        (is (= expected (get-command "add" "   " "3"))))

      (testing "arg2"
        (is (= expected (get-command "add" "2" nil)))
        (is (= expected (get-command "add" "2" "")))
        (is (= expected (get-command "add" "2" "   ")))))))

(deftest test-execute
  (testing "Happy path"
    (is (= (e/right 5) (execute {:op :add, :arg1 2, :arg2 3 })))
    (is (= (e/right -1) (execute {:op :sub, :arg1 2, :arg2 3})))
    (is (= (e/right 6) (execute {:op :mul, :arg1 2, :arg2 3})))
    (is (= (e/right 2/3) (execute {:op :div, :arg1 2, :arg2 3}))))

  (testing "Argument is zero"
    (is (= (e/right 0) (execute {:op :mul, :arg1 0, :arg2 1})))
    (is (= (e/right 0) (execute {:op :div, :arg1 0, :arg2 1})))
    (is (= (e/left "Can't divide by zero") (execute {:op :div, :arg1 1, :arg2 0})))
    (is (= (e/left "Can't divide by zero") (execute {:op :div, :arg1 1, :arg2 0.0})))))
