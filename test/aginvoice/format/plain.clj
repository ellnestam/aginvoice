(ns aginvoice.format.plain
  (:use midje.sweet)
  (:use aginvoice.structs))

(unfinished )

(defn test-items [])

(defn test-customer [])

(defn test-provider [])

(defn test-invoice []
  (struct-map invoice
    :from (test-provider)
    :to (test-customer)
    :items (test-items)))


(defn has-chunk [expected-lines] (fn [actual] nil))


(defn plain [inv] inv)


(facts
 (plain (test-invoice)) => (has-chunk '("Provider AB"
					"provider street 4711"
					"Stockholm")))


