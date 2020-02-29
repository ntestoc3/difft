(ns difft.freq-test
  (:require [difft.freq :refer :all]
            [midje.sweet :refer :all]))

(fact "similarity"
      (cos-similarity "test" "test") => (roughly 1.0 0.01)

      ;; 按字符统计，与出现顺序无关
      (cos-similarity "cdba" "abde") => (roughly 0.75 0.01)

      (cos-similarity "12" "23") => (roughly 0.5 0.01)

      (cos-similarity "中国人" "人国中") => (roughly 1.0 0.01)

      (cos-similarity "中" "") => (roughly 0.0 0.01)
      )
