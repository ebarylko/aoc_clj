(ns aoc-clj.2022.day11
  (:require [clojure.string :as s]))


(defn parse-item
  "Pre: Takes a string representing the value of a item
  Post: returns the value of that item as an integer"
  [item]
    (Integer/parseInt (apply str (take 2 item))))

(defn parse-monkey-items
  "Pre: takes a string of the monkey's starting items
  Post: returns the value of the starting items"
  [items]
  (let [[_ _ & items] (s/split items #" ")
        valid-items (map parse-item items)]
    valid-items))

(defn monkey-info
  "Pre: Takes a collection of information about a monkey, including its starting items, worry level change operation, test, and to which monkeys it throws to
  Post: returns a map with all this information"
  [[monkey-num starting-items operation test if-divisible if-not-divisible]]
  (let  [[_ num]   (filter empty? (s/split monkey-num #" "))
         items (parse-monkey-items starting-items)]
    (Integer/parseInt (str (first num)))
    (s/split starting-items #" ")
    items))

(keyword "0:")
(Integer/parseInt "1")
(s/includes? "48," ",")
(apply str (take 2 "48,"))
(str \4 \8)
(take 2 "48")
(s/split "      2 3l" #" ")
