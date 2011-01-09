(ns aginvoice.format.plain
  (:use aginvoice.structs)
  (:use aginvoice.examples)
  (:use aginvoice.utils)
  (:use midje.sweet))


(defn plain-separator []
  "returns a lazy character sequence representing an item separator"
  (seq "\n---------------------------------------\n"))


(defn plain-address [c-addr]
  "returns a lazy character sequence representing an address"
  (lazy-cat (c-addr :street) " " (str (c-addr :number)) [\newline]
	    (c-addr :zip) [\newline]
	    (c-addr :city)))

(defn plain-company [c]
  "returns a lazy character sequence representing a company"
  (lazy-cat (c :name) [\newline]
	    (c :reference) [\newline]
	    (plain-address (c :address))))
      


(defn plain-items [items]
  "creates a lazy sequence of characters representing all items passed"
  (mapcat (fn [v] v)
	  (interpose "\n"
		     (map (fn [i] (concat (str (i :count)) "\t" (i :spec) "\t" (str (i :price))))
			  items))))

(defn plain-total [items]
  (let [delsumma (reduce #(+ %1 (* (%2 :count) (%2 :price))) 0 items)
	moms (long (* delsumma 0.25))
	total (+ delsumma moms)]
    (concat "delsumma\t" (str delsumma) "\n"
	    "moms 25%\t"     (str moms) "\n"
	    "totalt\t"    (str total))))


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
 (plain-address (struct-map address
		       :street "The Street"
		       :number 1
		       :zip "123456"
		       :city "City")) => (seq "The Street 1\n123456\nCity"))