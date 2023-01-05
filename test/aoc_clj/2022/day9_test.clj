(ns aoc-clj.2022.day9-test
  (:require [aoc-clj.2022.day9 :as sut]
            [clojure.string :as s]
            [clojure.test :as t]))

(def input
  (->> "resources/2022/day9.txt"
       slurp
       s/split-lines
  ))

(def sample
  ["R 4"
   "U 4"
   "L 3"
   "D 1"
   "R 4"
   "D 1"
   "L 5"
   "R 2"])
input

(t/deftest move-head-test
  (t/is (= [4 0]
           (sut/move-head [0 0] "R 4")))
  (t/is (= [7 7]
           (sut/move-head [7 3] "U 4")))
  (t/is (= [-2 0]
           (sut/move-head [2 0] "L 4")))
  (t/is (= [3 5]
           (sut/move-head [3 9] "D 4")))
  )

(t/deftest positions-visited-test
  (t/is (= 13
           (sut/positions-visited sample))))
