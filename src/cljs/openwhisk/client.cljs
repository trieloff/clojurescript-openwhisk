(ns openwhisk.client)

(def owhisk (js/require "openwhisk"))

(defn list-actions [& options]
  "Returns a list of OpenWhisk functions"
  (let [ow (.owhisk (clj->js options))]
    (.list (.-actions ow))))