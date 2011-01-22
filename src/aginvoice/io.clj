(ns aginvoice.io
  (:use midje.sweet
	clojure.java.io
	aginvoice.io))
(import '(java.io ByteArrayOutputStream StringReader BufferedReader Reader)
	'(clojure.lang ASeq LazySeq StringSeq))

(unfinished )


(defn seq-reader [s]
  (let [cursor (atom s)]
    (proxy [Reader] []
      (read []
	    (if (empty? @cursor)
	      -1
	      (let [res (first @cursor)]
		 (swap! cursor rest)
		 (int res)))))))

(facts "about seq-reader"
       (.read (seq-reader (seq "abc"))) => (int \a)
       (let [r (seq-reader (seq "abc"))]
	    (.read r)
	    (.read r)) => (int \b))


(extend StringSeq
  IOFactory
  (assoc default-streams-impl
    :make-reader (fn [x opts] (seq-reader x))))


(fact
 (.readLine (BufferedReader. (reader (seq "hello-world")))) => "hello-world"
 (provided (seq-reader anything) => (StringReader. "hello-world")))

