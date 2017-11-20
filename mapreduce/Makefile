HHOME= /opt/hadoop/1.2.1
HADOOP = ${HHOME}/bin/hadoop
HADOOPLIB = ${HHOME}
 

JFLAGS = -d target
JAVAC = javac
#CLASSPATH= ${HADOOP}/hadoop-0.20.3-dev-core.jar:${HADOOP}/commons-cli-1.2.jar 
CLASSPATH= ${HADOOPLIB}/hadoop-core-1.2.1.jar:${HADOOPLIB}/hadoop-client-1.2.1.jar 


all: wordcount.jar

wordcount.jar: 
	mkdir -p target
	$(JAVAC) -classpath $(CLASSPATH)   $(JFLAGS) src/*.java
#	$(JAVAC) -classpath `$(HADOOP) classpath` $(JFLAGS) src/*.java
	jar -cvf wordcount.jar  -C target/ .

submit:
	sbatch submit-hadoop-comet.sh

clean:
	rm target/*.class *.jar
