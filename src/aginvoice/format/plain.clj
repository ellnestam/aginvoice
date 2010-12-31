(ns aginvoice.format.plain
  (:use midje.sweet)
  (:use aginvoice.structs))

(unfinished )

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



(defn contains-seq [phrase seq]
  "returns true if seq contains phrase" 
  (if (empty? phrase)
    true
    (if (empty? seq)
      false
      (if (= (first phrase) (first seq))
	(recur (rest phrase) (rest seq))
        (recur phrase (rest seq))))))


(defn seq-to-str [s] (String. (into-array (. Character TYPE) s)))

(defn has-chunk [expected-lines] (fn [actual] (contains-seq expected-lines (re-seq #"[^\n]+" (seq-to-str actual)))))


(defn plain [inv] (let [from (inv :from) from-addr (from :address)]
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