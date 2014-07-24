(ns setlista.core
  (:require [clj-http.client :as http]
            [compojure.route :as route]
            [clojure.java.io :as io])
  (:use [compojure.core :only [GET defroutes]]))

(def setlist-api "http://api.setlist.fm/rest/0.1/search/setlists.json")

(defn search
  [artist date]
  (let [response (http/get (format "%s?artistName=%s&date=%s" setlist-api artist date))]
    response))

(defroutes setlista-routes
  (GET "/" [] (io/resource "public/index.html"))
  (GET ["/search/:artist/:date"] [artist date] (search artist date))
  (route/resources "/")
  (route/not-found "Page not found"))
