#!/bin/bash

# Check if the required arguments are provided
if [ "$#" -ne 2 ]; then
  echo "Usage: $0 <directory_path> <output_csv>"
  exit 1
fi

# Extract the directory path and output CSV file path from the arguments
directory_path="$1"
output_csv="$2"

# Create the CSV file with headers
echo "commit_id,summary,diff,files,commit_time" > "$output_csv"

# Use 'find' to locate Java and Shell script files recursively and 'cat' to read file contents
find "$directory_path" -type f \( -name "*.java" -o -name "*.sh" \) | while read -r file; do
  relative_path="${file#$directory_path/}"  # Get the relative file path
  file_content=$(cat "$file" | sed -e 's/"/""/g')  # Read and escape the file content
  echo "$relative_path,\"$file_content\"" >> "$output_csv"  # Append to the CSV
done

echo "CSV file '$output_csv' created."

