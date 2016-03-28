(in-ns 'game.core)

; Support functions for RPG-like random functions
(defn randomize [lowlim uplim] (int (+ lowlim (* (rand) (- uplim lowlim)))))
(defn d6 [] (randomize 1 7))
(defn d10 [] (randomize 1 11))