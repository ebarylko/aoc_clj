(ns aoc-clj.2022.day9
  (:require [clojure.string :as s]))


(def ->usable-motion
  (comp #(vector (first %) (read-string (second %)))
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
    (north-west? head tail) (gen-poses a (inc b) d identity)
    (south-west? head tail) (gen-poses a (inc d) b identity)))

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

                                        ;destructure so last alreadt part of head-positions?


(defn gen-head-poses [motions]
  (first (reduce
   (fn [[head-positions head] motion]
     (let [new-poses (move-head head motion)
           new-head (last new-poses)]
     [(concat head-positions new-poses) new-head]))
   [[] [0 0]]
   motions)))

(defn follow-motions [[tail tail-poses] motions]
  (reduce
   follow-motion
   [tail tail-poses]
   (gen-head-poses motions)))

;; (defn positions-visited [motions]
;;   (->> motions
;;        gen-head-poses
;;        (follow-motion [[0 0] []])
;;        ( (comp set second))
;;        count) )

;; (defn gen-poses* [motions]
;;   (->> motions
;;        (follow-motions [[0 0] []])
;;        second
;;        #_#_( (comp set second))
;;        count) )



(defn follow-motion* [head-positions state]
                                        ;(conj
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

;; (defn positions-visited-many-knots [motions]
;;   (let [head-poses ((comp second (partial follow-motion [[0 0] []]) gen-head-poses) motions)]
;;     (->> (repeat 8 [[0 0] []])
;;          (reduce follow-motion* head-poses)
;;          set
;;          count)))

