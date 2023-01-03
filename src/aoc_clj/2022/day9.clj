(ns aoc-clj.2022.day9
  (:require [clojure.string :as s]))


(def ->usable-motion
  (comp #(vector
       (first %)
       (read-string (second %)))
   #(s/split % #" ")))

(defn move-in-direction [head pos f]
  (partial map (partial update head pos f)))

(defn move-head [head motion]
  (let [[direction distance] (->usable-motion motion)
        intermediate-steps (range 1 (inc distance))]
    (case direction
      "R"  ( (move-in-direction head 0 +) intermediate-steps)
      "L"((move-in-direction head 0 -) intermediate-steps)
      "U"((move-in-direction head 1 +) intermediate-steps)
      "D"((move-in-direction head 1 -) intermediate-steps))))

(defn touching? [[a b :as head] [c d :as tail]]
  (let [x (- c a)
        y (- d b)
        distance (+ (Math/pow x 2) (Math/pow y 2))]
    (<= distance 2)))


(defn correct-pos [num]
  (if (pos? num)
    (Math/ceil num)
    (Math/floor num)))

(defn- positions-touched [head tail]
  (->> tail
       (map - head)
       (map (partial * 0.5))
       (map correct-pos)
       (map + tail)
       (map int)))


(defn move-tail [[head tail]]
(if (touching? head tail)
  tail
 (positions-touched head tail)))


(defn follow-motion [[tail tail-positions :as tail-info] head-positions]
   (reduce
   (fn [[tail-pos tail-history] head-pos]
     [(move-tail [head-pos tail-pos])
      (conj tail-history (move-tail [head-pos tail-pos]))])
   [tail tail-positions]
   head-positions))

(defn gen-head-poses [motions]
  (first (reduce
   (fn [[head-positions head] motion]
     (let [new-poses (move-head head motion)
           new-head (last new-poses)]
     [(concat head-positions new-poses) new-head]))
   [[] [0 0]]
   motions)))



(defn follow-motion* [head-positions state]
  (second (reduce
           (fn [[tail-pos tail-history] head-pos]
             (let [new-pos (move-tail [head-pos tail-pos])]
               [new-pos (conj tail-history new-pos)]))
           state
           head-positions)))

(defn positions-visited [knots motions]
  (let [head-poses ((comp second (partial follow-motion [[0 0] []]) gen-head-poses) motions)]
    (->> (repeat (- knots 2) [[0 0] []])
         (reduce follow-motion* head-poses)
         set
         count)))

(def positions-visited-2-knots
  (partial positions-visited 2))

(def positions-visited-10-knots
  (partial positions-visited 10))


