(ns aoc-clj.2022.day9-test
  (:require [aoc-clj.2022.day9 :as sut]
            [clojure.string :as s]
            [clojure.test :as t]))

(def input
  (->> "resources/2022/day9.txt"
       slurp
       s/split-lines))

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

(def large-sample
  ["R 5"
   "U 8"
   "L 8"
   "D 3"
   "R 17"
   "D 10"
   "L 25"
   "U 20"])

(t/deftest move-head-test
  (t/is (= [[1 0] [2 0] [3 0][4 0]]
           (sut/move-head [0 0] "R 4")))
  (t/is (= [ [7 4] [7 5] [7 6][7 7]]
           (sut/move-head [7 3] "U 4")))
  (t/is (= [ [1 0] [0 0] [-1 0] [-2 0] ]
           (sut/move-head [2 0] "L 4")))
  (t/is (= [ [3 8] [3 7] [3 6] [3 5] ]
           (sut/move-head [3 9] "D 4")))
  )

(t/deftest touching?-test
  (t/is (sut/touching?  [2 2] [2 1]))
  (t/is (sut/touching?  [2 2] [3 2]))
  (t/is (sut/touching?  [2 2] [1 2]))
  (t/is (sut/touching?  [2 2] [3 3]))
  (t/is (not (sut/touching?  [2 2] [0 0])))
  (t/is (not (sut/touching?  [2 2] [9 0]))))

;; (t/deftest tail-above-head?-test
;;   (t/is (sut/tail-above-head? 0 2))
;;   (t/is (not (sut/tail-above-head? 2 0))))

;; (t/deftest north-east?-test
;;   (t/is (sut/north-east?  [0 0] [1 3]))
;;   (t/is (not (sut/north-east? [1 3] [0 0]))))

;; (t/deftest south-east?-test
;;   (t/is (sut/south-east?  [0 0] [1 -4]))
;;   (t/is (not (sut/south-east? [1 3] [0 0]))))

;; (t/deftest north-west?-test
;;   (t/is (sut/north-west?  [0 0] [-1 4]))
;;   (t/is (not (sut/north-west? [3 90] [2 9]))))

;; (t/deftest south-west?-test
;;   (t/is (sut/south-west?  [0 0] [-1 -4]))
;;   (t/is (not (sut/south-west? [1 90] [2 9]))))

(t/deftest move-tail-test
  (t/is (= [2 1]
           (sut/move-tail [ [2 2] [2 1]])))
  (t/is (= [2 1]
           (sut/move-tail [ [2 2] [2 0]])))
  (t/is (= [2 3]
           (sut/move-tail [ [2 2] [2 4]])))
  (t/is (= [2 2]
           (sut/move-tail [ [1 2] [3 2]])))
  (t/is (= [0 1]
           (sut/move-tail [ [0 0] [1 2]])))
  (t/is (= [0 -1] 
           (sut/move-tail [ [0 0] [1 -2]])))
  (t/is (= [0 -1]
           (sut/move-tail [ [0 0] [-1 -2]])))
  (t/is (= [-1 0]
           (sut/move-tail [ [0 0] [-2 1]]))))

(t/deftest  follow-motion-test
  (t/is (= [[3 0] [ [0 0] [1 0] [2 0] [3 0]]]
           (sut/follow-motion [[0 0] []] [[1 0] [2 0] [3 0] [4 0]])))
  (t/is (= [ [4 3] [ [0 0] [1 0] [2 0] [3 0] [3 0] [4 1] [4 2] [4 3]]]
           (sut/follow-motion 
            [[3 0] [ [0 0] [1 0] [2 0] [3 0]]] [[4 1] [4 2] [4 3] [4 4]]))))

(t/deftest gen-head-poses-test
  (t/is (= [ [1 0] [2 0] [3 0] [4 0]  [4 1] [4 2] [4 3] [4 4] ]
           (sut/gen-head-poses ["R 4"
                                "U 4"]))))

(sut/gen-head-poses sample)


(t/deftest positions-visited-test
  (t/is (= 13
           (sut/positions-visited-2-knots sample)))
  (t/is (= 6236
           (sut/positions-visited-2-knots input))) )

(t/deftest positions-visited-many-knots-test
  (t/is (= 1
           (sut/positions-visited-10-knots sample)))
  (t/is (= 36
           (sut/positions-visited-10-knots large-sample)))
  (t/is (= 2449
           (sut/positions-visited-10-knots input))) )

