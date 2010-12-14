#!/bin/bash
java -cp lib/clojure-1.2.0.jar:lib/clojure-contrib-1.2.0.jar:src clojure.main src/aginvoice/aglaunch.clj "$@"