(ns aginvoice.examples
  (:use aginvoice.structs))
(defn example-items [] '((struct item 2 "item1" 1200)
			 (struct item 40 "item2" 800)))

(defn example-customer-address [] (struct address "Customer street" 4611 "76543" "Chicago"))
(defn example-customer [] (struct-map company
			    :name "Some customer"
			    :address (example-customer-address)
			    :reference "Customer reference"))


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
