(ns aginvoice.test.io-facts
  (:use midje.sweet
	clojure.java.io
	aginvoice.io))
(import '(java.io ByteArrayOutputStream StringReader BufferedReader Reader)
	'(clojure.lang ASeq LazySeq StringSeq))


(facts "about copy-seq-to-array"
       (let [array (make-array Character/TYPE 10)]
	     (copy-seq-to-array (seq "hello") array) => (.length "hello")
	     (String. array) => (has-prefix "hello"))
       (let [array (make-array Character/TYPE 11)]
	 (copy-seq-to-array (seq " hello") array 5 6) => 6
	 (copy-seq-to-array (seq "world") array 0 10) => 5
	 (String. array) => "world hello"))

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

(fact
 (.readLine (BufferedReader. (reader (seq "hello-world")))) => "hello-world"
 (provided (seq-reader anything) => (StringReader. "hello-world")))