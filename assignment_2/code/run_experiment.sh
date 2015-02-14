#!/bin/sh

# make sure we have newest compiled version
javac -Xlint *.java || exit 1

# size for hash table (2^)
MAX_EXPONENT=24
OUTPUT_DIR=output

# check if output directory exists
if [ ! -d $OUTPUT_DIR ]; then
    mkdir $OUTPUT_DIR
else
    # clean if there are txt files in it
    rm $OUTPUT_DIR/*.txt 2>/dev/null
fi

# iterate upto MAX_EXPONENT raise power and save all output stuff to file
for i in $(seq 8 $MAX_EXPONENT); do
    v=$((2**i));
    echo $v
    java SpellChecker \
	../data/british-english-insane.txt \
	../data/war-and-peace-ascii.txt \
	$v \
	true 2>/dev/null > output/experiment_$v.txt
    if [[ $? != 0 ]]; then
	exit 1
    fi
done
