(ns difft.freq
  (:import (org.apache.commons.text.similarity
            LevenshteinDistance
            CosineDistance
            CosineSimilarity))
  (:require [clojure.string :as str]))

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

(defn cos-similarity
  "字符串相似度比较
  `freq-fn` 指定字符串频率统计的方法，默认按单个字符比较
  返回0到1之间的数，越大相似度越高"
  ([s1 s2] (cos-similarity s1 s2 frequencies-by-char))
  ([s1 s2 freq-fn]
   (let [gs1 (freq-fn s1)
         gs2 (freq-fn s2)]
     (-> (CosineSimilarity.)
         (.cosineSimilarity gs1 gs2)))))


(defn levenshtein-distance
  [s1 s2]
  (-> (LevenshteinDistance.)
      (.apply s1 s2)))
