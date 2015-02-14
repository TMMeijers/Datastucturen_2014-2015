#!/bin/sh

# make sure we have newest compiled version
javac -Xlint *.java || exit 1

# size for hash table (2^)
MAX_EXPONENT=24

# iterate upto MAX_EXPONENT raise power and save all output stuff to file
for i in $(seq 8 $MAX_EXPONENT); do
    v=$((2**i));
    echo $v
    java OwnSpellChecker \
	../data/british-english-insane.txt \
	../data/war-and-peace-ascii.txt \
	$v \
	true 2>/dev/null > output/output_experiment_$v.txt
done
