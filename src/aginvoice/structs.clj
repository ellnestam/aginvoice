(ns aginvoice.structs
  "datastructures used to represent an invoice")
  
(defstruct company :name :address :reference)
(defstruct address :street :number :zip :city)
(defstruct person :first :last)
(defstruct invoice :from :to :contract :items)
(defstruct item :count :spec :price)
				    




