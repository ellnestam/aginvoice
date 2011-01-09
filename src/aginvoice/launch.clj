(ns aginvoice.launch
   (:use aginvoice.format.plain)
   (:use aginvoice.examples))

(defn run-aginvoice [args]
  (do (reduce (fn [x y] (print y)) (plain (example-invoice)))))


