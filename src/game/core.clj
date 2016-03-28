(ns game.core (:gen-class))

(load "random")
(load "rooms_and_map")

(defrecord statecol [room user inventory])
(defrecord userstate [name hp])
(defrecord inventorystate [stuff])

; Use (refresh) to refresh in repl
(use 'clojure.tools.namespace.repl)

; Command validation and quit
(def valCommands #{"south" "north" "west" "east" "up" 
	"down" "look" "help" "greet" "exit" "quit" "exits" "inventory" "pickup" })
(defn invalid [state] (println "Invalid command") state)
(defn valC? [com] (contains? valCommands com))
(defn quit? [x] (if (or (= "quit" x) (= "exit" x)) true false))

(defn newroom [state room] (->statecol room (:user state) (:inventory state)))
(defn addinv [state thing] (->statecol (:room state) (:user state) (->inventorystate (conj (:stuff (:inventory state)) thing))))

; Game commands
(defn look [state] (println (:text (:room state))) state)
(defn help [state] (println valCommands) state)
(defn exits [state] (println (keys (worldmap (:symbol (:room state))))) state)
(defn inventory [state] (println "You carry: " (interpose "-" (map things (:stuff (:inventory state))))) state)
(defn pickup [state] (if (not (nil? (:thing (:room state)))) (addinv state (:thing (:room state))) (do (println "Nothing to pick up") state)))

(defn roomname [state] (println (:name (:room state))) state)
(defn greet [state] (println "This is a game called Game.") state)

; Direction valCommands
(defn invaliddir? [room dir] (= nil (dir ((:symbol room) worldmap))))
(defn godir [state dir] (if (invaliddir? (:room state) dir) (do (println "You can't go that way") state) (roomname (newroom state (dir ((:symbol (:room state)) worldmap))))))
(defn south [state] (godir state :south))
(defn north [state] (godir state :north))
(defn west [state] (godir state :west))
(defn east [state] (godir state :east))
(defn down [state] (godir state :down))
(defn up [state] (godir state :up))

; Another dispatcher method
(defn doro [act parm] ((ns-resolve 'game.core (symbol act)) parm))

(defn input [state imp] (loop [stat state inpot imp]
	(if (valC? inpot)
	(if (quit? inpot) (println "hasta la vista!") 
		(recur (doro inpot stat) (read-line)))
	(recur (doro "invalid" stat) (read-line)))))

(def startState (->statecol kitchen (->userstate "Frankenstein" 20) (->inventorystate [])))

(defn -main
  "This is the main entrypoint to the game called Game."
  [& args]
  (input startState "greet")
)
