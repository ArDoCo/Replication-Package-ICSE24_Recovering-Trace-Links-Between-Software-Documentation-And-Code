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
echo "issue_id,issue_desc,issue_comments,closed_at,created_at" > "$output_csv"

# Use 'find' to locate files in the directory, 'basename' to remove the extension,
# and 'cat' to read file contents
find "$directory_path" -type f -exec sh -c 'file_name="$(basename "$1" .txt)"; file_content="$(cat "$1" | sed "s/\"/\"\"/g" | sed -e ":a;N;$!ba;s/\n/\\n/g")"; echo "$file_name,\"$file_content\""' _ {} \; >> "$output_csv"

echo "CSV file '$output_csv' created."

