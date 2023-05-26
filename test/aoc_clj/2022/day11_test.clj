(ns aoc-clj.2022.day11-test
  (:require [aoc-clj.2022.day11 :as sut]
            [clojure.string :as s]
            [clojure.test :as t]))

(def input
  (->> "resources/2022/day11.txt"
       slurp
       s/split-lines
       ((partial remove empty?))
     ((partial partition 6))))

(def sample
  [
   ["Monkey 0:"
    "Starting items: 79, 98"
    " Operation: new = old * 19 "
    " Test: divisible by 23 "
    " If true: throw to monkey 2 "
    " If false: throw to monkey 3 "]

   [
    " Monkey 1: "
    " Starting items: 54, 65, 75, 74 "
    " Operation: new = old + 6 "
    " Test: divisible by 19 "
    " If true: throw to monkey 2 "
    " If false: throw to monkey 0 "]

   [
    " Monkey 2: "

    " Starting items: 79, 60, 97 "
    " Operation: new = old * old "
    " Test: divisible by 13 "
    " If true: throw to monkey 1 "
    " If false: throw to monkey 3 "
]

   [" Monkey 3: "
    " Starting items: 74 "
    " Operation: new = old + 3 "
    " Test: divisible by 17 "
    " If true: throw to monkey 0 "
    " If false: throw to monkey 1 "]
   ]
)

(t/deftest monkey-info-test
  (t/is (= {:monkey0 {:items [79 98]
            :operation (partial * 19)
            :test #(zero? (mod % 23))
            :pass-test :monkey2
                      :fail-test :monkey3}}
           (sut/monkey-info (first sample)))))
