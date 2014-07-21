(ns setlister.core
  (:require [liberator.dev :as dev]
            [clj-http.client :as http])
  (:use [compojure.core :only [context GET routes defroutes]]))

(def setlist-api "http://api.setlist.fm/rest/0.1/search/setlists.json")

(defn search
  [artist date]
  (let [response (http/get (format "%s?artistName=%s&eventDate=%s" setlist-api artist date))]
    :handle-ok response))

(defn assemble-routes []
  (->
   (routes
    (GET ["/search/:artist/:date"] [artist date] (search artist date)))
   (dev/wrap-trace :ui :header)))
