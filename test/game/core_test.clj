(ns game.core-test
  (:require [clojure.test :refer :all]
            [game.core :refer :all]))

(deftest standard-test
  (testing "Should always work"
    (is (= 1 1))))

(deftest commandValidation
	(testing "Exists in list shoud be true"
		(is (valC? "help")))

	(testing "Does not exist should not be true"
		(is (not (valC? "hulp"))))

	(testing "Capital characters should still be true"
		(is (valC? "HELP"))))

(deftest quitQuits
	(testing "Quit quits"
		(is (quit? "quit")))
	(testing "Exit quits"
		(is (quit? "exit")))
	(testing "Stop doesn't quit"
		(is not (quit? "stop"))))

(deftest moveSouth
	(testing "Can move south"
		(is (= livingroom (:room (south startState)))))
	(testing "Can't move south from livingroom. Will stay in livingroom."
		(is (= livingroom (:room (south (south startState))))))
	(testing "Can't move south from livingroom, even if trying twice."
		(is (= livingroom (:room (south (south (south startState))))))))

;(deftest cangoTest (testing "Testing the canmove function" (is (cango? south startState))))

(deftest addHiddenDoorTest ;should add hidden door to office
	; Setup
	(def testState (->statecol office (->userstate "Frankenstein" 20 worldmap) (->inventorystate [:keyT])))

	(testing "Office has a door to the east."
		(is (not (cantgo? :office :east))))
	(testing "Office should have no secret door before examining"
		(is (cantgo? :office :down)))
	(testing "Can go through trapdoor after activating key in office"
		(is (do (usethe :keyT testState) (not (cantgo? :office :down)))))
)