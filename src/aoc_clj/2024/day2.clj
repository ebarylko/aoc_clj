(ns aoc-clj.2024.day2)



(defn increasing-or-decreasing-seq?
  [coll]
(or (every? neg? coll) (every? pos? coll)))

(defn levels-differ-gradually?
  [coll]
  (every? #(< 0 (abs %) 4) coll))

(def valid-report? (every-pred increasing-or-decreasing-seq? levels-differ-gradually?))

(defn is-safe?
  [report]
  (->> report
       (partition 2 1)
       (map (partial apply -))
       valid-report?))

(defn num-of-safe-reports
  [reports]
  (->> reports
       (filter is-safe?)
       count))
