(ns difft.diff
  (:import (org.bitbucket.cowwoc.diffmatchpatch DiffMatchPatch
                                                DiffMatchPatch$Operation))
  (:require [clojure.pprint :as pprint]
            [jansi-clj.core :as color]
            [clojure.string :as string])
  (:use clojure.walk))

(defn- str-comparator [x y]
  (compare (str x) (str y)))

(defn- sort-map [m]
  (into (sorted-map-by str-comparator) m))

(defn- sort-set [s]
  (into (sorted-set-by str-comparator) s))

(defn- s-form [f]
  (if (map? f)
    (sort-map f)
    (if (set? f)
      (sort-set f)
      f)))

(defn sort-form [f]
  (postwalk s-form f))

;; google diff

(def diff-markers
  {DiffMatchPatch$Operation/EQUAL [identity " "]
   DiffMatchPatch$Operation/INSERT [(comp color/bold color/green) "+"]
   DiffMatchPatch$Operation/DELETE [(comp color/bold color/red) "-"]})

(defn- format-diff
  [d]
  (let [m (diff-markers (.operation d)) ]
    (format  "%s %s" m (string/replace (.trim (.text d))
                                       #"\n" (str "\n " m " ")))))

(defn- print-diff [d]
  (let [[c-fn m] (diff-markers (.operation d)) ]
    (println (str " " (c-fn m))
             (string/replace (c-fn (.trim (.text d)))
                             #"\n" (str "\n " (c-fn m) " ")))))

(defn print-diffs
  "输出diff到stdout"
  [diffs]
  (doseq [d diffs]
    (print-diff d)))

(defn canonical-form [f]
  (with-out-str (pprint/pprint (sort-form f))))

(defn diff [x y]
  (-> (DiffMatchPatch.)
      (.diffMain
       (canonical-form x)
       (canonical-form y))))

(defn clean-diff [x y]
  (let [dmp (DiffMatchPatch.)
        diffs (.diffMain dmp
                         (canonical-form x)
                         (canonical-form y))]
    (.diffCleanupSemantic dmp diffs)
    diffs))

(defn diff->html
  "输出diff到html"
  [diffs]
  (let [dmp (DiffMatchPatch.)]
    (.diffPrettyHtml dmp diffs)))

;; 莱文斯坦距离，又称Levenshtein距离，是编辑距离的一种。
;;  指两个字串之间，由一个转成另一个所需的最少编辑操作次数。
(defn diff-levenshtein
  "返回diff的莱文斯坦距离"
  [diffs]
  (-> (DiffMatchPatch.)
      (.diffLevenshtein diffs)))

(defn equal-diff?
  "diff结果是否相同"
  [diffs]
  (-> (diff-levenshtein diffs)
      zero?))

