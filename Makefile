JAVAC=/usr/bin/javac

.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin
DOC=./doc

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

BIN_CLASSES= Terrain.class FlowPanel.class Flow.class
SOURCE_CLASSES= Terrain.java Water.java Simulation.java FlowPanel.java Flow.java
CLASS_FILES=$(BIN_CLASSES:%.class=$(BINDIR)/%.class)
SOURCE_FILES=$(SOURCE_CLASSES:%.java=$(SRCDIR)/%.java)

default:
	javac -sourcepath src/ -cp bin/ -d bin/ -g src/*.java

clean:
	rm $(BINDIR)/*.class
med:
	java -cp $(BINDIR) Flow "data/medsample_in.txt"
large:
	java -cp $(BINDIR) Flow "data/largesample_in.txt"

doc:	$(CLASS_FILES)
	javadoc  -d $(DOC) $(SOURCE_FILES)