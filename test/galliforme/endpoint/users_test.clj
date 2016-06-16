(ns galliforme.endpoint.users-test
  (:require [clojure.test :refer :all]
            [shrubbery.core :as shrub]
            [galliforme.endpoint.users :as users]))

(def handler
  (users/users-endpoint {}))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))
