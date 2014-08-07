(ns setlista.core
  (:require [clj-http.client :as http]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [clojure.data.json :as json]
            [setlista.search-history :as search-history])
  (:use [compojure.core :only [GET defroutes]]))

(def setlist-api "http://api.setlist.fm/rest/0.1/search/setlists.json")

(defn home []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (slurp (io/resource "public/index.html"))})

(defn search
  ([artist] (search artist ""))
  ([artist date]
     (search-history/save artist date)
     (let [url (format "%s?artistName=%s&date=%s" setlist-api artist date)]
       (try
         (http/get url)
         (catch Exception e
           (println (format "Couldn't GET %s, got exeption %s"), url, e)
           {})))))

(defn search-history
  []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str (search-history/get-all))})

(defroutes setlista-routes
  (GET "/" [] (home))
  (GET "/status" [] {:status 200 :body "OK"})
  (GET "/search-history" [] (search-history))
  (GET ["/search/:artist/"] [artist] (search artist))
  (GET ["/search/:artist/:date"] [artist date] (search artist date))
  (route/resources "/")
  (route/not-found "Page not found"))
