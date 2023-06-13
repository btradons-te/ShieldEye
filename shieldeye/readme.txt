required jvm 17 and up
build: mvn clean package
execute with sdavc server IP argument: java -jar target\shieldeye-0.0.1-SNAPSHOT.jar 10.56.198.128
see greeting message: wget http://localhost:7070/greeting
security scan: wget http://localhost:7070/security-scan?segment=theSegment&periodInMinutes=60