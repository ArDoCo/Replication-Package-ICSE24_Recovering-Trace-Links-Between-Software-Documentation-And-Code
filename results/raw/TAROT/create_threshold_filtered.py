import os
from typing import List


def create_threshold_file(matrix, threshold_filename, current_threshold):
    result_lines = []
    ids_to_map = matrix[0]

    for row in matrix:
        sentence = row[0]
        if len(sentence) == 0:
            continue
        sentence_no = int(row[0])
        for (i, entry) in enumerate(row):
            if i == 0:
                continue
            if float(entry) >= current_threshold:
                result_lines.append(f"{sentence_no},{ids_to_map[i]}")

    with open(threshold_filename, 'w') as result_file:
        result_file.write("sentence_no,elementID\n")
        for line in result_lines:
            result_file.write(f"{line}\n")


def handle_file(filename, identifier):
    # Load CSV as matrix
    with open(filename, 'r') as f:
        lines = f.readlines()
        matrix: List[List[str]] = [list(map(lambda entry: entry.strip("\""), l.strip().split(','))) for l in lines]

    step_size = 0.05
    current_threshold = 0.0
    while current_threshold <= 1.0:
        threshold_filename = "threshold_filtered/" + identifier + "_" + ("{:.2f}".format(current_threshold)) + ".csv"
        create_threshold_file(matrix, threshold_filename, current_threshold)
        current_threshold += step_size
        current_threshold = round(current_threshold, 2)


def main():
    if os.path.exists("threshold_filtered"):
        for root, dirs, files in os.walk("threshold_filtered", topdown=False):
            for name in files:
                os.remove(os.path.join(root, name))
            for name in dirs:
                os.rmdir(os.path.join(root, name))
        os.rmdir("threshold_filtered")
    os.mkdir("threshold_filtered")

    # Iterate over all .csv files in the current directory
    files = [f for f in os.listdir('.') if os.path.isfile(f) and f.endswith('.csv')]
    for f in files:
        identifier = f[0:f.rfind("_")]
        handle_file(f, identifier)


if __name__ == '__main__':
    main()
