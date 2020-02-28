(ns difft.diff-test
  (:require [difft.diff :refer :all]
            [midje.sweet :refer :all]))


(fact "similarity"
      (similarity "test" "test") => (roughly 1.0 0.01)

      ;; 按字符统计，与出现顺序无关
      (similarity "cdba" "abde") => (roughly 0.75 0.01)

      (similarity "12" "23") => (roughly 0.5 0.01)

      (similarity "中国人" "人国中") => (roughly 1.0 0.01)

      (similarity "中" "") => (roughly 0.0 0.01)
      )

(fact "diff"
      (diff-levenshtein (diff {:a 1} {:a 2})) => 1

      (diff-levenshtein (diff {:a 1 :c "testab" :b 0} {:a 2 :c "texab" :b 0})) => 3


      (equal-diff? (diff "中文" "中文")) => true

      )
