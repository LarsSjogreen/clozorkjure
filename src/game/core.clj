(ns game.core (:gen-class))
(require '[clojure.string :as str])
(use 'clojure.pprint)

(load "random")
(load "rooms_and_map")

(def version "1.0")

(defrecord statecol [room user inventory])
(defrecord userstate [name hp])
(defrecord inventorystate [stuff])

; Use (refresh) to refresh in repl
(use 'clojure.tools.namespace.repl)

; Command validation and quit
(def valCommands #{"south" "north" "west" "east" "up" 
	"down" "look" "help" "greet" "exit" "quit" "exits" "inventory" "pickup" "status" "debug" })

(def valWCommands #{"examine" "usethe"})
(defn invalid [state] (println "Invalid command") state)
(defn valC? [com] (contains? valCommands (first (str/split com #" "))))
(defn valW? [com] (contains? valWCommands com))
(defn quit? [x] (if (or (= "quit" x) (= "exit" x)) true false))

(defn newroom [state room] (->statecol room (:user state) (:inventory state)))
(defn addinv [state thing] (->statecol (:room state) (:user state) (->inventorystate (conj (:stuff (:inventory state)) thing))))
(defn debug [state] (pprint state) state)

; Game commands
(defn look [state] (println (-> state :room :text)) state)
(defn help [state] (println valCommands) state)
(defn exits [state] (println (keys (worldmap (:symbol (:room state))))) state)
(defn inventory [state] (println "You carry: " (interpose "-" (map things (:stuff (:inventory state))))) state)
(defn pickup [state] (if (not (nil? (:thing (:room state)))) (addinv state (:thing (:room state))) (do (println "Nothing to pick up") state)))
(defn status [state] (do (println "\n" (-> state :user :name)) (println "Hp: " (-> state :user :hp)) (inventory state)))
(defn roomname [state] (println (:name (:room state))) state)
(defn greet [state] (println "This is a game called Game.  ---  ( Version" version ")") state)

(defn examine [thing state] (println "You are examining " thing) state)
(defn usethe [thing state] (println "You use" thing) state)

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
(defn doro [act state] ((ns-resolve 'game.core (symbol act)) state))
(defn dowith [act acton state] (if (valW? act) ((ns-resolve 'game.core (symbol act)) acton state) ((println "You can't do that") state)))

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
