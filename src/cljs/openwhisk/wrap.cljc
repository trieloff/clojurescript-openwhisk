(ns openwhisk.wrap
  (:require [promesa.core :as p]))

(defmacro defaction [name args body]
  "Defines an OpenWhisk action. It will create a function with name `name` that can be exported in the JavaScript module and called by OpenWhisk."
  `(def ~name (wrap (fn ~args ~body))))

(defmacro defweb [name args headers body]
  "Defines an OpenWhisk web action. In addition to `defaction`, it will pass through `headers`. "
  `(def ~name (wrap ~headers (fn ~args ~body))))

#?(:cljs (defn clj-promise->js [o]
  "`clj->js` with support for promises"
  (if (p/promise? o)
    (p/then o (fn [r] (p/resolved (clj->js r))))
    (clj->js o))))

#?(:cljs (defn clj->json [o]
  "Turns object `o` into a stringified JSON object."
  (.stringify js/JSON (clj->js o))))

#?(:cljs (defn wrap-headers [r headers]
  "Wraps value `r` in a JSON object compatible with OpenWhisk web actions, using headers `headers`."
  (clj->js {:headers headers
            :statusCode 200
            :body (clj->json r)})))

#?(:cljs (defn clj-promise->json [o headers]
  "`clj->js` with support for promises and headers"
  (if (p/promise? o)
    (p/then o (fn [r] (p/resolved (wrap-headers r headers))))
    (wrap-headers o headers))))

#?(:cljs (defn wrap
  "Wraps a ClojureScript function so that it can be used as OpenWhisk main function"
  ([headers cljsfunc]
    (fn [args] (clj-promise->json (cljsfunc (js->clj args :keywordize-keys true)) headers)))
  ([cljsfunc]
    (fn [args] (clj-promise->js (cljsfunc (js->clj args :keywordize-keys true)))))))

#?(:cljs (defn wrapfn [cljsfunc]
  "Wraps and exports a ClojureScript function as OpenWhisk main function"
  (if (nil? js/main)
    (set! js/main (wrap cljsfunc)))))

