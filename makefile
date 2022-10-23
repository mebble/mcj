# https://maex.me/2018/02/dont-fear-the-makefile/
# https://unix.stackexchange.com/questions/235223/makefile-include-env-file

.PHONY: test

include .env
export

test:
	lein eftest

test-watch:
	lein test-refresh

build:
	lein uberjar
	mv target/**/mcj-*-standalone.jar target/mcj.jar
