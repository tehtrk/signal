;; Copyright 2016-2018 Boundless, http://boundlessgeo.com
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

(ns signal.components.http.input
  (:require [signal.components.http.intercept :as intercept]
            [signal.components.http.response :as response]
            [signal.components.input-manager :as input-manager-api]
            [signal.components.processor :as processorapi]
            [signal.components.http.auth :refer [check-auth]]))

(defn http-get-all-inputs
  [input-comp _]
  (response/ok (input-manager-api/all input-comp)))

(defn http-get-input
  [input-comp request]
  (let [input (input-manager-api/find-by-id input-comp
                                            (get-in request [:path-params :id]))]
    (response/ok input)))

(defn http-put-inputs
  [input-comp request]
  (if (some? (input-manager-api/modify input-comp
                                       (get-in request [:path-params :id])
                                       (:json-params request)))
    (response/ok "success")
    (response/error "error updating input")))

(defn http-post-inputs
  [input-comp request]
  (if-let [input (input-manager-api/create input-comp (:json-params request))]
    (response/ok input)
    (response/error "Error creating input")))

(defn http-delete-inputs
  [input-comp request]
  (let [id (get-in request [:path-params :id])]
    (if (input-manager-api/delete input-comp id)
      (response/ok "success")
      (response/error "could not delete input: " id))))

(defn routes
  "Makes routes for the current inputs"
  [input-comp]
  #{["/api/inputs" :get
     (conj intercept/common-interceptors check-auth
           (partial http-get-all-inputs input-comp))
     :route-name :get-inputs]
    ["/api/inputs/:id" :get
     (conj intercept/common-interceptors check-auth
           (partial http-get-input input-comp))
     :route-name :get-input]
    ["/api/inputs/:id" :put
     (conj intercept/common-interceptors check-auth
           (partial http-put-inputs input-comp))
     :route-name :put-input]
    ["/api/inputs" :post
     (conj intercept/common-interceptors check-auth
           (partial http-post-inputs input-comp))
     :route-name :post-input]
    ["/api/inputs/:id" :delete
     (conj intercept/common-interceptors check-auth
           (partial http-delete-inputs input-comp))
     :route-name :delete-input]})
