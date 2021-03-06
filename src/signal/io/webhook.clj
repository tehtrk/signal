

(ns signal.io.webhook
  (:require [signal.io.protocol :as proto]
            [clj-http.client :as http]
            [cheshire.core :as json]
            [clojure.tools.logging :as log]
            [signal.components.database :as db]))

(def identifier "webhook")

(defn- send!
  [webhook value]
  (log/info (:url webhook))
  (do (http/post (:url webhook) {:body (json/generate-string value)
                                 :content-type :json})
      (map db/mark-as-sent (:notif-ids value))))

(defrecord Webhook [url verb]
  proto/Output
  (recipients [this] [(:url this)])
  (send! [this value]
    (send! this value)))

(defmethod proto/make-output identifier
  [cfg]
  (map->Webhook cfg))
