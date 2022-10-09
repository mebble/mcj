(ns mcj.argv-test
  (:require [clojure.test :refer [deftest is testing]]
            [spy.core :as spy]
            [cats.monad.either :as e]
            [mcj.argv :refer [parse-argv read-dot break-out]]))

(deftest test-parse-argv
  (testing "arithmetric command arguments"
    (is (= (e/left "No command given") (parse-argv ())))
    (is (= (e/left "No arguments given") (parse-argv '("add"))))
    (is (= (e/left "Can't have two dot arguments") (parse-argv '("add" "." "."))))
    (is (= (e/right {:cmd-str '("use", "2", "")}) (parse-argv '("use" "2"))))
    (is (= (e/right {:cmd-str '("add" "2" "3")}) (parse-argv '("add" "2" "3"))))
    (is (= (e/right {:cmd-str '("add" :dot "3")}) (parse-argv '("add" "." "3"))))
    (is (= (e/right {:cmd-str '("add" "2" :dot)}) (parse-argv '("add" "2" ".")))))

  (testing "informational arguments"
    (is (= (e/right {:version true}) (parse-argv '("-v"))))
    (is (= (e/right {:help true}) (parse-argv '("-h"))))
    (is (= (e/right {:help true}) (parse-argv '("-h" "-v")))))

  (testing "formatting arguments"
    (is (= (e/left "No command given") (parse-argv '("-d"))))
    (is (= (e/left "Do like dis: -d <integer>") (parse-argv '("add" "2" "3" "-d"))))
    (is (= (e/left "Do like dis: -d <integer>") (parse-argv '("add" "2" "3" "-d" "a"))))
    (is (= (e/right {:cmd-str '("add" "2" "3") :places 2})
           (parse-argv '("add" "2" "3" "-d" "2.5"))))
    (is (= (e/right {:cmd-str '("add" "2" "3") :places 2})
           (parse-argv '("add" "2" "3" "-d" "2"))))))

(deftest test-read-dot
  (let [mock-read-line (spy/stub "10")]
    (is (= '("add" "2" "3") (read-dot mock-read-line '("add" "2" "3"))))
    (is (spy/not-called? mock-read-line))
    (is (= '("add" "10" "3") (read-dot mock-read-line '("add" :dot "3"))))
    (is (= '("add" "2" "10") (read-dot mock-read-line '("add" "2" :dot))))))

(deftest test-break-out
  (let [configs {:APP_VERSION "app-version"}]
    (is (= (e/left "Help output")     (break-out configs {:help true :version true :cmd-str 'any})))
    (is (= (e/left "app-version")     (break-out configs {:version true :cmd-str 'any})))
    (is (= (e/right '("add" "2" "3")) (break-out configs {:cmd-str '("add" "2" "3")})))))
