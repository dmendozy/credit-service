Credit service
======================
# Getting Started
This project is based on Spring Boot microservices using the reactive stack, read more info there https://spring.io/reactive

## Frameworks 
* Spring Boot
* Spring Data - Reactive Mongo
* Webflux

## Setup
* Java 1.8
* Maven
* Mongo
* Docker

# Building
## Windows
1. Install [Docker Desktop](https://www.docker.com/products/docker-desktop).
2. Create an image and container for credit-service using the following code:
```
mvn install
docker build . -t credit-service
docker run -p 8090:8090 --name credit-service credit-service
```
# CRUD

| HTTP Verb  |     `/credits`  |      `/credits/{creditId}`      |   
| ---------- | :---------------: | :---------------: |
| **POST**| ADD new credit | - |  
| **GET**| GET all credits | GET credit by Id |
| **PUT**| - | EDIT credit by Id|  
| **DELETE**| - |DELETE credit by Id|  


# Operations
| HTTP Verb  |     `credits/customer/{customerId}`  |`credits/search/{bankId}/{firstDate}/{lastDate}`  |  `credits/expiration/{customerId}`  |
| ---------- | :---------------: |:---------------: |:---------------: |
| **GET**| GET customers by customerId |GET credits by date range |GET credits expirates by customerId |

| HTTP Verb  |     `/credits/pay/{creditId}/{amount}`  |`/credits/charge/{creditId}/{amount}`  |
| ---------- | :---------------: |:---------------: |
| **GET**|Pay credit|Add credit comsumption|


# Architecture

![Architecture](https://raw.githubusercontent.com/dmendozy/config-service/master/files/arch.png)

# Authors

* **Danny Mendoza Yenque** - *Everis Bootcamp Microservices July 2020* - [DannyMendoza](https://github.com/dmendozy)
