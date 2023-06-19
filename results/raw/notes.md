If an output format does not fit 100%, it is usually best to simply transform the output.

For example, FTLR had the output format where the sentences were trailed with ".txt" (due to the artifact=file with the sentence having that name) 
and the separator was a ";" instead of a ",".

Then, you can use the following commands:
sed -i 's/\.txt;/,/g' *
sed -i 's/;/,/g' *