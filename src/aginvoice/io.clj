(ns aginvoice.io
  (:use midje.sweet
	clojure.java.io
	aginvoice.io))
(import '(java.io ByteArrayOutputStream StringReader BufferedReader Reader)
	'(clojure.lang ASeq LazySeq StringSeq))

(unfinished )


(defn- copy-seq-to-array
  ([s a] (copy-seq-to-array s a 0 (alength a)))
  ([s a off len] (copy-seq-to-array s a off len 0))
  ([s a off len idx] (if (or (empty? s) (= 0 len))
	       idx
	       (let [c (first s)]
		 (aset a (+ off idx) (char c))
		 (recur (rest s) a off (dec len) (inc idx))))))

;.;. Any intelligent fool can make things bigger, more complex, and more
;.;. violent. It takes a touch of genius -- and a lot of courage -- to move
;.;. in the opposite direction. -- Schumacher

(facts "about copy-seq-to-array"
       (let [array (make-array Character/TYPE 10)]
	     (copy-seq-to-array (seq "hello") array) => (.length "hello")
	     (String. array) => (has-prefix "hello"))
       (let [array (make-array Character/TYPE 11)]
	 (copy-seq-to-array (seq " hello") array 5 6) => 6
	 (copy-seq-to-array (seq "world") array 0 10) => 5
	 (String. array) => "world hello"))
       


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



(facts "about seq-reader"
       (let [r (seq-reader (seq "abc"))]
	    (.read r) => (int \a)
	    (.read r) => (int \b)
	    (.read r) => (int \c)
	    (.read r) => (int -1))
       (let [r (seq-reader (seq "hello world")) arr (make-array Character/TYPE 6)]
	 (.read r arr) => 6
	     (String. arr) => "hello "
	     (.read r arr) => 5
	     (String. arr) => "world ")
       (let [r (seq-reader (seq "goodbye cruel world")) arr (make-array Character/TYPE 19)] 
	  (.read r arr 12 7) => 7
	  (.read r arr 11 1) => 1
	  (.read r arr 0 11) => 11
	  (String. arr) => "cruel world goodbye"))

(extend StringSeq
  IOFactory
  (assoc default-streams-impl
    :make-reader (fn [x opts] (seq-reader x))))


(fact
 (.readLine (BufferedReader. (reader (seq "hello-world")))) => "hello-world"
 (provided (seq-reader anything) => (StringReader. "hello-world")))

