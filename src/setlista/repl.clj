(ns setlista.repl
  (:use [ring.adapter.jetty]
        [setlista.server :only [handler]]))

(defonce server
  (run-jetty #'handler {:port 8000 :join? false}))

(defn start []
  (.start server))

(defn stop []
  (.stop server))

(defn restart []
  (stop)
  (start))
