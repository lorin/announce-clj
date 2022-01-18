run:
	clj -X announce/main

test:
	clj -X announce.time/run-tests
	clj -X announce.say/run-tests
