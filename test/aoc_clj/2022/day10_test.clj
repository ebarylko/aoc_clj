(ns aoc-clj.2022.day10-test
  (:require [aoc-clj.2022.day10 :as sut]
            [clojure.string :as s]
            [clojure.test :as t]))


(def input
  (->> "resources/2022/day10.txt"
       slurp
       s/split-lines
       ))

(def sample [
             "addx 15 "
             "addx -11 "
             "addx 6"
             "addx -3"
             "addx 5"
             "addx -1"
             "addx -8"
             "addx 13"
             "addx 4"
             "noop"
             "addx -1"
             "addx 5"
             "addx -1"
             "addx 5"
             "addx -1"
             "addx 5"
             "addx -1"
             "addx 5"
             "addx -1"
             "addx -35"
             "addx 1"
             "addx 24"
             "addx -19"
             "addx 1"
             "addx 16"
             "addx -11"
             "noop"
             "noop"
             "addx 21"
             "addx -15"
             "noop"
             "noop"
             "addx -3"
             "addx 9"
             "addx 1"
             "addx -3"
             "addx 8"
             "addx 1"
             "addx 5"
             "noop"
             "noop"
             "noop"
             "noop"
             "noop"
             "addx -36"
             "noop"
             "addx 1"
             "addx 7"
             "noop"
             "noop"
             "noop"
             "addx 2"
             "addx 6"
             "noop"
             "noop"
             "noop"
             "noop"
             "noop"
             "addx 1"
             "noop"
             "noop"
             "addx 7"
             "addx 1"
             "noop"
             "addx -13"
             "addx 13"
             "addx 7"
             "noop"
             "addx 1"
             "addx -33"
             "noop"
             "noop"
             "noop"
             "addx 2"
             "noop"
             "noop"
             "noop"
             "addx 8"
             "noop"
             "addx -1"
             "addx 2"
             "addx 1"
             "noop"
             "addx 17"
             "addx -9"
             "addx 1"
             "addx 1"
             "addx -3"
             "addx 11"
             "noop"
             "noop"
             "addx 1"
             "noop"
             "addx 1"
             "noop"
             "noop"
             "addx -13"
             "addx -19"
             "addx 1"
             "addx 3"
             "addx 26"
             "addx -30"
             "addx 12"
             "addx -1"
             "addx 3"
             "addx 1"
             "noop"
             "noop"
             "noop"
             "addx -9"
             "addx 18"
             "addx 1"
             "addx 2"
             "noop"
             "noop"
             "addx 9"
             "noop"
             "noop"
             "noop"
             "addx -1"
             "addx 2"
             "addx -37"
             "addx 1"
             "addx 3"
             "noop"
             "addx 15"
             "addx -21"
             "addx 22"
             "addx -6"
             "addx 1"
             "noop"
             "addx 2"
             "addx 1"
             "noop"
             "addx -10"
             "noop"
             "noop"
             "addx 20"
             "addx 1"
             "addx 2"
             "addx 2"
             "addx -6"
             "addx -11"
             "noop"
             "noop"
             "noop"])

(def ex-step (first sample))
(def neg-step "addx -11")
(def noop-step "noop")
(sut/parse-instruction-value ex-step)
(sut/parse-instruction-value noop-step)
(sut/parse-instruction-value neg-step)
(Integer/parseInt "-2877")


(t/deftest cycle-value-test
  (t/is (= 21 (sut/sum-cycles sample 20)))
  (t/is (= 19 (sut/sum-cycles sample 60)))
  (t/is (= 18 (sut/sum-cycles sample 100)))
  (t/is (= 21 (sut/sum-cycles sample 140)))
  (t/is (= 16 (sut/sum-cycles sample 180)))
  (t/is (= 18 (sut/sum-cycles sample 220))))


(t/deftest sum-signal-strengths-test
  (t/is (= 13140 (sut/sum-signal-strengths sample))))

(t/deftest sum-signal-strengths-test-input
  (t/is (= 16480 (sut/sum-signal-strengths input))))
