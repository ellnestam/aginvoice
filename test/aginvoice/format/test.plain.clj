(ns aginvoice.format.test-plain
  (:use midje.sweet)
  (:use aginvoice.structs))

(unfinished )

(defn example-items [])

(defn example-customer [])

(defn example-provider [])

(defn example-invoice []
  (struct-map invoice
    :from (example-provider)
    :to (example-customer)
    :items (example-items)))



(defn contains-seq [phrase seq]
  "returns true if seq contains phrase" 
  (if (empty? phrase)
    true
    (if (empty? seq)
      false
      (if (= (first phrase) (first seq))
	(recur (rest phrase) (rest seq))
        (recur phrase (rest seq))))))


(defn has-chunk [expected-lines] (fn [actual] (contains-seq expected-lines (re-seq #"[^\n]+" actual))))


(defn plain [inv] (:street (inv :from)))

(fact
 (plain (example-invoice)) => (has-chunk '("street 4711"
					 "11878"
					 "Stockholm"))
 (provided (example-provider) => (struct address "street2" 4711 "11878" "Stockholm")))
