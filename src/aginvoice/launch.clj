(ns aginvoice.launch
  (:use [aginvoice.format.plain]
	[aginvoice.examples]
	[aginvoice.io]
        [clojure.java.io :only (reader copy )]))

(defn formats [format]
  (get {:plain plain} format))

(defn source [format invoice]
  ((formats format) invoice))

(defn target [tgt]
  (get {:default *out*} tgt))

(defn parse
  ([args]         (parse args {}))
  ([args context] (assoc context (keyword (first args)) (keyword (second args)))))
  
(defn run-aginvoice [args]
  (let [parsed-args (parse args)]
    (copy (reader (source (parsed-args :format) (example-invoice))) (target :default))))
