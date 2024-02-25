(ns redis-pipeline-poc.core
  (:require [taoensso.carmine :as car]
            [criterium.core :as criterium-core])
  (:gen-class))

(defonce connection-pool (car/connection-pool {}))
(def connection-specs {:uri "redis://localhost:6379"})
(def carmine-opts {:pool connection-pool :spec connection-specs})

(defmacro wcar* [& body] `(car/wcar carmine-opts ~@body))

(defn run-load-without-pipeline []
  (do
    (wcar*
     (car/get "my-group:my-key1"))
    (wcar*
     (car/get "my-group:my-key2"))
    (wcar*
     (car/get "my-group:my-key3"))
    (wcar*
     (car/get "my-group:my-key4"))
    (wcar*
     (car/get "my-group:my-key5"))))

(defn run-load-with-pipeline []
  (wcar*
   (car/get "my-group:my-key1")
   (car/get "my-group:my-key2")
   (car/get "my-group:my-key3")
   (car/get "my-group:my-key4")
   (car/get "my-group:my-key5")))

(defn -main [& _]
  (println "Starting load testing without pipeline...")
  (criterium-core/with-progress-reporting (criterium-core/bench (run-load-without-pipeline) :verbose))
  (println "Sleeping for 10 seconds for next load test...")
  (Thread/sleep 10000)
  (println "Starting load testing with pipeline...")
  (criterium-core/with-progress-reporting (criterium-core/bench (run-load-with-pipeline) :verbose)))
