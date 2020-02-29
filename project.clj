(defproject ntestoc3/difft "0.1.0-SNAPSHOT"
  :description "diff helper"
  :url "https://github.com/ntestoc3/difft"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [lambdaisland/deep-diff "0.0-47"]
                 [jansi-clj "0.1.1"]
                 [org.apache.commons/commons-text "1.8"] ;; text diff
                 [org.bitbucket.cowwoc/diff-match-patch "1.2"] ;; google diff patch
                 ]
  :profiles {:dev {:dependencies [[midje "1.9.9" :exclusions [org.clojure/clojure]]
                                  [criterium "0.4.5"]
                                  ]
                   :plugins [[lein-midje "3.2.1"]]}}
  :repl-options {:init-ns difft.core})
