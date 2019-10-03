JAVAC=javac
CLASSPATH=opennlp-tools-1.9.1.jar:.

all: SentenceDetectionME.class POSTaggingMEDemo.class TokenizerMEProbs.class

%.class: %.java
	$(JAVAC) -cp $(CLASSPATH) $^

sen:
	java -cp $(CLASSPATH) SentenceDetectionME sample.txt > sample.splitted

tag:
	java -cp $(CLASSPATH) POSTaggingMEDemo sample.splitted > sample.tagged

tok:
	java -cp $(CLASSPATH) TokenizerMEProbs sample.splitted > sample.stemmed

clean:
	rm -f *.class *~
