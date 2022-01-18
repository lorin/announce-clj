run:
	clj -M -m announce

test:
	clj -X announce.time/run-tests
	clj -X announce.say/run-tests
