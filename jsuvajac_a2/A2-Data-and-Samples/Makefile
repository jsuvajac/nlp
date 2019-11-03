JAVAC=javac
CLASSPATH=opennlp-tools-1.9.1.jar:.

all: Preprocessor.class TreeMapDemo.class

%.class: %.java
	$(JAVAC) -cp $(CLASSPATH) $^
pre:
	java -cp $(CLASSPATH) Preprocessor sample.tokenized stopwords.txt > sample.out

off:
	java TreeMapDemo < test.txt

clean:
	rm -f *.class *~
