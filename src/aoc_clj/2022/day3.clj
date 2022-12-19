(ns aoc-clj.2022.day3
  (:require [clojure.set :as st]))


(defn item-types->priorities [c]
  (cond-> (int c)
    (Character/isUpperCase c) (- 38)
    (Character/isLowerCase c) (- 96)))

(defn- str->compartments [str]
  (let [mid (/ (count str) 2)]
    (split-at mid str)))

(def ->sets 
  (partial map set ))

(defn- find-shared-type [coll]
  (first (apply clojure.set/intersection coll)))

(defn- sum-priorities [f coll]
  (->> coll
       (f)
       (map ->sets)
       (map find-shared-type)
       (map item-types->priorities)
       (apply +)))

(def priority-sum
   (partial sum-priorities (partial map str->compartments)))

(def sum-badges
  (partial sum-priorities (partial partition 3)))


(def a #{1 2 3})
(def b #{1 2})
(filter a b)
