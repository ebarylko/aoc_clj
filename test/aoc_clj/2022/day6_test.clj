(ns aoc-clj.2022.day6-test
  (:require [aoc-clj.2022.day6 :as sut]
            [clojure.string :as s]
            [clojure.test :as t]))

(def input
  (->> "resources/2022/day6.txt"
       slurp
       s/split-lines
       first))

(def sample
  "bvwbjplbgvbhsrlpgdmjqwftvncz")


(t/deftest size-of-first-marker-test
  (t/is (= 5
           (sut/packet-marker-size sample)))
  (t/is (= 6
           (sut/packet-marker-size "nppdvjthqldpwncqszvftbrmjlhg")))
  (t/is (= 10
           (sut/packet-marker-size "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg ")))
  (t/is (= 11
           (sut/packet-marker-size "
nznrnfrfntjfmvfwmzdfjlvtqnbhcprsgzcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")))
  (t/is (= 1598
           (sut/packet-marker-size input)))

(t/deftest message-marker-size-test
  (t/is (= 19
           (sut/message-marker-size
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb")))
  (t/is (= 29
           (sut/message-marker-size
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")))
  (t/is (= 23
           (sut/message-marker-size
            "bvwbjplbgvbhsrlpgdmjqwftvncz")))
  (t/is (= 26
           (sut/message-marker-size
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")))
  (t/is (= 2414
           (sut/message-marker-size
            input))) ))
