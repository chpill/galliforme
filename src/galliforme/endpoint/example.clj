(ns galliforme.endpoint.example
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]
            [compojure.core :refer :all]
            [crypto.password.scrypt :as scrypt]
            [hugsql.core :as hugsql]
            [clojure.data.json :as json]))

(hugsql/def-db-fns "galliforme/endpoint/users.sql")

(defn example-endpoint [{{db :spec} :db}]
  (context "/example" []
    (GET "/" []
      (io/resource "galliforme/endpoint/example/example.html")))
  (context "/users" []
    (GET "/" []
      (all-users db))
    (POST "/auth" req
      (let [rawJsonBody (slurp (:body req))
            body (json/read-str rawJsonBody
                                :key-fn keyword)
            {:keys [login passwd]} body
            db-user (user-by-email db {:email login})]
        (if-not db-user
          {:status 400 :body "user does not exist..."}
          (if-not (scrypt/check passwd (:password db-user))
            {:status 400 :body "wrong password!"}
            "welcome!"))))))

