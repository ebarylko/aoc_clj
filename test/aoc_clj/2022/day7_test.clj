(ns aoc-clj.2022.day7-test
  (:require [aoc-clj.2022.day7 :as sut]
            [clojure.string :as s]
            [clojure.test :as t]
            [clojure.tools.trace :as tr]))

(def input
  (->> "resources/2022/day7.txt"
       slurp
       s/split-lines))

(def sample
  ["$ cd /"
   "$ ls"
   "dir a"
   "14848514 b.txt"
   "8504156 c.dat"
   "dir d"
   "$ cd a"
   "$ ls"
   "dir e"
   "29116 f"
   "2557 g"
   "62596 h.lst"
   "$ cd e"
   "$ ls"
   "584 i"
   "$ cd .."
   "$ cd .."
   "$ cd d"
   "$ ls"
   "4060174 j"
   "8033020 d.log"
   "5626152 d.ext"
   "7214296 k"])

(t/deftest edit-file-system-test
  (t/are [expected state cmd]
      (= expected (sut/edit-file-system state cmd))
    [{"/" {"w" {}}} ["/"]] [{"/" {}} ["/"]] ["dir" "w"]
    [{"/" {"w" {"f" 21} }} ["/" "w"]] [{"/" {"w" {}}} ["/" "w"]] ["21" "f"]
    [{"/" {"w" {} }} ["/" "w"]] [{"/" {"w" {}}} ["/" ]]  ["$" "cd" "w"]
    [{"/" {"w" {} }} ["/"]] [{"/" {"w" {}}} ["/"  "w"]] ["$" "cd" ".."]
    [{"/" {"w" {} }} ["/"]] [ {"/" {"w" {}}} ["/"  "w" "e" "r"]] ["$" "cd" "/"]))


(t/deftest make-file-system-test
  (t/is (= {"/"
            {"a" {"f" 29116 "g" 2557 "h.lst" 62596 "e" {"i" 584}}
             "b.txt" 14848514 "c.dat" 8504156
             "d" {"j" 4060174 "d.log"  8033020  "d.ext" 5626152 "k" 7214296}}}
           (sut/make-file-system sample))))








(t/deftest calc-size-test
  (t/is (= {:size 48381165
            :folders { "/" {:size 48381165
                            :folders {"a" {:size 94853
                                           :folders {"e" {:size 584}}} 
                                      "d" {:size 24933642}}}}}
           (sut/calc-size (sut/make-file-system sample))))
  
  (t/is (= {:size 30
            :folders {"p" {:size 10}}}
           (sut/calc-size {"a" 10 "b" 10 "p" {"w" 10}})))
  (t/is (= {:size 40
            :folders {"p" {:size 10}
                      "l" {:size 10}}}
           (sut/calc-size {"a" 10 "b" 10 "p" {"w" 10} "l" {"k" 10}}))) )


#_ (t/deftest sum-directories-test
   (t/is (= 20
            (sut/sum-directories {:folders {"p" {:size 10}
                                  "l" {:size 99999999
                                       :folders {"j" {:size 10}}}}})))
    )

(t/deftest filter+sum-test
  (t/is (= 95437 (sut/filter+sum sample)))
  (t/is (= 1783610 (sut/filter+sum input))))

(t/deftest smallest-directory-test
  (t/is (= 24933642 (sut/smalllest-directory sample)))
  (t/is (= 4370655 (sut/smalllest-directory input))) )
