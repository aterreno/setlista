(ns setlista.search-history
  (:require [taoensso.carmine :as car :refer (wcar)]
            [clojure.data.json :as json]))

(defn server-conn []
  (when-let [redis-cloud (System/getProperty "REDISCLOUD_URL")]
    {:pool {} :spec {:host redis-cloud :port 6379}}))

(def last-searches "last-searches")

(defmacro wcar* [& body] `(car/wcar (server-conn) ~@body))

(defn save
  [artist date]
  (wcar*
   (car/lpush last-searches (json/write-str {:artist artist :date date}))
   (car/ltrim last-searches 0 9)))

(defn get-all
  []
  (let [result (wcar* (car/lrange last-searches 0 -1))]
    (map (fn [x] (json/read-str x)) result)))
