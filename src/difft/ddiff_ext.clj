(ns difft.ddiff-ext
  (:require [puget.printer :as puget]
            [lambdaisland.deep-diff.printer :as dprinter]
            [lambdaisland.deep-diff :as ddiff]
            [clojure.string :as str]))

(def default-color-schema {::dprinter/deletion  [:red]
                           ::dprinter/insertion [:green]
                           ::dprinter/other     [:black]
                           :nil       [:black]
                           :number    [:black]
                           :string    [:black]
                           :character [:black]
                           :symbol    nil
                           :function-symbol [:black]
                           :class-delimiter [:black]
                           :class-name      [:black]
                           :boolean    [:black]
                           :keyword    [:black]
                           :delimiter  [:black]
                           :tag        [:black]})

(defn cprint-str
  "输出到字符串"
  ([diff] (cprint-str diff {:color-scheme default-color-schema}))
  ([diff opts]
   (->> opts
        (dprinter/puget-printer)
        (puget/cprint-str diff))))

(defn print-html
  "输出到html"
  ([diff] (print-html diff {:color-scheme default-color-schema
                            :width 80}))
  ([diff opts]
   (let [r (->> (merge opts {:color-markup :html-inline})
                (cprint-str diff))]
     (-> (str/replace r #"(?im)\R" "<br/>")
         (as-> $
             (str "<div>" $ "</div>"))))))


