(ns announce
  (:require [clojure.java.shell :refer [sh]])
  (:require [clojure.test :refer [is  with-test]])
  (:import java.time.ZonedDateTime)
  (:import java.time.ZoneId))

#_{:clj-kondo/ignore [:unused-private-var]}
(defn- get-current-time []
  (. ZonedDateTime (now)))

(with-test
  (defn parse-time
    "Given a ZonedDateTime t, return a map that contains :hour and :min
  
   Note that :hour is in 12-hour format"
    [t]
    (let [h (.getHour t)
          min (.getMinute t)]
      {:hour h, :min min}))
      (let [zone (. ZoneId (systemDefault))
            make-time (fn [h m] (. ZonedDateTime (of 2021 1 1 h m 0 0 zone)))]
        (is (= {:hour 9 :min 45} (parse-time (make-time 9 45))))))

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

(defn run-tests
  [& _]
  #_{:clj-kondo/ignore [:unresolved-var]}
  (test #'parse-time))