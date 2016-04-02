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