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

(defn gen-monkey-num
  "Takes a monkey and its position
  Post: returns a keyword of the monkey and its position"
  [monkey]
  (let [[_ num] (remove-empty-space monkey)]
    (keyword (str "monkey" (first num)))))

(defn throw-to
  "Pre: takes a string repesenting which monkey iten should be thrown to
  Post: returns a keyword of the monkey which item should be thrown to"
  [s]
  (let [[_ _ _ _ _ num] (remove-empty-space s)]
    (keyword (str "monkey" num))))

(defn monkey-info
  "Pre: Takes a collection of information about a monkey, including its starting items, worry level change operation, test, and to which monkeys it throws to
  Post: returns a map with all this information"
  [[monkey-num starting-items operation test if-divisible if-not-divisible]]
  (let  [num   (gen-monkey-num monkey-num)
         items (parse-monkey-items starting-items)
         op (parse-operation operation)
         interest-test (test-operation test)
         [success fail] (map throw-to [if-divisible if-not-divisible])]
    {num
     {:items items 
      :op op 
      :intr-test interest-test
      :success success
      :fail fail} }))

(defn all-monkeys
  "Pre: takes a collection of monkeys
  Post: returns a collection of the form :monkey monkey-info for every monkey"
  [monkeys]
  (into {} (map monkey-info monkeys)))


(defn worry-shift
  "pre: takes an operation to apply on an item
  Post: returns a function that will take a worry level and shift it to the updated value"
  [op]
  (comp int #(Math/floor %) #(/ % 3) op))


(defn new-item-destination
  "Pre: takes a monkey interest test, the monkeys to pass to depending on the outcome of the test, a collection of destinations for the new items, and an item
  Post: returns the updated monkey items list with the new destination for the item"
  [test pass fail dest item]
  (let [monkey-to (if (test item) pass fail)]
    (update dest monkey-to (fnil conj []) item)))

(defn new-item-destinations
  "Pre: takes a collection of items, a monkey interest test, and the monkeys to throw to if test is passed/failed
  Post: returns the new destination of the items, to which monkeys they go to"
  [items test pass fail]
(reduce (partial new-item-destination test pass fail) {} items ))

(defn item-shift
  "Pre: takes a monkey
  Post: returns the destination of the monkey's items"
  [{:keys [items op intr-test success fail]}]
  (let [new-item-vals  (map (worry-shift op) items)]
    (new-item-destinations new-item-vals intr-test success fail)))

(defn new-monkey-lists
  "Pre: takes a collection of monkeys and a specific monkey
  Post: returns the collection updated with the new destinations of the items the specific monkey held"
  [monkeys monkey]
  (let [items (item-shift (monkey monkeys))]
    (for [[mon it] items]
    (-> monkeys
        (assoc-in [monkey :items] [])
        (update-in [mon :items] concat it)))))

(defn round
  "Pre: takes a collection of monkeys
  Post: returns the collection with the updated items list for every monkey"
  [monkeys]
  (reduce new-monkey-lists monkeys (keys monkeys)))


(apply conj [] [1 2 3 4])
(let [{name :name} {:name "hello"}]
  name)
(for [[k v] {:ji :jello}]
  [k v])
(concat [74] [1 2 3])
