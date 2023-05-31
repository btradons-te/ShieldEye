required jvm 17 and up
build: mvn clean package
execute: java -jar target\shieldeye-0.0.1-SNAPSHOT.jar
test: http://localhost:7070/greeting