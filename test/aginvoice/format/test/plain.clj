(ns aginvoice.format.test.plain
  (:use midje.sweet)
  (:use aginvoice.structs)
  (:use aginvoice.examples)
  (:use aginvoice.utils)
  (:use aginvoice.format.plain))

(fact
 (plain-address (struct-map address
		       :street "The Street"
		       :number 1
		       :zip "123456"
		       :city "City")) => (seq "The Street 1\n123456\nCity"))

(fact
 (plain-separator) => (seq "\n---------------------------------------\n"))

(fact
 (plain-address (struct-map address
		       :street "The Street"
		       :number 1
		       :zip "123456"
		       :city "City")) => (seq "The Street 1\n123456\nCity"))

(fact (plain-company
       (struct-map company
	 :reference "Reference"
	 :name "The Company"
	 :address ...address...)) => (seq "The Company\nReference\n[address]")
	 (provided
	  (plain-address ...address...) => "[address]"))

(facts
 (plain-items []) => '()
 (plain-items [(struct item 2 "item1" 980)]) => (seq "2\titem1\t980")
 (plain-items [(struct item 40 "programming" 1200)
	       (struct item 1 "course" 150000)]) => (seq "40\tprogramming\t1200\n1\tcourse\t150000"))

(facts
 (plain-total [(struct item 1 "item 1" 1000)]) => (seq "delsumma\t1000\nmoms 25%\t250\ntotalt\t1250")
 (plain-total [(struct item 1 "item 1" 1000)
	       (struct item 10 "item 2" 2000)]) => (seq "delsumma\t21000\nmoms 25%\t5250\ntotalt\t26250"))

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

