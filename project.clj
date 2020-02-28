(defproject difft "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [lambdaisland/deep-diff "0.0-47"]
                 [jansi-clj "0.1.1"]
                 [org.apache.commons/commons-text "1.8"] ;; text diff
                 [org.bitbucket.cowwoc/diff-match-patch "1.2"] ;; google diff patch
                 ]
  :profiles {:dev {:dependencies [[midje "1.9.9" :exclusions [org.clojure/clojure]]
                                  ]
                   :plugins [[lein-midje "3.2.1"]]}}
  :repl-options {:init-ns difft.core})
