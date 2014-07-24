(ns setlista.core
  (:require [clj-http.client :as http]
            [compojure.route :as route]
            [clojure.java.io :as io])
  (:use [compojure.core :only [GET defroutes]]))

(def setlist-api "http://api.setlist.fm/rest/0.1/search/setlists.json")

(defn index []
  {:headers {"Content-Type" "text/html"}
   :body (io/resource "public/index.html")})

(defn search
  [artist date]
  (let [url (format "%s?artistName=%s&date=%s" setlist-api artist date)]
    (try
      (http/get url)
      (catch Exception e
        (println (format "Couldn't GET %s, got exeption %s"), url, e)
        {}))))

(defroutes setlista-routes
  (GET "/" [] (index))
  (GET ["/search/:artist/:date"] [artist date] (search artist date))
  (route/resources "/")
  (route/not-found "Page not found"))
