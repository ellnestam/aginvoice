(ns aginvoice.examples
  (:use aginvoice.structs))
(defn example-items [])
(defn example-customer [])
(defn example-provider-address [] (struct address "Provider street" 12 "12345" "Stockholm"))
(defn example-provider [] (struct-map company
			    :name "Some Company"
			    :address (example-provider-address)
			    :reference "Some Reference"))
(defn example-invoice []
  (struct-map invoice
    :from (example-provider)
    :to (example-customer)
    :items (example-items)))
