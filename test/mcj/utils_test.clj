(ns mcj.utils-test
  (:require [clojure.test :refer [deftest testing is]]
            [mcj.utils :refer [round-to]]))

(deftest test-round-to
  (testing "round to two decimal places"
    (testing "integer"
      (is (= 1. (round-to 2 1))))
    (testing "arg has fewer than two places"
      (is (= 1.1 (round-to 2 1.1))))
    (testing "round down"
      (is (= 1.12 (round-to 2 1.1234))))
    (testing "round up"
      (is (= 1.13 (round-to 2 1.1261))))
    (testing "round down to prev whole num"
      (is (= 1. (round-to 2 1.001))))
    (testing "round up to next whole num"
      (is (= 2. (round-to 2 1.999)))))

  (testing "round to three decimal places"
    (testing "round down"
      (is (= 1.123 (round-to 3 1.1234))))
    (testing "round up"
      (is (= 1.124 (round-to 3 1.12361))))))
