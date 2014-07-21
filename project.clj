(defproject setlister "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.3"]
                 [clj-http "0.9.2"]
                 [hiccup "1.0.5"]
		 [ring/ring-core "1.2.1"]
                 [ring/ring-jetty-adapter "1.1.0"]]
  :plugins [[lein-ring "0.8.11"]]
  :uberjar-name "setlister.jar"
  :min-lein-version "2.0.0"
  :ring {:handler setlister.server/handler
         :adapter {:port 8080}}
  :main setlister.server
  :aot [setlister.server])
