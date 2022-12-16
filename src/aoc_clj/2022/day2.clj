(ns aoc-clj.2022.day2)

(def rock-scenario
  {\X 4 \Y 8 \Z 3})

(def paper-scenario
  {\X 1 \Y 5 \Z 9   })

(def scissor-scenario
  {\X 7 \Y 2 \Z 6})

(def coll->pairs 
  (juxt first last))

(def scenarios
  {\A rock-scenario
   \B paper-scenario
   \C scissor-scenario})

(defn- rps-scenario [[p1 p2]]
  ((scenarios p1) p2))

(defn rps-score
  ([coll] (rps-score identity coll))
  ([f coll] 
   (->> coll
        (map (comp rps-scenario f coll->pairs))
        (apply +))))


(def decrypt-rock-scenario
  {\X \Z \Y \X \Z \Y })

(def decrypt-scissor-scenario
  (clojure.set/map-invert decrypt-rock-scenario))


(def decrypt-scenario
  {\A decrypt-rock-scenario
   \B identity
   \C decrypt-scissor-scenario})

(defn- decrypt-rps [[p1 p2]]
  (conj [p1]
        ((decrypt-scenario p1) p2)))

(def decrypted-rps-score 
  (partial rps-score decrypt-rps))

