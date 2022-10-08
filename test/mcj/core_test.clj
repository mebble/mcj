(ns mcj.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [mcj.core :refer [-main]]))

(deftest test-integration-main
  (let [mock-out (fn [f] (with-out-str (f)))
        mock-in-out (fn [f] (with-in-str "10" (mock-out f)))]
    (testing "Happy path"

      (testing "Information"
        (is (= "vx.y.z\n" (mock-out #(-main "-v"))))
        (is (= "Help output\n" (mock-out #(-main "-h")))))

      (testing "Arithmetic command"
        (testing "Basic"
          (is (= "5.0\n" (mock-out #(-main "add" "2" "3"))))
          (is (= "5.0\n" (mock-out #(-main "add" "2" "3" "extra" "argv" "items"))))
          (is (= "12.0\n" (mock-in-out #(-main "add" "2" ".")))))

        (testing "Formatted output"
          (is (= "0.6667\n" (mock-out #(-main "div" "2" "3" "-d" "4"))))
          (is (= "1.3333\n" (mock-out #(-main "div" "4" "3" "-d" "4")))))))

    (testing "Sad path"
      (is (= "No command given\n" (mock-out #(-main))))
      (is (= "Unknown command foo\n" (mock-out #(-main "foo" "2" "3")))))))
