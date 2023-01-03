(ns aoc-clj.2022.day1)

(defn- calories? [coll]
  (seq (first coll)))

(defn- calc-total [coll]
  (->> coll
       (map read-string)
       (apply +)))

(defn list->calories [input]
  (->> input 
       (partition-by empty?)
       (filter calories?)
       (map calc-total)))

(defn max-calories [input]
  (->> input
       (list->calories)
       (apply max)))


(defn sum-max-calories [input]
  (->> input
       (list->calories)
       (sort >)
       (take 3)
       (apply +)))
  

