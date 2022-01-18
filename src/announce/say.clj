(ns announce.say
  (:require [clojure.java.shell :refer [sh]])
  (:require [clojure.string :refer [join]])
  (:require [clojure.test :refer [is with-test run-tests]])
  (:require [announce.time :refer [make-time parse-time]]))

(def say-path "/usr/bin/say")

(defn- say
  "speak the string s by invoking the external say program"
  [s]
    (sh say-path s))


(with-test
  (defn speaking-line
    "Given a ZonedDateTime t, return the string to say"
    [t]
    (let [{:keys [hour min]} (parse-time t)
          m (if (= min 0) "o'clock" min)]
      (join " " ["It's" hour m])))
  (is (= "It's 11 30" (speaking-line (make-time 11 30))))
  (is (= "It's 4 45" (speaking-line (make-time 16 45))))
  (is (= "It's 10 o'clock" (speaking-line (make-time 10 0)))))


(defn say-time
  "speak the time t"
  [t]
  (-> t speaking-line say))

(defn -main
  []
  (run-tests 'announce.say))