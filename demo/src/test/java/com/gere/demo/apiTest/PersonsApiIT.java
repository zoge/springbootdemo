package com.gere.demo.apiTest;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import java.util.UUID;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.anyOf;

class PersonsApiIT extends ApiTestBase {

    @Test
    @DisplayName("GET /api/persons -> 200 és tömb")
    void listPersons_ok() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/persons")
                .then()
                .statusCode(anyOf(is(200), is(204))) // ha üres, lehet 200 üres tömb; 204 is elfogadható, ha így implementáltad
                .contentType(anything()); // nem szigorítunk, mert a spec-ben */* szerepel
    }

    @Test
    @DisplayName("POST /api/persons -> 201 és PersonRes")
    void createPerson_created() {
        var payload = """
      {
        "firstName": "Anna",
        "lastName": "Kovacs",
        "birthDate": "2000-01-01T00:00:00Z"
      }
      """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/persons")
                .then()
                .statusCode(201)
                .contentType(anything())
                .body("id", notNullValue())
                .body("firstName", equalTo("Anna"))
                .body("lastName", equalTo("Kovacs"));
    }

    @Test
    @DisplayName("POST /api/persons hiányzó kötelező mező -> 400 (validation)")
    void createPerson_validationError() {
        var invalid = """
      {
        "firstName": "OnlyFirst"
      }
      """;
        given()
                .contentType(ContentType.JSON)
                .body(invalid)
                .when()
                .post("/api/persons")
                .then()
                .statusCode(anyOf(is(400), is(422))) // implementációtól függ
                .contentType(containsString("application/problem+json"))
                .body("title", notNullValue())
                .body("status", anyOf(is(400), is(422)));
    }

    @Test
    @DisplayName("GET /api/persons/{id} nem létező -> 404 ProblemDetail")
    void getPerson_notFound() {
        given()
                .accept("application/problem+json")
                .when()
                .get("/api/persons/{id}", 999999)
                .then()
                .statusCode(404)
                .contentType(containsString("application/problem+json"))
                .body("status", is(404));
    }

    private Integer createPerson() {
        var uniq = UUID.randomUUID().toString().substring(0, 8);
        var payload = """
      {
        "firstName": "Anna-%s",
        "lastName": "Kovacs-%s",
        "birthDate": "2000-01-01T00:00:00Z"
      }""".formatted(uniq, uniq);

        Integer id
                = given()
                        .contentType(ContentType.JSON)
                        .body(payload)
                        .when()
                        .post("/api/persons")
                        .then()
                        .statusCode(201)
                        .body("id", notNullValue())
                        .extract().path("id");

        return id;
    }

    private void deletePersonIgnore404(long id) {
        given()
                .when()
                .delete("/api/persons/{id}", id)
                .then()
                .statusCode(anyOf(is(204), is(404))); // 404 is ok: már nincs mit törölni
    }

    @Test
    @DisplayName("create → delete → GET=404 (független, újrafuttatható)")
    void createThenDelete_isolatedAndRerunnable() {
        var personId = createPerson();
        try {
            // 1) törlés
            given()
                    .when()
                    .delete("/api/persons/{id}", personId)
                    .then()
                    .statusCode(204);

            // 2) ellenőrzés: már nem létezik
            given()
                    .accept("application/problem+json")
                    .when()
                    .get("/api/persons/{id}", personId)
                    .then()
                    .statusCode(404)
                    .contentType(containsString("application/problem+json"))
                    .body("status", is(404));
        } finally {
            // Biztonsági takarítás, ha a fenti assertek előtt dőlne el a teszt:
            deletePersonIgnore404(personId);
        }
    }

    @Test
    @DisplayName("PATCH /api/persons/{id} -> 200 és frissített PersonRes")
    void updatePerson_ok() {
        var upd = """
      { "firstName": "Annus" }
      """;
        given()
                .contentType(ContentType.JSON)
                .body(upd)
                .when()
                .patch("/api/persons/{id}", 1)
                .then()
                .statusCode(200)
                .body("firstName", equalTo("Annus"));
    }

    @Test
    @DisplayName("GET /api/persons/search lapozás és szűrés")
    void searchPersons_paged() {
        given()
                .accept(ContentType.JSON)
                .queryParam("lastName", "Kov")
                .queryParam("page", 0)
                .queryParam("size", 5)
                .queryParam("sort", "lastName,asc")
                .when()
                .get("/api/persons/search")
                .then()
                .statusCode(200)
                .body("size", is(5))
                .body("number", is(0))
                .body("content", notNullValue());
    }
}
