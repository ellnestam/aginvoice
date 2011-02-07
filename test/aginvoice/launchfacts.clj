(ns aginvoice.launchfacts
  (:use midje.sweet
	aginvoice.launch)
  (:import java.io.ByteArrayOutputStream))

(unfinished )


(fact
 (source :aFormat ...invoice...) => (seq (.getBytes "formatted with a format"))
 (provided (formats :aFormat) => (fn [invoice] (seq (.getBytes "formatted with a format")))))

(fact
 (run-aginvoice ["format" "someFormat"]) => nil
 (provided
  (source :someFormat anything) => (seq "hello")
  (target :default) => (ByteArrayOutputStream.)))


