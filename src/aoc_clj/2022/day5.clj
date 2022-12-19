(ns aoc-clj.2022.day5
  (:require [clojure.string :as s]))

(defn- ->crate [[fst snd]]
  (when (= \[ fst)
    snd))

(defn parse-crate-level [level]
  (->> level 
       (partition-all 4)
       (map ->crate)))

(defn construct-stack [stacks]
  (->> stacks
       (map parse-crate-level)
       (apply map vector)
       (map (partial filter identity))
       (mapv (partial apply list))))

(re-matches
 #"move (\d+) from (\d+) to (\d+)"
            "move 1 from 2 to 1")

(defn parse-instruction [instruction]
  (->> instruction
       (re-matches #"move (\d+) from (\d+) to (\d+)")
       rest
       (mapv read-string)
       (#(-> %
             (update 1 dec)
             (update 2 dec)))))


(defn push-many  [stack f crates]
  (apply conj stack (f crates)))

(defn pop-many [stack quantity]
  (apply list (drop quantity stack)))

(defn move-crates [f stack [quantity from to]]
  (-> stack
      (update from pop-many quantity)
      (update to push-many f (take quantity (stack from)))))
(defn rearrange-crates [f input]
  (let [[stacks _ instructions] (partition-by empty? input)
        stack (construct-stack (butlast stacks))]

    (->> instructions
         (map parse-instruction)
         (reduce (partial move-crates f) stack))))

(def rearrange-single-crates
  (partial rearrange-crates identity))

(def rearrange-many-crates
  (partial rearrange-crates reverse))

(defn top-crates [method instructions]
  (->> instructions
       method
       (map first)
       s/join))
