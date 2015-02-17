(ns melins-system.routes.home
  (:require [compojure.core :refer :all]
            [melins-system.layout :as layout]))

(defn home-page []
  (layout/render "app.html"))

(defroutes home-routes
  (GET "/" [] (home-page)))
