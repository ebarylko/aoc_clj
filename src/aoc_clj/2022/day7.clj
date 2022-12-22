(ns aoc-clj.2022.day7
  (:require [clojure.string :as s]
            [clojure.tools.trace :as tr]))

(def dir? 
  (comp (partial = "dir") first))

(def digits (set (range 48 59)))


(defn- add-file [file-system absolute-path [file-size file-name]]
  [(assoc-in file-system (conj absolute-path file-name) (read-string file-size)) absolute-path])

(defn- ls? [[fst snd]]
  (= ["$" "ls"] [fst snd]))

(defn- cd? [[fst snd]]
  (= ["$" "cd"] [fst snd]))

(defn- change-directory [absolute-path [_ _ folder]]
  (case folder
    "/" ["/"]
    ".." (pop absolute-path)
    (conj absolute-path folder)))

(defn add-dir [file-system absolute-path [_ dir-name]]
  [(assoc-in file-system
             (conj absolute-path dir-name)  {})
   absolute-path])

(defn edit-file-system [[file-system absolute-path] command]
  (cond
    (dir? command) (add-dir file-system absolute-path command)
    (ls? command) [file-system absolute-path] 
    (cd? command) [file-system (change-directory absolute-path command)]
    :else (add-file file-system absolute-path command)))

(defn make-file-system [commands]
  (->> commands
       (map (partial re-seq #"\S+"))
       (reduce edit-file-system [{"/" {}} ["/"]])
       first))

(declare calc-size)

(defn update-size [m k v]
  (if (map? v)
    (let [dir (calc-size v)]
      (-> m
          (update :folders assoc k dir)
          (update :size (fnil + 0) (:size dir))))
    (update m :size (fnil + 0) v)))

(defn children? [node]
  (tr/trace node)
  (or (and (map? node) (:folders node))
      (:folders (second node))))


(defn calc-size [fs]
  (->> fs
       (reduce-kv update-size {})))

(defn dir-sizes [fs]
  (->> fs
       calc-size
       (tree-seq children? children?)
       rest
       (map (comp :size second))))


(defn filter+sum [commands]
  (->> commands
       make-file-system
       dir-sizes 
       (filter (partial >= 100000))
       (apply +)))


(defn smalllest-directory [commands]
  (let [all-dirs (->> commands
                      make-file-system
                      dir-sizes)
        min-size (->> all-dirs
                      first 
                      (- 70000000)
                      (- 30000000))]
    (->> all-dirs
         (filter (partial <= min-size))
         (apply min))))



