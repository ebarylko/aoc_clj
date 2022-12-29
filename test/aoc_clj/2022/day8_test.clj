(ns aoc-clj.2022.day8-test
  (:require [aoc-clj.2022.day8 :as sut]
            [clojure.string :as s]
            [clojure.test :as t]))

(def input
  (->> "resources/2022/day8.txt"
       slurp
       s/split-lines))

(def input-grid
  (sut/->grid input))

(def sample
  ["30373"
   "25512"
   "65332"
   "33549"
   "35390"]) 

(def sample-grid
  (sut/->grid sample))

(t/deftest exterior-trees-test
  (t/is (= 16
           (sut/exterior-trees sample-grid))))



(t/deftest interior-rows-test
  (t/is (= (sut/->grid ["25512" "65332" "33549"])
           (sut/interior-rows sample-grid))))

(t/deftest interior-columns-test
  (t/is (= (sut/->grid ["05535" "35353" "71349"])
           (sut/interior-columns sample-grid))))


(t/deftest all-columns-test
  (t/is (= (sut/->grid ["32633" "05535" "35353" "71349" "32290"])
           (sut/all-columns sample-grid))))

(t/deftest interior-trees-pos-test
  (t/is (= [[1 1] [1 2] [1 3] [2 1] [2 2] [2 3] [3 1] [3 2] [3 3]]
         (sut/interior-trees-pos sample))))

(t/deftest rows+columns-to-check-test
  (t/is (= [ [5 [ 2 ] [ 5 1 2 ] [0] [5 3 5]]
            [5 [3 3] [4 9] [3 5 3] [3]]]
          (sut/rows+columns-to-check
           (sut/->grid ["30373" "25512" "65332" "33549" "35390"])
            (sut/->grid ["32633" "05535" "35353" "71349" "32290"])
            [[1 1] [2 3]]))))



(t/deftest visible?-test
  (t/is (sut/visible?
         [7 [ 2 ] [5 1 2] [0] [5 3 5]]))
  (t/is (not (sut/visible?
         [ 1 [2] [5 1 2] [1] [5 3 5]]))))

(t/deftest visible-trees-test
  (t/is (= 21 (sut/visible-trees sample)))
  (t/is (= 1546 (sut/visible-trees input))))

(t/deftest viewing-score-test
  (t/is (= 2
           (sut/viewing-score
            5 [2 5] ))))

(t/deftest viewing-scores-test
  (t/is (= [ [1 1] [1 1]]
           (sut/viewing-scores [ 1 [ 2 ] [5 1 2] [ 1 ] [5 3 5] ])))
  (t/is (= [ [1 2] [1 2]]
           (sut/viewing-scores [ 5 [5 2] [1 2] [ 3 ] [3 5 3] ])))
  (t/is (= [ [2 2] [2 1]]
           (sut/viewing-scores [ 5 [3 3] [4 9] [3 5 3] [ 3 ]] ))))

(t/deftest scenic-score-test
  (t/is (= 1
           (sut/scenic-score [[1 1] [1 1]])))
  (t/is (= 8
           (sut/scenic-score [[2 2] [2 1]]))))

(t/deftest highest-scenic-score-test
  (t/is (= 8
           (sut/highest-scenic-score sample-grid)))
  (t/is (= 519064
           (sut/highest-scenic-score input-grid))))

