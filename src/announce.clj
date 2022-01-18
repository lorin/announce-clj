(ns announce
  (:require [announce.say :refer [say-time]])
  (:require [announce.time :refer [get-current-time]]))

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

(def every-second (partial every-n-seconds 1))

(defn check-and-say-time
  "check if it's time to say the time. If so, say it"
  []
  (let [t (get-current-time)
        m (.getMinute t)
        is-speaking-minute? (speaking-minutes m)]
    (when is-speaking-minute?
      (say-time t)
      ; Sleep to avoid saying twice in the same minute
      (sleep 60))))
  

(defn -main
  [& _]
  (every-second check-and-say-time)
  )
