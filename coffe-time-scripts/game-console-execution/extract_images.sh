#!/bin/bash

# check if the user provided a JAR file name as an argument
if [ $# -eq 0 ]; then
    echo "Error: Enter the name of the JAR file."
    exit 1
fi

JAR_FILE="$1"
OUTPUT_DIR="extracted_images"

# create the output directory
mkdir -p "$OUTPUT_DIR"

# extract images from the JAR file
unzip -j "$JAR_FILE" "images/*" -d "$OUTPUT_DIR"

echo "Images extracted from $JAR_FILE to $OUTPUT_DIR"
