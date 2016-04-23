(defproject game "0.1.0-SNAPSHOT"
  :description "This may become a game. Written in Clojure."
  :url "http://no.url/here"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot game.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
