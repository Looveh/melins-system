(ns melins.main
  (:require [jayq.core :refer [$] :as jq]
            [melins.core :refer [text->sign-set]]))

(defn log [& args]
  (.log js/console (apply str (interpose " " args))))

(defn draw-sign [context sign]
  (let [img (js/Image.)
        uri (str "img/" (:sign sign) ".png")]
    (set! (.-src img) uri)
    (set! (.-onload img)
          (fn []
            (.drawImage context img (:x sign) (:y sign))))))

(defn draw [canvas sign-set]
  (let [context (.getContext canvas "2d")]
    (.clearRect context 0 0 (* 3 (.-width canvas)) (* 3 (.-height canvas)))
    (dorun (map #(draw-sign context %) sign-set))))

(defn render [canvas text]
  (let [dimensions {:width (.-width canvas)
                    :height (.-height canvas)
                    :margin-x 60
                    :margin-y 120}]
    (draw canvas (text->sign-set text dimensions))))

(defn init []
  (jq/on ($ :#text-input)
         [:keyup]
         #(render (first ($ "#canvas"))
                  (jq/val ($ :#text-input)))))

(jq/document-ready init)
