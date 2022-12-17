(ns aoc-clj.2022.day3-test
  (:require [aoc-clj.2022.day3 :as sut]
            [clojure.string :as s]
            [clojure.test :as t]))

(def input
  (->> "resources/2022/day3.txt"
       slurp
       s/split-lines
  ))

(def sample
  ["vJrwpWtwJgWrhcsFMMfFFhFp"
   "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"
   "PmmdzqPrVvPwwTWBwg"
   "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"
   "ttgJtRGJQctTZtZT"
   "CrZsJsPPZsGzwwsLwLmpwMDw"])


(t/deftest priority-sum-test
  (t/is (= 157 (sut/priority-sum sample)))
  (t/is (= 8515 (sut/priority-sum input))))



(t/deftest sum-badges-test
  (t/is (= 70 (sut/sum-badges sample)))
  (t/is (= 2434 (sut/sum-badges input))))
