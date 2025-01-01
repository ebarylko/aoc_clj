(ns aoc-clj.2024.day2-test
  (:require [aoc-clj.2024.day2 :as sut]
            [clojure.test :as t]))

(def sample
  [[7 6 4 2 1]
  [1 2 7 8 9]
  [9 7 6 2 1]
  [1 3 2 4 5]
  [8 6 4 4 1]
  [1 3 6 7 9]])

(t/deftest num-of-safe-reports
  (t/is (= 2 (sut/num-of-safe-reports sample))))
