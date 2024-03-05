## Pokemon Service
### What is this
A Spring Boot RESTful API that fetches random pokemons from https://pokeapi.co/
and serves them. Unique pokemons are saved in a PostgreSQL database. 
The service uses RestTemplate to make HTTP requests and has a retry feature, that will retrieve a pokemon from the database (if there is any) in case the pokemon API is not available.

### How to run
1 . mvn install
2. docker build -t pokemon
3. use the docker-compose file to run it together with postgres or setup as you see fit

### Usage and Info
The service is designed to return a basic carrier object, with a message and a pokemon. 
Various other properties can be added to the carrier object.

 Uses:
 - Java 17
 - Maven
 - PostgreSQL
 - Docker
