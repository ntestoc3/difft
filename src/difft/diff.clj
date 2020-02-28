(ns difft.diff
  (:import (org.apache.commons.text.similarity CosineDistance CosineSimilarity)
           org.apache.commons.text.diff.StringsComparator
           (org.bitbucket.cowwoc.diffmatchpatch DiffMatchPatch
                                                DiffMatchPatch$Operation))
  (:require [clojure.pprint :as pprint]
            [jansi-clj.core :as color]
            [clojure.string :as string]
            [clojure.string :as str])
  (:use clojure.walk))

(defn frequencies-coll
  "按出现次数统计频率"
  [coll]
  (->> (group-by identity coll)
       (map (fn [[k v]] [(str k)
                         (int (count v))]))
       (into {})))

(def frequencies-by-char "按字符统计频率" frequencies-coll)

(defn frequencies-by-word
  "按单词统计频率"
  [s]
  (-> (str/split s #"\s+")
      frequencies-coll))

(defn similarity
  "字符串相似度比较
  `freq-fn` 指定字符串频率统计的方法，默认按单个字符比较
  返回0到1之间的数，越大相似度越高"
  ([s1 s2] (similarity s1 s2 frequencies-by-char))
  ([s1 s2 freq-fn]
   (let [gs1 (freq-fn s1)
         gs2 (freq-fn s2)]
     (-> (CosineSimilarity.)
         (.cosineSimilarity gs1 gs2)))))

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


