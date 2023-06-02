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

(t/deftest remove-empty-space-test
  (t/is (= '("Starting" "items:" "79," "98")
           (sut/remove-empty-space "Starting items: 79, 98"))))

(t/deftest determine-operation-test
  (t/is (= ( (partial + 13) 1)
           ( (sut/determine-operation "+" "13") 1))))

(t/deftest parse-operation-test
  (t/is (= 4.0
           ( (sut/parse-operation "Operation: new = old * old ") 2))))

(t/deftest gen-monkey-num-test
  (t/is (= :monkey1
           (sut/gen-monkey-num " Monkey 1: "))))


(t/deftest num-value-test
  (t/is (= 3 (sut/num-value "3"))))

(t/deftest squaring?-test
  (t/is (sut/squaring? "*" "old")))

(t/deftest throw-to-test
  (t/is (= :moneky2
           (sut/throw-to " If true: throw to monkey 2 "))))

(t/deftest multiplication-or-addition-test
  (t/is (= * (sut/multiplication-or-addition "*"))))

(t/deftest parse-operation-test
  (t/is (= 38
           ((sut/parse-operation " Operation: new = old * 19 ") 2 ))))

(t/deftest test-operation-test
  (t/is ((sut/test-operation " Test: divisible by 23 ") 23)))

(t/deftest monkey-info-test
  (t/is (= {:monkey0 {:items [79 98]
            :operation (partial * 19)
            :test #(zero? (mod % 23))
            :success :monkey2
                      :fail :monkey3}}
           (sut/monkey-info (first sample)))))

(t/deftest item-shift-test
  (t/is (= {:monkey3 [500 620]}
           (sut/item-shift
            (:monkey0 (sut/monkey-info (first sample)))
            ))))

(t/deftest new-monkey-lists-test
  (t/is (= 10
           (:monkey3 (first (sut/new-monkey-lists
            (sut/all-monkeys sample)
            :monkey0))))))

(t/deftest round-test
  (t/is {:monkey0 [20 23 27 26]
         :monkey1 [2080, 25, 167, 207, 401, 1046]
         :monkey2 []
         :monkey3 [500 620]
         }

(sut/round (sut/all-monkeys sample))))
(sut/item-shift (sut/monkey-info (first sample)))
(keys (sut/all-monkeys sample))
(:monkey0 (sut/all-monkeys sample))
