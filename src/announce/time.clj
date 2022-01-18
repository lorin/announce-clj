(ns announce.time
  (:require [clojure.test :refer [is with-test run-tests]])
  (:import (java.time ZonedDateTime ZoneId)))
  
(defn make-time
  "Given a 24-hour h and minute m, create a ZonedDateTime int he current time zone"
  [h m]
  (let [zone (ZoneId/systemDefault)
        year 2021
        month 1
        day-of-month 1
        second 0
        nanosecond 0]
    (ZonedDateTime/of year month day-of-month h m second nanosecond zone))) 
  

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


(defn -main
  []
  (run-tests 'announce.time))