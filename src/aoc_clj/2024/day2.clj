(ns aoc-clj.2024.day2
  (:require [clojure.math :as m]))

(defn increasing-or-decreasing-seq?
  [[fst  :as coll]]
  (let [f #(= (m/signum fst) (m/signum %))]
    (every? f coll)))


(defn levels-differ-gradually?
  [coll]
  (every? #(< 0 (abs %) 4) coll))

(def valid-report? (every-pred increasing-or-decreasing-seq? levels-differ-gradually?))

(defn safe?
  [report]
  (->> report
       (partition 2 1)
       (map (partial apply -))
       valid-report?))

(defn num-of-safe-reports
  [reports]
  (->> reports
       (filter safe?)
       count))

(defn gen-report-variation
  [report idx]
(concat (take (dec idx) report) (drop idx report)))

(defn can-be-made-safe?
  [report]
  (->> (range 1 (inc (count report)))
       (map (partial gen-report-variation report))
       (some safe?)))

(defn num-of-safe-reports*
  [reports]
  (->> reports
       (remove safe?)
       (filter can-be-made-safe?)
       count
       (+ (num-of-safe-reports reports))))
