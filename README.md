### springbootdemo
Spring boot demo

## Feladat leírása
3 táblás minta feladat a Spring Boot Rest API végpontok bebumatására

## Adatbázis indítása

`cd /path/to/your/docker`

Építsd fel az image-et

`docker build -t mssql2019-img .`

Indítsd el a konténert

`docker run -d -p 1433:1433 --name mssql2019 mssql2019-img`

## Alkalmazás indítása

`mvn spring-boot:run`

## Tesztek indítása

`mvn test`

## Integrációs tesztek indítása:

`mvn verify`

Kimenet a következő lesz. Az integrációs teszthez kell hogy fusson egy alkalmazás példány a localhost:8080 -as címen!
<pre>
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.gere.demo.apiTest.ContactsApiIT
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.247 s -- in com.gere.demo.apiTest.ContactsApiIT
[INFO] Running com.gere.demo.apiTest.AddressesApiIT
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.215 s -- in com.gere.demo.apiTest.AddressesApiIT
[INFO] Running com.gere.demo.apiTest.PersonsApiIT
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.292 s -- in com.gere.demo.apiTest.PersonsApiIT
</pre>

## Tovább fejlesztési lehetőségek:
* Front-end fejlésztés a Spring Boot back-end részére: Mondjuk React, Vue, Svelte
* Nem érdemes lenne megvizsgálni A Spring Boot asynchron lehesőségetit egy mondjuk egy hasonló méretű projekten
