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

(def dummy-contract {:programming (struct type "Programmering" 980)
		     :mikado-intro (struct type "Mikado introduktion" 15000)})
(def dummy-invoice (struct-map invoice
		     :from dummy-provider
		     :to dummy-customer
		     :contract dummy-contract
		     :items '((struct item :programming "V12" 48)
			      (struct item :programming "V13" 40)
			      (struct iten :mikado-intro 1))))
					



