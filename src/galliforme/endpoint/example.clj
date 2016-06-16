(ns galliforme.endpoint.example
  (:require [clojure.java.io :as io]
            [compojure.core :refer :all]))


(defn example-endpoint [{{db :spec} :db}]
  (context "/example" []
    (GET "/" []
      (io/resource "galliforme/endpoint/example/example.html"))))


