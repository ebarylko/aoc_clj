(ns aoc-clj.2022.day5-test
  (:require [aoc-clj.2022.day5 :as sut]
            [clojure.string :as s]
            [clojure.test :as t :refer [is deftest]]))

(def input
  (->> "resources/2022/day5.txt"
       slurp
       s/split-lines
  ))


(t/deftest parse-crate-level-test
  (is (= [ \T \V nil nil \W]
         (sut/parse-crate-level "[T] [V]         [W]"))))

(deftest construct-stack-test
  (is (= [ [\T \N] [\V] [] [] [] [\W \X]]
         (sut/construct-stack
          ["[T]                 [W]"
           "[N] [V]             [X]"]))))

(deftest parse-instruction-test
  (is (= [7 2 8] (sut/parse-instruction
                  "move 7 from 3 to 9") )))

(deftest move-crates-test
  (is (= [ [] [\V] [\N \T] ]
         (sut/move-crates
          identity
          [ '(\T \N) '(\V) '()]
          [2 0 2])))

  (is (= [ [] [\V] [\T \N] ]
         (sut/move-crates
          reverse
          [ '(\T \N) '(\V) '()]
          [2 0 2])))

  (is (= [ [] [\N \T \V] [\W \X]]
         (sut/move-crates
          identity
          [ '(\T \N) '(\V) [\W \X]]
          [2 0 1])
         )))

(def sample
  ["    [D]    "
   "[N] [C]    "
   "[Z] [M] [P]"
   " 1   2   3 "
   ""
   "move 1 from 2 to 1"
   "move 3 from 1 to 3"
   "move 2 from 2 to 1"
   "move 1 from 1 to 2"])


(deftest rearrange-crates-test
  (is (= [[\C] [\M] [\Z \N \D \P]]
         (sut/rearrange-single-crates sample)))
  (is (= [[\M] [\C] [\D \N \Z  \P]]
         (sut/rearrange-many-crates sample))))

(deftest top-crates-test
  (is (= "CMZ"
         (sut/top-crates sut/rearrange-single-crates sample)))
  (is (= "LJSVLTWQM"
         (sut/top-crates sut/rearrange-single-crates input)))
  (is (= "MCD"
         (sut/top-crates sut/rearrange-many-crates sample)))
  (is (= "BRQWDBBJM"
         (sut/top-crates sut/rearrange-many-crates input))))
