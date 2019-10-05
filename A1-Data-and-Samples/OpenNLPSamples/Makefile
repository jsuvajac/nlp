JAVAC=javac
JFLEX=jflex
CLASSPATH=opennlp-tools-1.9.1.jar:.

all: SentenceDetection.class Token.class Lexer.class Scanner.class POSTaggingMEDemo.class TokenizerMEProbs.class

SentenceDetection.class:SentenceDetection.java
	$(JAVAC) -cp $(CLASSPATH) $^

POSTaggingMEDemo.class:POSTaggingMEDemo.java
	$(JAVAC) -cp $(CLASSPATH) $^

TokenizerMEProbs.class:TokenizerMEProbs.java
	$(JAVAC) -cp $(CLASSPATH) $^

%.class: %.java
	$(JAVAC) $^

Lexer.java: tiny.flex
	$(JFLEX) tiny.flex

tok:
	java Scanner < sample.sen > sample.splitted

sen:
	java -cp $(CLASSPATH) SentenceDetection input.txt > sample.sen

tag:
	java -cp $(CLASSPATH) POSTaggingMEDemo sample.splitted > sample.tagged

stem:
	java -cp $(CLASSPATH) TokenizerMEProbs sample.splitted > sample.stemmed

clean:
	rm -f Lexer.java *.class *~ sample.*
