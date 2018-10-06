#Demoaccount#
## Demo API for "MoiSklad"

Current API works with PostgreSQL database. In order to fully operate, you have to create database `jeorgius` and add `bankdemo` schema. Default credentials are set as static fields of Sql class in `jeorgius.DbAccess.Sql` as follows:

- url: `jdbc:postgresql://localhost:5432/jeorgius?currentSchema=bankdemo`
- user: `postgres`
- password: `1234` 

Before installing application you may change them, however it is strongly recommended to let them stay the same.
The API will take care of creating table afterwards.

To start this project:

1. Save .zip archive or clone this repository using git: `git clone`
2. Open command prompt and head to the project root folder
3. Assuming you have Maven installed, execute `mvn clean` to delete existing target files and then perform `mvn install` to seamlessly recreate them
4. Execute `java -jar target/demoaccount-1.0-SNAPSHOT-jar-with-dependencies.jar` to launch the API

The last command will start a **GlassFish** server with **Jersey Framerwork** that has an embedded servlet container. However, since its a standalone .jar that is supposed to work on a PC, it doesn't support CDI beans yet, so the direct instances of classes are called instead.


The API receives JSON **POST**, **PUT** and **GET** requests to work with a demo bank account. 
Bank account `{id}` should contain exactly **5** digits, no other combinations allowed. You cannot create duplicate accounts.


Available commands:

1. **POST** `/bankaccount/{id}` - creates new account if it doesn't exist already. Receives a form parameter `account_number`
2. **PUT** `/bankaccount/{id}/deposit` - deposits a sum to an account. Receives 2 form parameters `account_number` and `sum`
3. **PUT** `/bankaccount/{id}/withdraw` - withdraws a sum from an account. Receives 2 form parameters `account_number` and `sum`
4. **GET** /bankaccount/{id}/balance - check

To test the functionality of API start making request using cURL

- First, create a bankaccount by typing:
`curl -v http://localhost:8080/bankaccount/00000 -X POST -H "Content-Type:application/json" -d "{\"account_number\":\"{id}\"}"
`

- To check balance of your existing account you can go to the `link http://localhost:8080/bankaccount/{id}/balance`
or you can make a cURL request:
`curl -v -X GET http://localhost:8080/bankaccount/{id}/balance`

- You can deposit sum to your account:
`curl -v http://localhost:8080/bankaccount/{id} -X PUT -H "Content-Type:application/json" -d "{\"account_number\":\"{id}\", "\deposit\":\"{value}\"}" `

- Or you can withdraw funds:
`curl -v http://localhost:8080/bankaccount/{id} -X PUT -H "Content-Type:application/json" -d "{\"account_number\":\"{id}\", "\withdraw\":\"{value}\"}"`

Here are some examples of sequent actions:

`curl -v http://localhost:8080/bankaccount/00000 -X POST -H "Content-Type:application/json" -d "{\"account_number\":\"00000\"}"`

`curl -v http://localhost:8080/bankaccount/00000/deposit -X PUT -H "Content-Type:application/json" -d "{\"account_number\":\"00000\",\"deposit\":\"9000\"}"`

`curl -v http://localhost:8080/bankaccount/00000/withdraw -X PUT -H "Content-Type:application/json" -d "{\"account_number\":\"00000\",\"withdraw\":\"7777\"}"`

`curl -v http://localhost:8080/bankaccount/00000 -X GET`

To test the API, execute:
`mvn test`

**Thank you** for testing/using my API.
