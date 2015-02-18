(ns melins-system.app.melins
  (:require [clojure.string :refer [split]]
            [jayq.core :refer [$] :as jq]))

(defn nil-or [value default]
  (if (nil? value) default value))

(defn tokenize [word]
  (filter coords (map str word)))

(defn ->words [text]
  (map tokenize (split (.trim text) #" +")))

(defn word-width [word]
  (if (empty? word)
    0
    (Math/abs (- (:x (last word))
                 (:x (first word))))))

(defn sign-map [word]
  (loop [mapped [] tokens word x 0 y 0]
    (if (empty? tokens)
      mapped
      (let [token (first tokens)
            {{start-x :x start-y :y} :start {end-x :x end-y :y} :end} (coords token)
            sign-map {:sign token
                      :x (+ x start-x)
                      :y (+ y start-y)}]
        (recur (conj mapped sign-map)
               (rest tokens)
               (- (+ x end-x) start-x)
               (- (+ y end-y) start-y))))))

(defn word-map [word]
  (let [signs (sign-map word)]
    {:x 0
     :y 0
     :signs signs
     :width (word-width signs)}))

(defn word-set [text]
  (let [words (->words text)
        word-maps (mapv word-map words)]
    word-maps))

(ns melins.core
  (:require [jayq.core :refer [$] :as jq]
            [melins-system.handler :refer [word-set]]))

(defn draw-sign [context sign]
  (let [img (Image.)
        uri (str "img/" (:sign sign) ".png")]
    (set! (.-src img) uri)
    (set! (.-onload img) (fn [] (.drawImage context img (:x sign) (:y sign))))))

(defn draw-word [context word]
  (map #(draw-sign context %) word))

(defn draw [canvas words]
  (let [context (.getContext canvas "2d")]
    (.clearRext context 0 0 (* 3 (.height canvas)) (* 3 (.height canvas)))
    (dorun (map #(draw-word context %) words))))

(defn render [canvas text]
  (draw canvas (word-set text)))

(defn init []
  (jq/on ($ :#text-input)
         [:keyup]
         #(render ($ "#canvas")
                  (jq/val ($ :#text-input)))))

(jq/document-ready init)
