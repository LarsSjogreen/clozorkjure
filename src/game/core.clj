(ns game.core (:gen-class))

(load "random")
(load "rooms_and_map")

; Use (refresh) to refresh in repl
(use 'clojure.tools.namespace.repl)

; Command validation and quit
(def valCommands #{"south" "north" "west" "east" "up" "down" "look" "help" "greet" "exit" "quit" "exits" })
(defn invalid [room] (println "Invalid command") room)
(defn valC? [com] (contains? valCommands com))
(defn quit? [x] (if (or (= "quit" x) (= "exit" x)) true false))

; Game commands
(defn look [room] (println (:text room)) room)
(defn roomname [room] (println (:name room)) room)
(defn help [room] (println valCommands) room)
(defn exits [room] (println (keys (worldmap (:symbol room)))) room)
(defn greet [room] (println "This is a game called Game.") room)

; Direction valCommands
(defn invaliddir? [room dir] (= nil (dir ((:symbol room) worldmap))))
(defn godir [room dir] (if (invaliddir? room dir) (do (println "You can't go that way") room) (roomname (dir ((:symbol room) worldmap)))))
(defn south [room] (godir room :south))
(defn north [room] (godir room :north))
(defn west [room] (godir room :west))
(defn east [room] (godir room :east))
(defn down [room] (godir room :down))
(defn up [room] (godir room :up))

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
