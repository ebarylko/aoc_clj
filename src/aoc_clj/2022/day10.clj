(ns aoc-clj.2022.day10
  (:require [clojure.string :as s]))

(defn noop?
  [instr]
  (= \n (first instr)))

(defn instr-val
  [instr]
  (if (noop? instr)
    1
    2))

(defn parse-instruction-value
  "Pre: takes an instuction
  Post: returns the numerical value of the instuction"
  [instr]
  (if (noop? instr)
    0
  (let [[_ value] (s/split instr #" ")]
    (Integer/parseInt value))))

(defn within-time?
  "Pre: takes an amount of cycles C and an instruction value V
  Post: returns true if the difference of C and V is non-negative"
  [cycles instr-val]
  (> (- cycles instr-val) 0))

(defn cycles-left?
  "Pre: takes the amount of cycles left C and the instruction P
  Post: Returns true if P can be completed within C"
  [cycles instr]
(let [instr-value (instr-val instr)]
  (if (within-time? cycles instr-value)
    (- cycles instr-value)
    nil)))

(defn cycles-val
  "Pre: takes current cycle value, cycles left, and an instruction
  Post: if there are cycles left to complete instruction, returns updated cycle value and cycles left. Otherwise returns given cycle value and cycles left"
  [[cyc-val cycles] instr]
  (if (cycles-left? cycles instr)
    [(+ cyc-val (parse-instruction-value instr))
     (cycles-left? cycles instr)]
    (reduced [cyc-val cycles])))

(defn sum-cycles
  "Pre: takes a series of instructions and the number of cycles N
  Post: returns the value of the register at cycle N"
  [instrucs cycles]
  (first (reduce cycles-val [1 cycles] instrucs)))

