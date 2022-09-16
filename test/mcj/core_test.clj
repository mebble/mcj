(ns mcj.core-test
  (:require [clojure.test :refer :all]
            [mcj.core :refer :all]))

(deftest test-get-command
  (testing "Basic"
    (testing "All commands"
      (is (= {:op :add, :arg1 2.0, :arg2 3.0} (get-command '("add" "2" "3"))))
      (is (= {:op :sub, :arg1 2.0, :arg2 3.0} (get-command '("sub" "2" "3"))))
      (is (= {:op :mul, :arg1 2.0, :arg2 3.0} (get-command '("mul" "2" "3"))))
      (is (= {:op :div, :arg1 2.0, :arg2 3.0} (get-command '("div" "2" "3")))))

    (testing "Negative arguments"
      (is (= {:op :add, :arg1 -2.0, :arg2 -3.0} (get-command '("add" "-2" "-3")))))

    (testing "Fractional arguments"
      (is (= {:op :add, :arg1 2.5, :arg2 3.2} (get-command '("add" "2.5" "3.2")))))))
