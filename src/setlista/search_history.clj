(ns setlista.search-history
  (:require [taoensso.carmine :as car :refer (wcar)]
            [clojure.data.json :as json]))

(defn heroku-conn []
  (let
      [redisurl (System/getenv "REDISCLOUD_URL")
       uri (new java.net.URI redisurl)
       userpass
       (and (.getUserInfo uri)
            (clojure.string/split (.getUserInfo uri) #":"))]
    (car/make-conn-spec :host (.getHost uri)
                        :port (.getPort uri)
                        :password (first (rest userpass)))))

(defn server-conn []
  (when-let [config (System/getenv "REDISCLOUD_URL")]
    (heroku-conn)))

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
