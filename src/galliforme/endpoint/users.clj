(ns galliforme.endpoint.users
  (:require [clojure.data.json :as json]
            [compojure.core :refer :all]
            [crypto.password.scrypt :as scrypt]
            [hugsql.core :as hugsql]))

(hugsql/def-db-fns "galliforme/endpoint/users.sql")

(defn parse-json-payload [req]
  (json/read-str (slurp (:body req))
                 :key-fn keyword))

(defn users-endpoint [{{db :spec} :db}]
  (routes
    (context "/users" []
      (GET "/" []
        (all-users db))
      (POST "/" req
        (let [user (parse-json-payload req)
              db-password (scrypt/encrypt (:password user))
              db-user (assoc user :password db-password)]
          (println user)
          (register-user db db-user))))
    (context "/auth" []
      (POST "/" req
        (let [{:keys [login password]} (parse-json-payload req)
              db-user (user-by-email db {:email login})]
          (if-not db-user
            {:status 400 :body "user does not exist..."}
            (if-not (scrypt/check password (:password db-user))
              {:status 400 :body "wrong password!"}
              "welcome!")))))))

