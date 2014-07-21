(ns setlister.server
  (:require
   [ring.adapter.jetty :as jetty])
  (:use
   [setlister.core :only [setlister-routes]]
   [ring.util.response :only [header]]
   [compojure.handler :only [site]]))

(def handler
  (-> setlister-routes
      site))

(defn start [options]
  (jetty/run-jetty #'handler (assoc options :join? false)))

(defn -main
  ([port]
     (start {:port (Integer/parseInt port)}))
  ([]
     (-main "8000")))
