(ns aoc-clj.2022.day2-test
  (:require [aoc-clj.2022.day2 :as sut]
            [clojure.string :as s]
            [clojure.test :as t]))

(def input
  (->> "resources/2022/day2.txt"
       slurp
       s/split-lines
       ))

(def sample
  ["A Y" "B X" "C Z"])

input

(t/deftest rps-score-test
  (t/is (= 15 (sut/rps-score sample)))
  (t/is (= 11150 (sut/rps-score input))))


(t/deftest decrypted-rps-score-test
  (t/is (= 12 (sut/decrypted-rps-score sample)))
  (t/is (= 8295 (sut/decrypted-rps-score input))))

(sut/decrypted-rps-score input)
