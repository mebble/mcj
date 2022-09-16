(ns mcj.core-test
  (:require [clojure.test :refer :all]
            [mcj.core :refer :all]))

(deftest test-get-command
  (testing "Add command"
    (testing "Basic"
      (is (= {:op :add, :arg1 2, :arg2 3} (get-command '("add" "2" "3")))))
    
    (testing "Negative arguments"
      (is (= {:op :add, :arg1 -2, :arg2 -3} (get-command '("add" "-2" "-3")))))))
