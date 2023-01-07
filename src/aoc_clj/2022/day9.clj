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


(def relative-poses (for [x (range -1 2)
                          y (range -1 2)]
                      [x y]))


(defn touching? [head tail]
  (let [next-to-head (set (map (partial map + head) relative-poses))]
    (next-to-head tail)))

(defn- same-column? [[a b] [c d]]
  (= a c))

(defn tail-above-head? [head-y tail-y]
  (pos? (- tail-y head-y)))

(defn- gen-poses [x start-y end-y f]
  (map
   (comp f (partial vector x))
   (range start-y end-y)))

(defn- gen-column-poses [[a b :as head] [c d :as tail]]
  (if (tail-above-head? b d)
    (gen-poses a (inc b) d identity)
    (gen-poses a (inc d) b identity)))

(defn- same-row? [[a b] [c d]]
  (= b d))

(defn- right-of-head? [head-x tail-x]
  (pos? (- tail-x head-x)))

(defn- gen-row-poses [[a b :as head] [c d :as tail]]
  (if (right-of-head? a c)
    (gen-poses b (inc a) c rseq)
    (gen-poses b (inc c) a rseq)))

(defn north-east? [head tail]
  (let [[x y] (map - tail head)]
    (and (pos? x) (pos? y))))

(defn south-east? [head tail]
  (let [[x y] (map - tail head)]
    (and (pos? x) (neg? y))))

(defn north-west? [head tail]
  (let [[x y] (map - tail head)]
    (and (neg? x) (pos? y))))

(defn  south-west? [head tail]
  (let [[x y] (map - tail head)]
    (and (neg? x) (neg? y))))

(defn- gen-diagonal-poses [[a b :as head] [c d :as tail]]
  (cond
    (north-east? head tail) (gen-poses a (inc b) d identity)
    (south-east? head tail) (gen-poses a (inc d) b  identity)
    (north-west? head tail) (gen-poses  a (inc b) d identity)
    (south-west? head tail) (gen-poses a (inc d) b identity)))

(defn- positions-touched [head tail]
  (cond
    (same-column? head tail) (gen-column-poses head tail)
    (same-row? head tail) (gen-row-poses head tail)
    :else  (gen-diagonal-poses head tail)))

(defn move-tail [[head tail]]
(if (touching? head tail)
  []
 (positions-touched head tail)))

(north-east?  [0 2] [4 3] )
(move-tail [ [0 2] [4 3] ])
(touching? [0 2] [4 3])
(map - [1  3] [0 0])
(range 3 3)
