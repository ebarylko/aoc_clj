(ns aoc-clj.2022.day11
  (:require [clojure.string :as s]))


(def remove-empty-space
  #(remove empty? (s/split % #" ")))


(defn test-operation
  "Pre: takes the monkey interest process
  Post: returns a predicate that takes a worry value, and returns true if the monkey is interested. False otherwise"
  [process]
  (let [num (-> (remove-empty-space process)
                last
                Integer/parseInt)]
    #(zero? (mod % num))))

(defn multiplication?
  "Pre: takes a string representing an operator
  Post: returns true if the operator is multiplication, false otherwise"
  [op]
  (= "*" op))

(defn multiplication-or-addition
  "Pre: takes a string representing addition or subtraction
  Post: returns the function being represented"
  [op]
  (if (multiplication? op)
    *
    +))

(defn num-value
  "Pre: takes a string representing a number
  Post: returns the numerical value of the string"
  [num]
  (Integer/parseInt num))

(defn determine-operation
  "Pre: takes a string reprenting a mathematical operation and a string representation of a number N
  Post: returns a function representing the operation that takes two numbers, one being the integer representation of N"
  [op num]
  (let [op (multiplication-or-addition op)
        val (num-value num)]
    (partial op val)))


(def addition? (complement multiplication?))

(defn doubling?
  "Pre: takes an operation and a number
  Post: returns true if applying the operation would double the number"
  [op num]
  (and (addition? op)
       (= "old" num)))


(defn squaring?
  "Pre: takes an operation and a number
  Post: returns true if the operation squares the number"
  [op num]
  (and (multiplication? op)
       (= "old" num)))

(defn parse-operation
  "Pre: takes a string describing the worry level shift of an item for a monkey
  Post: returns a function that takes a worry level and applies the shift"
  [worry-shift]
  (let [ [_ _ _ _ operation num] (remove-empty-space worry-shift) ]
    (cond
      (squaring? operation num)  #(Math/pow % 2)
      (doubling? operation num) (partial * 2)
      :else (determine-operation operation num))))

    

(defn parse-item
  "Pre: Takes a string representing the value of a item
  Post: returns the value of that item as an integer"
  [item]
    (Integer/parseInt (apply str (take 2 item))))

(defn parse-monkey-items
  "Pre: takes a string of the monkey's starting items
  Post: returns the value of the starting items"
  [items]
  (let [[_ _ & items] (remove-empty-space items)
        valid-items (map parse-item items)]
    valid-items))

(defn monkey-info
  "Pre: Takes a collection of information about a monkey, including its starting items, worry level change operation, test, and to which monkeys it throws to
  Post: returns a map with all this information"
  [[monkey-num starting-items operation test if-divisible if-not-divisible]]
  (let  [[_ num]   (s/split monkey-num #" ")
         items (parse-monkey-items starting-items)
         op (parse-operation operation)
         interest-test (test-operation test)]
    {:items items
     :op op
     :intr-test interest-test}))

(keyword "0:")
(Integer/parseInt "1")
(s/includes? "48," ",")
(apply str (take 2 "48,"))
(str \4 \8)
(take 2 "48")
(s/split "      2 3l" #" ")
(remove empty? (s/split " Starting items: 79, 60, 97 " #" "))
