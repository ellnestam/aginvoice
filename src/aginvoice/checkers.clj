(ns aginvoice.checkers
  "checkers for midje"
  (:use aginvoice.utils))

(defn has-chunk [expected-lines]
  "checks if the expected chunk exists in the sequence"
  (fn [actual] (contains-seq expected-lines (re-seq #"[^\n]+" (seq-to-str actual)))))
