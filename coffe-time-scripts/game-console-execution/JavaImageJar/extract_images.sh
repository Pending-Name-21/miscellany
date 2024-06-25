#!/bin/bash

JAR_FILE="MyJavaProject.jar"
OUTPUT_DIR="extracted_images"

# Create the output directory if it doesn't exist
mkdir -p "$OUTPUT_DIR"

# Extract images from the JAR file
unzip -j "$JAR_FILE" "images/*" -d "$OUTPUT_DIR"

echo "Images extracted to $OUTPUT_DIR"
