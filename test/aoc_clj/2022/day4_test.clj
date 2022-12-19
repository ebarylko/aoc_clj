(ns aoc-clj.2022.day4-test
  (:require [aoc-clj.2022.day4 :as sut]
            [clojure.string :as s]
            [clojure.test :as t]))

(def input
  (->> "resources/2022/day4.txt"
       slurp
       s/split-lines
       ))

(def sample
  ["2-4,6-8"
   "2-3,4-5"
   "5-7,7-9"
   "2-8,3-7"
   "6-6,4-6"
   "2-6,4-8"])


(t/deftest contained-ranges-test
  (t/is (= 2 (sut/contained-ranges sample)))
  (t/is (= 524 (sut/contained-ranges input))))

(t/deftest overlapping-ranges-test
  (t/is (= 4 (sut/overlapping-ranges sample)))
  (t/is (= 798 (sut/overlapping-ranges input))))
