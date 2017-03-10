;; Copyright 2016-2017 Boundless, http://boundlessgeo.com
;;
;; Licensed under the Apache License, Version 2.0 (the "License");
;; you may not use this file except in compliance with the License.
;; You may obtain a copy of the License at
;;
;; http://www.apache.org/licenses/LICENSE-2.0
;;
;; Unless required by applicable law or agreed to in writing, software
;; distributed under the License is distributed on an "AS IS" BASIS,
;; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;; See the License for the specific language governing permissions and
;; limitations under the License.

(ns signal.components.user.db
  (:require [signal.db.conn :as db]
            [yesql.core :refer [defqueries]]
            [clojure.java.jdbc :as jdbc]
            [buddy.hashers :as hashers]
            [clojure.tools.logging :as log]))

;; define sql queries as functions in this namespace
(defqueries "sql/user.sql" {:connection db/db-spec})

(defn- sanitize
  "Cleans up fields from the database"
  [user]
  (dissoc user :password :created_at :updated_at :deleted_at))

(defn all
  []
  (log/debug "Fetching all users from db")
  (map sanitize (find-all)))

(defn create
  "Adds a new user to the database.
   Returns the user with id."
  [u]
  (log/debug "Inserting user" u)
  (let [user-info {:name     (:name u)
                   :email    (:email u)
                   :password (hashers/derive (:password u))}]
    (sanitize (create<! user-info))))
