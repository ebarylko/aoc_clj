(ns aoc-clj.2022.day4-test
  (:require [aoc-clj.2022.day4 :as sut]
            [clojure.string :as s]
            [clojure.test :as t]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [com.gfredericks.test.chuck.clojure-test :refer [checking]]))

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



(def range-gen
  (->> gen/nat
       (gen/tuple gen/nat)
       (gen/fmap sort)
       (gen/such-that (fn [[fst snd]]
                     (> snd fst)))))

(gen/sample range-gen)
(gen/generate range-gen)
(t/deftest parse-ranges-test
  (checking "Ranges count matches difference between min and max" 1000
            [[a b] range-gen
             [c d] range-gen]
            (let [[fst snd] (sut/parse-ranges (format "%d-%d,%d-%d" a b c d))]
              (t/is (= (set (range a (inc b))) fst))
              (t/is (= (set (range c (inc d))) snd))   )))

(t/deftest contained-ranges-test
  (t/is (= 2 (sut/contained-ranges sample)))
  (t/is (= 524 (sut/contained-ranges input))))

(t/deftest overlapping-ranges-test
  (t/is (= 4 (sut/overlapping-ranges sample)))
  (t/is (= 798 (sut/overlapping-ranges input))))
