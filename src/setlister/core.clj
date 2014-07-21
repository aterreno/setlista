(ns setlister.core
  (:require [clj-http.client :as http])
  (:use [compojure.core :only [context GET routes defroutes]]
        [hiccup.page :only [html5]]
        [hiccup.element :only [javascript-tag]]))

(def setlist-api "http://api.setlist.fm/rest/0.1/search/setlists.json")

(defn index
  []
  (html5
   [:head
    [:title "Setlister"]
    [:script {:type "text/javascript" :src "/cljs/goog/base.js"}]
    [:script {:type "text/javascript" :src "/cljs/deps.js"}]
    ]
   [:body
    [:h1 "Setlister"]
    [:div#content]
    ]))

(defn search
  [artist date]
  (let [response (http/get (format "%s?artistName=%s&date=%s" setlist-api artist date))]
    response))

(defroutes setlister-routes
  (GET "/" [] (index))
  (GET ["/search/:artist/:date"] [artist date] (search artist date)))
