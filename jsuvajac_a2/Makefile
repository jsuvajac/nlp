JAVAC=javac
CLASSPATH=opennlp-tools-1.9.1.jar:.

all: PreProcessor.class OfflineProcessor.class OnlineProcessor.class

%.class: %.java
	$(JAVAC) -cp $(CLASSPATH) $^
pre:
	java -cp $(CLASSPATH) PreProcessor sample.tokenized stopwords.txt > sample.out
off:
	java OfflineProcessor < sample.out
on:
	java -cp $(CLASSPATH) OnlineProcessor dictionary.txt postings.txt docids.txt
clean:
	rm -f *.class *~
