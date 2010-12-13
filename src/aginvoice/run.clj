(ns aginvoice.run
  (:gen-class))
(use 'aginvoice.launch)

(defn -main [& args]
  (run-aginvoice args))