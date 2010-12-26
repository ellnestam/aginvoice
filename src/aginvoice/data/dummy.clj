(ns aginvoice.data.dummy
  "Dummy structures to test invoices with"
  (:use aginvoice.structs))

(def dummy-address (struct address "SomeStreet" 4711
			           11233 "Stockholm"))

(def dummy-reference (struct person "John" "Doe"))

(def dummy-customer (struct-map company
				 :name "Dummy customer"
				 :address dummy-address
				 :reference dummy-reference))

(def dummy-provider-address (struct address "Provierstrasse" 12
				    15678 "Hamburg"))

(def dummy-provider-reference (struct person "Jürgen" "Düe"))

(def dummy-provider (struct-map company
		      :name "Dummy provider"
		      :address dummy-provider-address
		      :reference dummy-provider-reference))

(def dummy-invoice (struct-map invoice
		     :from dummy-provider
		     :to dummy-customer
		     :items '((struct item 48 "Programmering V12" 925)
			      (struct item 40 "Programmering V13" 925)
			      (struct item 1 "Mikado introduktion" 15000))))
					



