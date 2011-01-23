(ns aginvoice.io
  (:use midje.sweet
	clojure.java.io
	aginvoice.io))
(import '(java.io ByteArrayOutputStream StringReader BufferedReader Reader)
	'(clojure.lang ASeq LazySeq StringSeq))

(unfinished )


(defn copy-seq-to-array
  ([s a] (copy-seq-to-array s a 0))
  ([s a i] (if (empty? s)
	     i
	     (let [c (first s)]
	       (aset a i (char c))
	       (recur (rest s) a (inc i))))))
(facts "about copy-seq-to-array"
       (let [array (make-array Character/TYPE 10)]
	     (copy-seq-to-array (seq "hello") array) => (.length "hello")
	     (String. array) => (has-prefix "hello")))


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
		       (copy-seq-to-array res chars)
		       (count res)
		     	    ))))))


(facts "about seq-reader"
       (let [r (seq-reader (seq "abc"))]
	    (.read r) => (int \a)
	    (.read r) => (int \b)
	    (.read r) => (int \c)
	    (.read r) => (int -1))
       (let [r (seq-reader (seq "hello world")) arr (make-array Character/TYPE 6)]
	 (do (.read r arr) => 6
	     (String. arr) => "hello "
	     (.read r arr) => 5
	     (String. arr) => "world "
	     )))

(extend StringSeq
  IOFactory
  (assoc default-streams-impl
    :make-reader (fn [x opts] (seq-reader x))))


(fact
 (.readLine (BufferedReader. (reader (seq "hello-world")))) => "hello-world"
 (provided (seq-reader anything) => (StringReader. "hello-world")))

