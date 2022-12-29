(ns aoc-clj.2022.day8)

(defn exterior-trees [grid]
  (- (* 4 (count grid)) 4) )

(defn interior-rows [grid]
  (->> grid
       rest
       butlast
       vec))

(defn all-columns [grid]
  (vec (apply map vector grid)))

(defn interior-columns [grid]
  (->> (all-columns grid)
       rest
       butlast
       vec))

(defn all-trees-pos [grid]
  (for [x (range  (count grid))
        y (range  (count grid))]
    [x y]))


(defn interior-trees-pos [grid]
  (for [x (range 1 (dec (count grid)))
        y (range 1 (dec (count grid)))]
    [x y]))


(defn- remove-pos [pos block]
  (let [[before after] (split-at pos block)]
    [(reverse before) (rest after)]))

(defn current-pos [columns x y]
  (get-in columns [x  y]))

(defn altered-trees [x y rows columns]
  (apply conj (remove-pos x (nth rows y ))
         (remove-pos y (nth columns x ))))

(defn- row+columns-to-pos [rows columns [x y :as pos]]
  (apply vector (current-pos columns x y)
         (altered-trees x y rows columns)))

(defn rows+columns-to-check [rows columns tree-poses]
  (map (partial row+columns-to-pos rows columns) tree-poses))

(defn- tallest? [tree-height trees]
  (every? (partial > tree-height) trees))


(defn visible? [[tree-height & trees]]
  (let [f (partial some (partial tallest? tree-height))]
    (f trees)))

(defn ->grid [input]
  (mapv (partial mapv #(Character/digit % 10)) input))


(defn trees-to-check [grid]
  (rows+columns-to-check
   grid
   (all-columns grid)
   (interior-trees-pos grid)))

(defn visible-trees [input]
  (->> input
       ->grid
       trees-to-check
       (filter visible?)
       count
       (+ (exterior-trees input))))


(defn viewing-score [tree-height trees]
  (let [shorter-trees (count (take-while (partial > tree-height) trees)) ]
    (if (= (count trees) shorter-trees)
      shorter-trees
      (inc shorter-trees))))

(defn viewing-scores [[tree-height a b  c d ]]
  [(map (partial viewing-score tree-height) [a b])
   (map (partial viewing-score tree-height) [c d])])

(defn scenic-score [viewing-scores]
  (->> viewing-scores
       flatten
       (apply *)))

(defn highest-scenic-score [grid]
  (let [trees-to-check (rows+columns-to-check grid (all-columns grid) (all-trees-pos grid))]
    (->> trees-to-check
         (map viewing-scores)
         (map scenic-score)
         (apply max))))
