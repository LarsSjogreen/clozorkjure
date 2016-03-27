(ns game.core
  (:gen-class))

(use 'clojure.tools.namespace.repl)
; Use (refresh) to refresh in repl

; Support functions for RPG-like random functions
(defn randomize [lowlim uplim] (int (+ lowlim (* (rand) (- uplim lowlim)))))
(defn d6 [] (randomize 1 7))
(defn d10 [] (randomize 1 11))

; Rooms and map
(defrecord room [name text symbol])

(def woods (->room "Woods" "Trees and shrubbery. And pine cones." :woods))
(def livingroom (->room "Living room" "A nice cozy couch and a bookcase." :livingroom))
(def kitchen (->room "Kitchen" "This looks like a kitchen. It's hot and it you can smell tasty, tasty food." :kitchen))
(def cellar (->room "Cellar" "It's dark and terrifying here. It smells of moss and mold." :cellar))
(def corridor (->room "Corridor" "This is a long and boring corridor" :corridor))

(def worldmap { 
	:kitchen { :room kitchen :south livingroom :north woods :west corridor } 
	:livingroom { :room livingroom :north kitchen :down cellar } 
	:woods { :room woods :south kitchen }
	:corridor { :room corridor :east kitchen }
	:cellar { :room cellar :up livingroom }})

(def valCommands #{"south" "north" "west" "east" "up" "down" "look" "help" "greet" "quit" "exits" })

; Game commands
(defn look [room] (println (:text room)) room)
(defn roomname [room] (println (:name room)) room)
(defn help [room] (println valCommands) room)
(defn exits [room] (println (keys (worldmap (:symbol room)))) room)

(defn godir [room dir] (roomname (dir ((:symbol room) worldmap))))

(defn south [room] (godir room :south))
(defn north [room] (godir room :north))
(defn west [room] (godir room :west))
(defn east [room] (godir room :east))
(defn down [room] (godir room :down))
(defn up [room] (godir room :up))
(defn invalid [room] (println "Invalid command") room)

(defn valC? [com] (contains? valCommands com))

(defn quit? [x] (if (= "quit" x) true false))

(defn greet [room] (println "This is a game called Game.") room)

; Another dispatcher method
(defn doro [act parm] ((ns-resolve 'game.core (symbol act)) parm))

(defn input [room imp] (loop [rum room inpot imp]
	(if (valC? inpot)
	(if (quit? inpot) (println "hasta la vista!") 
		(recur (doro inpot rum) (read-line)))
	(recur (doro "invalid" rum) (read-line)))))

(defn -main
  "This is the main entrypoint to the game called Game."
  [& args]
  (input kitchen "greet")
)
