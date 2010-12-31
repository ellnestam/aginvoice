(ns aginvoice.utils
  "various utility methods for converting to and from strings etc")

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
