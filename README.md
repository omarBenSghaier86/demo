# storeApp

This application was generated using JHipster 8.1.0, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v8.1.0](https://www.jhipster.tech/documentation-archive/v8.1.0).

This is a "microservice" application intended to be part of a microservice architecture, please refer to the [Doing microservices with JHipster][] page of the documentation for more information.
This application is configured for Service Discovery and Configuration with . On launch, it will refuse to start if it is not able to connect to .

## Project Structure
this project implement two business (Sandwich, Ingredients)
we use spring MVC to implement ths Business
we use spring security to validate role

## Api Structure
* add ingredients :
POST http://127.0.0.1:8090/api/ingredients
payload :
{
"name": "a29",
"code": "a29",
"price": 200,
"qty": 100
}
* Oreder :
 get lis of ingredient with qantity needed
GET http://127.0.0.1:8090/api/sandwich/order
payload: 
[
{
        "id": 3105,
        "name": "a29",
        "code": "a29",
        "price": 200,
        "qty": 10
    }
]
* Best Ingredients :
GET http://127.0.0.1:8090/api/ingredients/bestIngr
* Number of Sandwich Sold :
GET http://127.0.0.1:8090/api/sandwich/counSandwichSolde
* Benefits per Day 
GET http://127.0.0.1:8090/api/sandwich/bestDay?date=2024-01-02


## Run Project

- Install jdk17
- Install Apache maven
- Install Docker
- Install docker compose
- Pull and run Kafka Image 
  docker compose -f src/main/docker/kafka.yml up -d
- Pull and run Postgres Image
  docker compose -f src/main/docker/postgres.yml up -d
- Build Project 
 mvn clean install
- Run project with maven
./mvnw
- Run project with docker
  docker compose -f src/main/docker/app.yml up -d

## Hazelcast
 as provider to perform the transaction between application and database ,
 it s used on high level treatment , for our case we case wee need only to 
 Handle the high availability of order ,
 Kafka answer well for this case 
