
# Quick Start  
  
## Development
add the database config to the ```application.properties``` in ```src/main/resources``` folder and run below command:
```mvn spring-boot:run```  
it will start the server
  
  
## Deploy 
```  
mvn install  
# the jar should be in target folder,
# copy it to the server and run by below command:
java -jar tag-xx.jar
```
### Open Office
use docker to start a open-office server
```
docker run -d -p 6080:6080 -p 8100:8100  goodrainapps/openoffice:v4.1.5
```