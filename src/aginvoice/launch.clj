(ns aginvoice.launch
  (:use [aginvoice.format.plain]
	[aginvoice.examples]
	[aginvoice.io]
        [clojure.java.io :only (reader copy )]))

(defn run-aginvoice [args]
   (copy (reader (plain (example-invoice))) *out* ))


