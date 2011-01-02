(ns aginvoice.format.plain
  (:use midje.sweet)
  (:use aginvoice.structs)
  (:use aginvoice.examples)
  (:use aginvoice.utils)
  (:use aginvoice.checkers))

(unfinished plain-separator plain-total plain-total plain-items )

(defn plain-address [c-addr]
  (lazy-cat (c-addr :street) " " (str (c-addr :number)) [\newline]
	    (c-addr :zip) [\newline]
	    (c-addr :city)))

(fact
 (plain-address (struct-map address
		       :street "The Street"
		       :number 1
		       :zip "123456"
		       :city "City")) => (seq "The Street 1\n123456\nCity"))

(defn plain-company [c]
   (lazy-cat (c :name) [\newline]
      (c :reference) [\newline]
      (plain-address (c :address))))
      
(fact (plain-company
       (struct-map company
	 :reference "Reference"
	 :name "The Company"
	 :address ...address...)) => (seq "The Company\nReference\n[address]")
	 (provided
	    (plain-address ...address...) => "[address]"))

(defn plain [inv]
  "formats an invoice as plain text (a lazy sequence of characters)"
  (lazy-cat (plain-company (:to inv))
	  (plain-separator)
	  (plain-company (:from inv))
	  (plain-separator)
	  (plain-items (:items inv))
	  (plain-separator)
	  (plain-total (:items inv))))


(fact
 (plain (struct-map invoice
	  :from ...provider...
	  :to ...customer...
	  :items ...items...)) => (seq "Customer/Provider/Items/Sum")
 (provided
  (plain-company ...customer...) => "Customer"
  (plain-company ...provider...) => "Provider"
  (plain-items ...items...) => "Items"
  (plain-total ...items...) => "Sum"
  (plain-separator) => "/"))