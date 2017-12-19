(defproject trieloff/clojurescript-openwhisk "0.1.2-SNAPSHOT"
  :dependencies [[org.clojure/clojurescript "1.9.521" :exclusions [org.apache.ant/ant]]
                 [funcool/promesa "1.8.1"]
                 [org.clojure/clojure "1.8.0"]]
  :author "Lars Trieloff <https://github.com/trieloff>"
  :description "Making it easier to write OpenWhisk actions using ClojureScript"
  :url "https://github.com/trieloff/clojurescript-openwhisk"
  :license {:name "Apache License 2.0"
            :url  "https://www.apache.org/licenses/LICENSE-2.0"
            :distribution :repo}
  :plugins [[lein-cljsbuild "1.1.6"]
            [lein-doo "0.1.7"]]
  :clean-targets ^{:protect false} ["target"]
  :hooks [leiningen.cljsbuild]
  :cljsbuild {
    :builds [{:id "prod"
              :source-paths ["src/cljs"]
              :jar true
              :compiler {:main openwhisk.wrap
                         :output-to "target/clojurescript-openwhisk.js"
                         :language-in  :ecmascript5
                         :language-out :ecmascript5
                         :pretty-print true
                         :closure-output-charset "US-ASCII" ;; USA! USA! USA! https://github.com/google/closure-compiler/issues/1704
                         :hashbang false
                         :optimizations :simple ;; notice this!
                         :target :nodejs }}]})
