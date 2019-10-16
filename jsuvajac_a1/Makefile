JAVAC=javac
JFLEX=jflex
CLASSPATH=opennlp-tools-1.9.1.jar:.

all: SentenceDetection.class Token.class Lexer.class Scanner.class POSTagging.class Stats.class

SentenceDetection.class:SentenceDetection.java
	$(JAVAC) -cp $(CLASSPATH) $^

POSTagging.class:POSTagging.java
	$(JAVAC) -cp $(CLASSPATH) $^

Stats.class:Stats.java
	$(JAVAC) -cp $(CLASSPATH) $^

%.class: %.java
	$(JAVAC) $^

Lexer.java: doc.flex
	$(JFLEX) doc.flex

# Run
sen:
	java -cp $(CLASSPATH) SentenceDetection articles.txt > splitted.txt

tok:
	java Scanner < splitted.txt > tokenized.txt

tag:
	java -cp $(CLASSPATH) POSTagging tokenized.txt > tagged.txt

stats:
	java -cp $(CLASSPATH) Stats tokenized.txt > stats.txt

# Clean
clean:
	rm -f Lexer.java *.class *~
