(ns aoc-clj.2024.day1
  (:require [clojure.math :as m]))

(defn total-distance [distances]
  (->> distances
       (map sort)
       (apply map (comp abs -))
       (apply +)))

(defn partial-similarity-score
  [right-list [loc-id num-of-appearances]]
  (* num-of-appearances loc-id (get right-list loc-id 0)))

(defn similarity-score
  [[fst snd]]
  (->> (frequencies fst)
       (map (partial partial-similarity-score (frequencies snd)))
       (apply +)))
