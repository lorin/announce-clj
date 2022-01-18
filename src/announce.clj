(ns announce
  (:require [announce.say :refer [say-time]])
  (:import java.time.ZonedDateTime))

(def speaking-minutes #{0 15 30 45})

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

(defn check-and-say-time
  "check if it's time to say the time. If so, say it.
  
   We sleep for an additional minute after saying the time to avoid saying it twice in the same minute."
  []
  (let [time (ZonedDateTime/now)
        is-speaking-minute? (->> time .getMinute (contains? speaking-minutes))]
    (when is-speaking-minute?
      (say-time time)
      (sleep 60))))
  
(defn -main
  []
  (let [every-second (partial every-n-seconds 1)]
    (every-second check-and-say-time)))
