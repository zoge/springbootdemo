### springbootdemo
Spring boot demo

## Feladat leírása
3 táblás minta feladat a Spring Boot Rest API végpontok bebumatására

## Alkalmazás indítása
```bash
mvn spring-boot:run`

## Adatbázis indítása
Lépj abba a mappába, ahol a Dockerfile van
```bash
cd /path/to/your/docker`

Építsd fel az image-et
```bash
docker build -t mssql2019-img .`

Indítsd el a konténert
```bash
docker run -d \
  -p 1433:1433 \
  --name mssql2019 \
  mssql2019-img`

## Tesztek indítása
mvn test

## Integrációs tesztek indítása:

mvn verify

Kimenet a következő lesz
```bash
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.gere.demo.apiTest.ContactsApiIT
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.247 s -- in com.gere.demo.apiTest.ContactsApiIT
[INFO] Running com.gere.demo.apiTest.AddressesApiIT
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.215 s -- in com.gere.demo.apiTest.AddressesApiIT
[INFO] Running com.gere.demo.apiTest.PersonsApiIT
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.292 s -- in com.gere.demo.apiTest.PersonsApiIT
`

## Tovább fejlesztési lehetőségek:
* Front-end fejlésztés a Spring Boot back-end részére: Mondjuk React, Vue, Svelte
* Nem érdemes lenne megvizsgálni A Spring Boot asynchron lehesőségetit egy mondjuk egy hasonló méretű projekten
