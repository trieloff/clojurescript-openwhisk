(ns openwhisk.wrap
  (:require [promesa.core :as p]))

(def owhisk (js/require "openwhisk"))

(defn list-actions [& options]
  "Returns a list of OpenWhisk functions"
  (let [ow (.owhisk (clj->js options))]
    (.list (.-actions ow))))

(defn clj-promise->js [o]
  "`clj->js` with support for promises"
  (if (p/promise? o)
    (p/then o (fn [r] (p/resolved (clj->js r))))
    (clj->js o)))

(defn wrapfn [cljsfunc]
  "Wraps and exports a ClojureScript function as OpenWhisk main function"
  (set! js/main (fn [args] (clj-promise->js (cljsfunc (js->clj args :keywordize-keys true))))))
