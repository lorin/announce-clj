(ns announce
  (:require [clojure.java.shell :refer [sh]])


#_{:clj-kondo/ignore [:unused-private-var]}
(defn- say
  "speak the string s by invoking the external say program"
  [s]
  (let [ex "/usr/bin/say"]
    (sh ex s)))

(defn sleep
  "sleep for n seconds"
  [n]
  (let [ms (* 1000 n)]
    (java.lang.Thread/sleep ms)))

(defn every-n-seconds
  "Execute f every n seconds"
  [n f]
  (dorun
   (repeatedly (fn []
                 (f)
                 (sleep n)))))

#_{:clj-kondo/ignore [:unused-private-var]}
(defn- main
  [& _]
  (every-n-seconds 5 (fn [] (println "hello"))))
