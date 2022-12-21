(ns aoc-clj.2022.day4
  (:require [clojure.string :as s]
            [clojure.set :as st]))

(def split-at-comma #(s/split % #","))

(def split-at-dash
  (partial map #(s/split % #"-")))

(def ->nums 
  (partial map read-string))


(defn ->range [[start end]]
  (range start (inc end)))


(defn in-another-range? [[r1 r2]]
  (or (st/subset? r1 r2)
      (st/subset? r2 r1)))

(defn parse-ranges [line]
  (->> line
       split-at-comma
       split-at-dash
       (map  (comp set ->range ->nums))))

(defn contained-ranges
  ([assignments]
   (contained-ranges in-another-range? assignments))
  ([f assignments]
   (->> assignments
        (map parse-ranges)
        (filter f)
        (count))))

(def overlapping-ranges
  (partial contained-ranges (comp seq (partial apply st/intersection))))

