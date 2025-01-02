(ns aoc-clj.2024.day1-test
  (:require [aoc-clj.2024.day1 :as sut]
            [clojure.test :as t]
            [clojure.string :as s]))

(def input
  (->> "resources/2024/day1.txt"
       slurp
       s/split-lines
       (map #(format "[%s]" %))
       (map read-string)
       (apply mapv vector)))

(def sample
  (->> [[3   4]
        [4   3]
        [2   5]
        [1   3]
        [3   9]
        [3   3]]
       (apply map vector)
       ))

(t/deftest total-distance-test
  (t/is (= 11 (sut/total-distance sample)))
  (t/is (= 2378066 (sut/total-distance input))))

(t/deftest similarity-score-test
  (t/is (= 31 (sut/similarity-score sample)))
  (t/is (= 18934359 (sut/similarity-score input))))
