(ns melins.core
  (:require [jayq.core :refer [$] :as jq]
            [clojure.string :refer [split]]
            [melins.signs :refer [coords]]))

(defn nil-or [value default]
  (if (nil? value) default value))

(defn tokenize [word]
  (filter coords (map str word)))

(defn ->words [text]
  (map tokenize (split (.trim text) #" +")))

(defn word-width [word]
  (if (empty? word)
    0
    (Math/abs (- (:x (last word)) (:x (first word))))))

(defn sign-map [word]
  (loop [mapped [] tokens word x 0 y 0]
    (if (empty? tokens)
      mapped
      (let [token (first tokens)
            {{start-x :x start-y :y} :start {end-x :x end-y :y} :end} (coords token)
            sign-map {:sign token
                      :x (- x start-x)
                      :y (- y start-y)}]
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

(defn place-words [words dimensions]
  (let [{width :width height :height margin-x :margin-x margin-y :margin-y} dimensions]
    (loop [placed [] words words x margin-x y margin-y]
      (let [word (first words)]
        (if (empty? words)
          placed
          (if (and (> (+ x (:width word))
                      (- width margin-x))
                   (not= x margin-x))
            (recur placed words margin-x (+ y 60))
            (recur (conj placed (assoc word :x x :y y))
                   (rest words)
                   (+ x (:width word) 60)
                   y)))))))

;;; JS Specific

(defn log [& args]
  (.log js/console (apply str (interpose " "args))))

(defn draw-sign [context sign]
  (let [img (js/Image.)
        uri (str "img/" (:sign sign) ".png")]
    (set! (.-src img) uri)
    (set! (.-onload img)
          (fn []
            (.drawImage context img (:x sign) (:y sign))))))

(defn draw-word [context word]
  (doseq [sign (:signs word)] (draw-sign context (assoc sign
                                                        :x (+ (:x sign)
                                                              (:x word))
                                                        :y (+ (:y sign)
                                                              (:y word))))))

(defn draw [canvas words]
  (let [context (.getContext canvas "2d")]
    (.clearRect context 0 0 (* 3 (.-width canvas)) (* 3 (.-height canvas)))
    (doseq [word words] (draw-word context word))))

(defn render [canvas text]
  (let [dimensions {:width (.-width canvas)
                    :height (.-height canvas)
                    :margin-x 60
                    :margin-y 120}
        words (word-set text)
        placed-words (place-words words dimensions)]
    (draw canvas placed-words)))

(defn init []
  (jq/on ($ :#text-input)
         [:keyup]
         #(render (first ($ "#canvas"))
                  (jq/val ($ :#text-input)))))

(jq/document-ready init)
