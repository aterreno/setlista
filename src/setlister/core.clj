(ns setlister.core
  (:require [clj-http.client :as http]
            [compojure.route :as route])
  (:use [compojure.core :only [GET defroutes]]
        [hiccup.page :only [html5]]
        [hiccup.element :only [javascript-tag]]))

(def setlist-api "http://api.setlist.fm/rest/0.1/search/setlists.json")

(defn index
  []
  (html5
   [:head
    [:title "Setlister"]
    [:script {:type "text/javascript" :src "http://code.jquery.com/jquery-1.10.1.min.js"}]
    [:script {:type "text/javascript" :src "http://underscorejs.org/underscore-min.js"}]
    [:script {:type "text/javascript" :src "/main.js"}]
    ]
   [:body {:onload "searchSetList('jayhawks','15-07-2014')"}
    [:h1 "Setlister"]
    [:div#content]
    ]))

(defn search
  [artist date]
  (let [response (http/get (format "%s?artistName=%s&date=%s" setlist-api artist date))]
    response))

(defroutes setlister-routes
  (GET "/" [] (index))
  (GET ["/search/:artist/:date"] [artist date] (search artist date))
  (route/resources "/")
  (route/not-found "Page not found"))
