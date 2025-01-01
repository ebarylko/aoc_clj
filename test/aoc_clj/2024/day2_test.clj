(ns aoc-clj.2024.day2-test
  (:require [aoc-clj.2024.day2 :as sut]
            [clojure.test :as t]
            [clojure.string :as s]))

(def input
  (->> "resources/2024/day2.txt"
       slurp
       s/split-lines
       (map #(format "[%s]" %))
       (map read-string)))

(def sample
  [[7 6 4 2 1]
  [1 2 7 8 9]
  [9 7 6 2 1]
  [1 3 2 4 5]
  [8 6 4 4 1]
  [1 3 6 7 9]])

(t/deftest num-of-safe-reports
  (t/is (= 2 (sut/num-of-safe-reports sample)))
  (t/is (= 598 (sut/num-of-safe-reports input))))

(t/deftest num-of-safe-reports*
  (t/is (= 4 (sut/num-of-safe-reports* sample)))
  (t/is (= 2 (sut/num-of-safe-reports* input))))

(s/split-lines "hi\nwhy")
(map (partial apply -) (partition 2 1 [1 2 3 4]))
(< 1 1 4)
(take 0 [23 8 9 8 9])
(def a (drop 3 [23 7 4 8 9]) )
(def b (take 2 [23 7 4 8 9]))
(concat a b)
(range 1 8)
(remove #{1 2} [1 3 2])
(seq [1])
(vec (take 3 a))
