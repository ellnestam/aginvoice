(ns aginvoice.format.plain
  (:use midje.sweet)
  (:use aginvoice.structs)
  (:use aginvoice.examples)
  (:use aginvoice.utils)
  (:use aginvoice.checkers))

(unfinished )

(defn plain [inv]
  "formats an invoice as plain text (a lazy sequence of characters)"
  (let [from (inv :from) from-addr (from :address)]
		    (lazy-cat
		       (from :name) [\newline]
		       (from :reference) [\newline]
		       (from-addr :street) " " (str (from-addr :number)) [\newline]
		       (from-addr :zip) [\newline]
		       (from-addr :city))))

(fact
 (plain (example-invoice)) => (has-chunk '("The company"
					   "The reference"
					   "street 4711"
					   "11878"
					   "Stockholm"))
 (provided (example-provider) => (struct-map company :name "The company"
						     :address (struct address "street" 4711 "11878" "Stockholm")
					             :reference "The reference")))