# clojurescript-openwhisk
Making it easier to write OpenWhisk functions using ClojureScript

[![Clojars Project](https://img.shields.io/clojars/v/trieloff/clojurescript-openwhisk.svg)](https://clojars.org/trieloff/clojurescript-openwhisk)


## Background

This is a very small library that is intended to do two things:

1. wrap your ClojureScript code, so that it can be executed in OpenWhisk's Node.js runtime
2. make some of the functions of the OpenWhisk npm package available in ClojureScript

## Installing

```clojure
[trieloff/clojurescript-openwhisk "0.1.0"]
```

```clojure
(:require [openwhisk.wrap :as o])
```

## Usage

```clojure
(defn echo [params]
  (identity params))

(o/wrapfn echo)
```

### Building with Leiningen

```clojure
(defproject myproject "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojurescript "1.9.521" :exclusions [org.apache.ant/ant]]
                 [trieloff/clojurescript-openwhisk "0.1.0"]
                 [org.clojure/clojure "1.8.0"]]
  :plugins [[lein-cljsbuild "1.1.6"]]
  :clean-targets ^{:protect false} ["target", "main.js"]
  :hooks [leiningen.cljsbuild]
  :cljsbuild {
    :builds [{:id "server"
              :source-paths ["src/cljs"]
              :jar true
              :compiler {:main mypriject.core
                         :output-to "main.js"
                         :language-in  :ecmascript5
                         :language-out :ecmascript5
                         :pretty-print true
                         :closure-output-charset "US-ASCII"
                         :hashbang false
                         :optimizations :simple ;; notice this!
                         :target :nodejs }}]})

```

Then run `lein compile` to build, and deploy the resulting `main.js` to OpenWhisk.

The `:closure-output-charset` is set to "US-ASCII" to work around an [encoding shortcut in the Google Closure compiler](https://github.com/google/closure-compiler/issues/1704) that is causing interoperability challenges in OpenWhisk.

## Examples

* [Lanyrd-Embed](https://github.com/trieloff/lanyrd-embed/tree/dev) – Adding OEmbed Support for Lanyrd