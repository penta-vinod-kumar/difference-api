WAES Coding Test
================
#Goal
The goal of this assignment is to show your coding skills and what you value in software
engineering. We value new ideas so next to the original requirement feel free to
improve/add/extend.
We evaluate the assignment depending on your role (Developer/Tester) and your level of
seniority.
#The assignment
   Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints
 
     <host>/v1/diff/<ID>/left and <host>/v1/diff/<ID>/right 
   The provided data needs to be diff-ed and the results shall be available on a third endpoint

         <host>/v1/diff/<ID>
   The results shall provide the following info in JSON format
  
        o If equal return that
        o If not of equal size just return that
        o If of same size provide insight in where the diffs are, actual diffs are not needed.
            § So mainly offsets + length in the data

   Make assumptions in the implementation explicit, choices are good but need to be communicated
   
### How to start application
1. You need to have Java 8 and Maven installed on your system.
2. Make sure port 9082 is free since application is going to run default on port 9082.
3. to start application from cmd

        mvn clean install
        java -jar target\ difference-api-0.0.1-SNAPSHOT.jar 

or we can start from idea/STS by running DifferenceApiApplication.java.

# Technical Stack
* Java 8
* Spring Boot 2
* Lombok
* Swagger 2
* mongodb(from mlabs)

### documentation
Documentation is available [here](http://localhost:9082/swagger-ui.html)

### choices and decisions made
1. Used Spring boot 2.3.4.RELEASE
2. Used Swagger for api documentation.
3. Used mongodb database from mlabs to store left and right sides of compare document
4. created a different spring profile & database for testing with junit.

### additional implementations
#### Dockerized application
###### Below are the commands to build and run images with docker-compose file.
1. Run 'docker-compose build' to build the api container locally.
2. Run 'docker-compose up' to get a running api.