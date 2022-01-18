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
  "check if it's time to say the time. If so, say it"
  []
  (let [time (. ZonedDateTime (now))
        minute (.getMinute time)
        is-speaking-minute? (contains? speaking-minutes minute)]
    (when is-speaking-minute?
      (say-time time)
      ; Sleep to avoid speaking twice in the same minute
      (sleep 60))))
  
(defn -main
  []
  (let [every-second (partial every-n-seconds 1)]
    (every-second check-and-say-time)))
