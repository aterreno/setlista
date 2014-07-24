(ns setlista.server
  (:require
   [ring.adapter.jetty :as jetty])
  (:use
   [setlista.core :only [setlista-routes]]
   [ring.middleware.content-type]
   [ring.middleware.file-info]
   [compojure.handler :only [site]])
  (:gen-class :main true))

(def handler
  (-> setlista-routes
      wrap-content-type
      wrap-file-info
      site))

(defn start [options]
  (jetty/run-jetty #'handler (assoc options :join? false)))

(defn -main
  ([port]
     (start {:port (Integer/parseInt port)}))
  ([]
     (-main "8000")))
