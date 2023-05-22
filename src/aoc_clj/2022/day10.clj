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

(defn signal-strength
  "Pre: Takes a series of instructions and the number of cycles N
  Post: Returns the product of the value of the register at cycle N and N"
  [instr cycles]
  (->> cycles
       (sum-cycles instr)
       (* cycles)))

(def important-cycles
  [20 60 100 140 180 220])

(defn sum-signal-strengths
  "Pre: Takes a series of instructions
  Post: Returns the sum of the signal strengths at the 20th, 60th, 100th, 140th, 180th, and 220th cycle"
  [instr]
(apply + (map (partial signal-strength instr) important-cycles)))

(def generate-empty-line
  (repeat 40 ["."]))

(defn gen-sprite-loc
  [pos]
 #{(dec pos) pos (inc pos)})

(defn sprite-at-pos?
  [sprite-pos cyc]
  (sprite-pos cyc))


(defn draw-noop-line
  "Pre: Takes a line, position of the sprite, and the cycle
  Returns the line with the postition of the sprite noted, the position of the sprite, and the current cycle"
  [line pos cyc]
  (let [sprite-pos (gen-sprite-loc pos)]
    (if (sprite-at-pos? sprite-pos cyc)
      [(conj line ["#"]) pos (inc cyc)]
      [(conj line ["."]) pos (inc cyc)])))

(defn draw-addx-line
  "Pre: Takes a line, position of the sprite, the cycle, and the position to add to the sprite
  Returns the line with the postition of the sprite noted, the new position of the sprite, and the current cycle"
  [line pos cyc instr-val]
  (let [[new-line old-pos new-cyc ] (draw-noop-line line pos cyc)]
    (update-in (draw-noop-line new-line old-pos new-cyc) [1] + instr-val)))

(defn draw-line
  "Pre: Takes a line, the position of the sprite, the cycle, and the instruction
  Post: Returns the line with the position of the sprite noted at the cycles for which the instruction occured, the position of the sprite, and the cycle"
  [[line pos cyc] instr]
  (if (noop? instr)
    (draw-noop-line line pos cyc)
    (draw-addx-line line pos cyc (parse-instruction-value instr))))

(defn draw-lines
  "Pre: takes a series of instructions, the starting position of the sprite, and the starting cycle
  Post: returns the line after following the instructions and nting the position of the sprite at every one."
  [instrucs pos cyc]
  (first (reduce draw-line [[] 1 0] instrucs)))

(defn render-lines
"Pre: takes a series of instructions, the starting position of the sprite, and the starting cycle
  Post: returns the line after following the instructions and nting the position of the sprite at every one."
  []

  )
(conj [[2]] 3)
(flatten [["."] "#"])
(#{2 3} 2)
(draw-noop-line [] 11 12)
(draw-line (draw-line [[] 1 0] "addx 15 ")
           "addx -11 ")

(draw-lines ["addx 15"] 1 0)
