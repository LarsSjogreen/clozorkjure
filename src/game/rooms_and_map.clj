; Rooms and map
(defrecord room [name text symbol thing])

(def things { :keyT "A key" :mapT "An old map" :swordT "A shiny sword" })

(def woods (->room "Woods" "Trees and shrubbery. And pine cones." :woods nil))
(def livingroom (->room "Living room" "A nice cozy couch and a bookcase." :livingroom nil))
(def kitchen (->room "Kitchen" "This looks like a kitchen. It's hot and it you can smell tasty, tasty food." :kitchen nil))
(def cellar (->room "Cellar" "It's dark and terrifying here. It smells of moss and mold." :cellar :keyT ))
(def rootcellar (->room "Root cellar" "This is were the food is kept. Cold, damp and filled with turnips and a sword." :rootcellar :swordT ))
(def corridor (->room "Corridor" "This is a long and boring corridor." :corridor nil))
(def office (->room "Office" "A dimly lit room with a desk, a chair and a small bookcase.", :office nil))
(def frontlawn (->room "Front lawn" "The lawn in front of a small house." :frontlawn nil))
(def clearing (->room "A clearing" "The forest opens up into a small clearing. With a pond." :clearing :mapT ))
(def road (->room "A forest road" "A dwindling forest road." :road nil ))
(def bridge (->room "A bridge" "A rickety bridge over a fast flowing stream in the forest." :bridge nil ))

(def worldmap { 
	:kitchen { :room kitchen :south livingroom :north woods :west corridor :east frontlawn }
	:livingroom { :room livingroom :north kitchen :down cellar } 
	:woods { :room woods :south kitchen :north clearing }
	:clearing { :room clearing :south woods :west road }
	:road { :room road :east clearing :west bridge }
	:bridge { :room bridge :east road }
	:corridor { :room corridor :east kitchen :west office }
	:cellar { :room cellar :up livingroom :north rootcellar }
	:rootcellar { :room rootcellar :south cellar }
	:office { :room office :east corridor }
	:frontlawn { :room frontlawn :west kitchen }})

(def thinguse { :keyT { :room office :message "You open the desk drawer. It's empty" } })