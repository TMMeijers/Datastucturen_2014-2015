#!/bin/sh

javac -Xlint *.java && java SpellChecker $@ #../data/british-english-insane.txt ../data/origin-of-species-ascii.txt 970000
