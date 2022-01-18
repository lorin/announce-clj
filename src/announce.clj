(ns announce
  (:require [clojure.string :refer [join]])
  (:require [clojure.java.shell :refer [sh]])
  (:require [clojure.test :refer [is  with-test]])
  (:import java.time.ZonedDateTime)
  (:import java.time.ZoneId))

#_{:clj-kondo/ignore [:unused-private-var]}
(defn- get-current-time []
  (. ZonedDateTime (now)))

(defn- make-time
  "Given a 24-hour h and minute m, create a ZonedDateTime int he current time zone"
  [h m]
  (let [zone (. ZoneId (systemDefault))
        year 2021
        month 1
        day-of-month 1
        second 0
        nanosecond 0]
    (. ZonedDateTime (of year month day-of-month h m second nanosecond zone))))  
  

(with-test
  (defn parse-time
    "Given a ZonedDateTime t, return a map that contains :hour and :min
  
   Note that :hour is in 12-hour format"
    [t]
    (let [min (.getMinute t)
          h (.getHour t)
          hour (if (> h 12) (- h 12) h)]
      {:hour hour, :min min}))
    (is (= {:hour 9 :min 45} (parse-time (make-time 9 45))))
    (is (= {:hour 3 :min 30} (parse-time (make-time 15 30)))))

(with-test
  (defn speaking-line
    "Given a ZonedDateTime t, return the string to say"
    [t]
    (let [{:keys [hour min]} (parse-time t)]
      (join " " ["It's" hour min])))
  (is (= "It's 11 30" (speaking-line (make-time 11 30))))
  (is (= "It's 4 45" (speaking-line (make-time 16 45))))
  (is (= "It's 10 o'clock" (speaking-line (make-time 10 0))))
  )

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
  (test #'parse-time)
  (test #'speaking-line)
  )