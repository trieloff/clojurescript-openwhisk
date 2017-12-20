(ns openwhisk.wrap
  (:require [promesa.core :as p]))

(defmacro defnw [name args body]
  "defines a wrapped function"
  `(def ~name (wrap (fn ~args ~body))))

#?(:cljs (defn clj-promise->js [o]
  "`clj->js` with support for promises"
  (if (p/promise? o)
    (p/then o (fn [r] (p/resolved (clj->js r))))
    (clj->js o))))

#?(:cljs (defn wrap [cljsfunc]
  "Wraps a ClojureScript function so that it can be used as OpenWhisk main function"
  (fn [args] (clj-promise->js (cljsfunc (js->clj args :keywordize-keys true))))))

#?(:cljs (defn wrapfn [cljsfunc]
  "Wraps and exports a ClojureScript function as OpenWhisk main function"
  (if (nil? js/main)
    (set! js/main (wrap cljsfunc)))))

