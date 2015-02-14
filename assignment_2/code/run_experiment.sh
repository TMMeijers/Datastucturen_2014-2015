#!/bin/sh

# make sure we have newest compiled version
javac -Xlint *.java || exit 1

# size for hash table (2^)
MIN_EXPONENT=8
MAX_EXPONENT=24
# how often do we want to run the experiment
HOW_OFTEN=10
# where do we store output files
OUTPUT_DIR=output
FIGURE_DIR=figs

# check if output directory exists
if [ ! -d $OUTPUT_DIR ]; then
    mkdir $OUTPUT_DIR
else
    # clean if there are txt files in it
    rm $OUTPUT_DIR/*.txt 2>/dev/null
fi

if [ ! -d $FIGURE_DIR ]; then
    mkdir $FIGURE_DIR
else
    rm $FIGURE_DIR/*.png 2>/dev/null
fi

echo "Run ${HOW_OFTEN} experiments for hash size in [2^${MIN_EXPONENT},2^${MAX_EXPONENT}]"

# iterate upto MAX_EXPONENT raise power and save all output stuff to file
for i in $(seq $MIN_EXPONENT $MAX_EXPONENT); do
    echo "exponent: ${i}"
    for j in $(seq 1 $HOW_OFTEN); do
	v=$((2**i));
	echo $j
	java SpellChecker \
	    ../data/british-english-insane.txt \
	    ../data/war-and-peace-ascii.txt \
	    $v \
	    true 2>/dev/null > output/exp_${j}_${v}.txt
	# if error occured kill
	if [[ $? != 0 ]]; then
	    exit 1
	fi
    done
done

python plot_data.py
