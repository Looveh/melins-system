(ns melins.core
  (:require[clojure.string :refer [split]]
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
    (let [end-points (concat (map #(get-in coords [(:sign %) :start :x]) word)
                             (map #(get-in coords [(:sign %) :end :x]) word))]
      (- (reduce max end-points)
         (reduce min end-points)))))

(defn sign-set [word]
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

(defn word-set [text]
  (mapv #(hash-map :x 0
                   :y 0
                   :signs %
                   :width (word-width %))
        (mapv sign-set (->words text))))


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

(defn place-signs [words]
  (map (fn [word]
         (map (fn [sign-set]
                (assoc sign-set
                       :x (+ (:x sign-set) (:x word))
                       :y (+ (:y sign-set) (:y word))))
              (:signs word)))
       words))

(defn text->sign-set [text dimensions]
  (let [words (word-set text)
        placed-words (place-words words dimensions)
        placed-signs (place-signs placed-words)]
    (flatten placed-signs)))

