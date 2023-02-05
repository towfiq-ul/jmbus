echo "Remember to set your JAVA_HOME env var"
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64/
echo "====================== Compiling Java File ======================"
javac -h . JMeterBusService.java

echo "====================== Compiling CPP File ======================"
g++ --static -c -Wall -g -DONE=1 -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux JMeterBusService.cpp -o com_github_laziestcoder_jmbus_JMeterBusService.o

echo "====================== Compiling Library File ======================"
g++ -shared -fPIC com_github_laziestcoder_jmbus_JMeterBusService.o -o ../../../../../resources/libnative.so -lc -lm -L. -lmbus

#echo "====================== Running Java File ======================"
#java -cp . JMeterBusService

#echo "====================== Creating Java Jar ======================"
#jar cf jmbuslib.jar *.class *.so
