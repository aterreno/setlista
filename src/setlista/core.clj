(ns setlista.core
  (:require [clj-http.client :as http]
            [compojure.route :as route]
            [clojure.java.io :as io])
  (:use [compojure.core :only [GET defroutes]]))

(def setlist-api "http://api.setlist.fm/rest/0.1/search/setlists.json")

(defn home []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (slurp (io/resource "public/index.html"))})

(defn search
  ([artist] (search artist ""))
  ([artist date]
     (let [url (format "%s?artistName=%s&date=%s" setlist-api artist date)]
       (println url)
       (try
         (http/get url)
         (catch Exception e
           (println (format "Couldn't GET %s, got exeption %s"), url, e)
           {})))))

(defroutes setlista-routes
  (GET "/" [] (home))
  (GET "/status" [] {:status 200 :body "OK"})
  (GET ["/search/:artist/"] [artist] (search artist))
  (GET ["/search/:artist/:date"] [artist date] (search artist date))
  (route/resources "/")
  (route/not-found "Page not found"))
