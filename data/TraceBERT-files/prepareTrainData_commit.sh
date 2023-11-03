#!/bin/zsh

# Check if at least two arguments are provided
if [ "$#" -lt 3 ]; then
  echo "Usage: $0 <output.csv> <file1.csv> [file2.csv ...]"
  exit 1
fi

# Extract the first argument as the output file
output_file="$1"
shift

# Clean the output file (empty it)
truncate -s 0 "$output_file"

first_file=true

for file in "$@"; do
    if [ ! -f "$file" ]; then
        echo "File not found: $file"
        continue
    fi

    foldername=$(basename "$(dirname "$file")")

    if [ "$first_file" = "true" ]; then
        awk -v foldername="$foldername" -F, -v OFS=',' '{if (NR > 1) $1 = $1; print}' "$file" > "$output_file"
        first_file=false
    else
        # If it's not the first file, skip the header line
        tail -n +2 "$file" |  awk -v foldername="$foldername" -F, -v OFS=',' '{$1 = $1; print}' >> "$output_file"
    fi
done

