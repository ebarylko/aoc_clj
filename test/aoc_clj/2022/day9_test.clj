(ns aoc-clj.2022.day1-test
  (:require [aoc-clj.2022.day1 :as sut]
            [clojure.string :as s]
            [clojure.test :as t]))

(def input
  (->> "resources/2022/day1.txt"
       slurp
       s/split-lines
       
  ))

(def sample
  ["1000"
   "2000"
   "3000"
   ""
   "4000"
   ""
   "5000"
   "6000"
   ""
   "7000"
   "8000"
   "9000"
   ""
   "10000"]
)
input

(t/deftest max-calories-test
  (t/is (= 24000 (sut/max-calories sample)))
  (t/is (= 71780 (sut/max-calories input))))

(t/deftest sum-max-calories-test
  (t/is (= 45000 (sut/sum-max-calories sample)))
  (t/is (= 212489 (sut/sum-max-calories input))))
