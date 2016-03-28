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