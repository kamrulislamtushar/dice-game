## Intro
DiceGame app is a simple REST api based game app which uses a special dice to
calculate score for the players. The application is built with Java. 

### Requirements

1. Java 
2. Maven 
3. Docker (Optional)



## Project Structure


`/src/*` structure follows default Java structure.

## Development



Run the following commands in terminal to create a blissful development experience.

```
./mvnw
```



## Building for production

### Packaging as jar

To build the final jar and optimize the  application for production, run:

```
./mvnw -Pprod clean verify
```


Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./mvnw -Pprod,war clean verify
```

## Testing

To launch your application's tests, run:

```
./mvnw verify
```


```
mvn test
```




## DB Connection

This application uses H2 in memory to connect to DB.

## Docker build
To build your Spring Boot docker container, you will use the docker build command and provide a tag or a name for the container, so you can reference it later when you want to run it. The final part of the command tells Docker which directory to build from.
```
docker build -t dice-app
```

The final step is to run the container you have just built using Docker:

```
docker run -it -p 8080:8080 dice-app
```
The command tells Docker to run the container and forward the exposed port 8080 to port 8080 on your local machine. After you run this command, you should be able to visit http://localhost:8080 in your browser.

### Check  docker process status
```
docker ps -a
```

Also, you can use docker compose command to run the container 
```
docker-compose -f src/main/docker/app.yml up -d
```