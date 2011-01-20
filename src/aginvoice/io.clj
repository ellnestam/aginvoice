(ns aginvoice.io
  (:use midje.sweet
	clojure.java.io
	aginvoice.io))
(import '(java.io ByteArrayOutputStream StringReader BufferedReader)
	'(clojure.lang ASeq LazySeq StringSeq))

(unfinished seq-reader )


(extend StringSeq
  IOFactory
  (assoc default-streams-impl
    :make-reader (fn [x opts] (seq-reader x))))




;.;. FAIL at (NO_SOURCE_FILE:1)
;.;. You never said seq-reader would be needed with these arguments:
;.;.     ((\h \e \l \l \o \- \w \o \r \l \d))
;.;. 
;.;. FAIL at (NO_SOURCE_FILE:1)
;.;. You claimed the following was needed, but it was never used:
;.;.     (seq-reader ...hello...)
;.;. 
;.;. FAIL at (NO_SOURCE_FILE:1)
;.;.     Expected: "hello-world"
;.;.       Actual: java.lang.ClassCastException: clojure.lang.LazySeq cannot be cast to java.io.Reader
;.;.               aginvoice.io$eval4409$fn__4410.invoke(NO_SOURCE_FILE:1)
;.;.               aginvoice.io$eval4409.invoke(NO_SOURCE_FILE:1)
;.;.               swank.commands.basic$eval_region.invoke(basic.clj:47)
;.;.               swank.commands.basic$eval_region.invoke(basic.clj:37)
;.;.               swank.commands.basic$eval818$eval_and_grab_output__819$fn__820.invoke(basic.clj:82)
;.;.               swank.commands.basic$eval818$eval_and_grab_output__819.invoke(basic.clj:81)
;.;.               aginvoice.io$eval4407.invoke(NO_SOURCE_FILE)
;.;.               swank.core$eval_in_emacs_package.invoke(core.clj:94)
;.;.               swank.core$eval_for_emacs.invoke(core.clj:241)
;.;.               swank.core$eval_from_control.invoke(core.clj:101)
;.;.               swank.core$spawn_worker_thread$fn__455$fn__456.invoke(core.clj:300)
;.;.               swank.core$spawn_worker_thread$fn__455.doInvoke(core.clj:296)
(fact
 (.readLine (BufferedReader. (reader (seq "hello-world")))) => "hello-world"
 (provided (seq-reader anything) => (StringReader. "hello-world")))
;; (provided
;;  (seq-reader (seq "hello-world"))) => (StringReader. "hello-world"))


;;(fact
;;  (String. (bytes-from-object (reader seq("hello-world")))) => "Hello World"
;;  (provided
;;    (seq-reader (seq "Hello World")) => (StringReader. "Hello World")))
