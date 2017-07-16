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

…

## Examples

* [Lanyrd-Embed](https://github.com/trieloff/lanyrd-embed/tree/dev) – Adding OEmbed Support for Lanyrd