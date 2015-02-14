#!/bin/sh

javac -Xlint *.java && java OwnSpellChecker $@ #../data/british-english-insane.txt ../data/origin-of-species-ascii.txt 970000
