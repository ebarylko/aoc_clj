(ns aoc-clj.2022.day9
  (:require [clojure.string :as s]))

(defn positions-visited [motions]
  )

(def ->usable-motion
  (comp #(vector (first %) (read-string (second %)))
     #(s/split % #" ")))

(defn move-head [head motion]
  (let [[direction distance] (->usable-motion motion)]
    (case direction
      "R" (update head 0 + distance)
      "L" (update head 0 - distance)
      "U" (update head 1 + distance)
      "D" (update head 1 - distance))))


(conj [1 2 3] [])


(defn move-tail [[head tail]]

  )

(def relative-poses (for [x (range -1 2)
      y (range -1 2)]
  [x y]))



(defn touching? [[head tail]]
  (let [next-to-head (set (map (partial map + head) relative-poses))]
    (next-to-head tail)))
