# Assignment 2
## Jovan Suvajac
## Special Topics in NLP

# To compile:
make

# To Run:

preprocessor:
	java -cp opennlp-tools-1.9.1.jar:. PreProcessor tokenized.txt stopwords.txt > preprocessed.txt
offlineprocessor:
	java OfflineProcessor < preprocessed.txt
onlineprocessor:
	java -cp opennlp-tools-1.9.1.jar:. OnlineProcessor dictionary.txt postings.txt docids.txt

With promission from Dr. Song I have used and modified the example code from A1 and A2 for this assignment.
I have also used my A1 code as reference for A2.

# limitations/assumptions
* Java 8
* The programs are run as described above, dviations from this will break the functionality
* did in the postings file is the start-position in the docids file
* The makefile must be run from the project root directory!
* The opennlp.jar and opennlp models are not moved from where they are in the project
* Did not get to finishing the document ranking

# possible improvements
* Unit tests
* Better project organization
* More robust try-catch error checking with file reading and writing

# Test Plan:
Since the 3 programs are independent and require a tokenized document file as input,
the 3 programs can be tested separately.

Since sample test files were provided we can get its tokenized version by
running it through the sentence splitter from A1 and running the output of 
that as input into the tokenizer form A1. We can use the output of the tokenizer
as the input for preprocessing.

The output of preprocessing should match all of the require critria which can be tested manually.
The output of preprocessing can be then used as input for the Offline processing program.

The output of the offline processing can be also checked manually by computing the dictionar, postings and docids
by hand.

For offline processing the output of the program can also be checked with manuall calculation.
