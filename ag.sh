#!/bin/bash
java -cp lib/clojure-1.2.0.jar:lib/clojure-contrib-1.2.0.jar:lib/unifycle-0.5.0.jar:lib/midje-1.0.0.jar:src clojure.main src/aginvoice/aglaunch.clj "$@"
