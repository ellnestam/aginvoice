(ns aginvoice.io
  (:use clojure.java.io))
	
(import '(java.io ByteArrayOutputStream StringReader BufferedReader Reader)
	'(clojure.lang ASeq LazySeq StringSeq))

(defn copy-seq-to-array
  ([s a] (copy-seq-to-array s a 0 (alength a)))
  ([s a off len] (copy-seq-to-array s a off len 0))
  ([s a off len idx] (if (or (empty? s) (= 0 len))
	       idx
	       (let [c (first s)]
		 (aset a (+ off idx) (char c))
		 (recur (rest s) a off (dec len) (inc idx))))))



(defn seq-reader [s]
  (let [cursor (atom s)]
    (proxy [Reader] []
      (read ([] (if (empty? @cursor)
		  -1
		  (let [res (first @cursor)]
		    (swap! cursor rest)
		    (int res))))
	    ([chars] (let [res (take (alength chars) @cursor)]
		       (swap! cursor #(drop (alength chars) %))
		       (copy-seq-to-array res chars)))
	    ([chars off len] (let [res (take len @cursor)]
			       (swap! cursor #(drop len %))
			       (copy-seq-to-array res chars off len)))))))





(extend StringSeq
  IOFactory
  (assoc default-streams-impl
    :make-reader (fn [x opts] (seq-reader x))))
(extend ASeq
  IOFactory
  (assoc default-streams-impl
    :make-reader (fn [x opts] (seq-reader x))))
(extend LazySeq
  IOFactory
  (assoc default-streams-impl
    :make-reader (fn [x opts] (seq-reader x))))




