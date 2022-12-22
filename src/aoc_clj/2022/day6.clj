(ns aoc-clj.2022.day6)

(defn find-marker-size [size buffer]
  (->> buffer
       (partition-all size 1)
       (map-indexed vector)
       (filter (comp (partial = size) count set second))
       ffirst
       (+ size)))

(def packet-marker-size
    (partial find-marker-size 4))

(def message-marker-size
  (partial find-marker-size 14))

