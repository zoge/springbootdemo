package com.gere.demo.apiTest;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import java.util.UUID;
import static org.hamcrest.Matchers.*;

class ContactsApiIT extends ApiTestBase {

    @Test
    @DisplayName("GET /api/contacts lapozva, personId szűréssel opcionális")
    void listContacts_ok() {
        given()
                .accept(ContentType.JSON)
                .queryParam("page", 0)
                .queryParam("size", 3)
                .when()
                .get("/api/contacts")
                .then()
                .statusCode(200)
                .body("size", greaterThan(2))
                .body("content", notNullValue());
    }

    @Test
    @DisplayName("POST /api/contacts -> 201 és ContactRes")
    void createContact_created() {
        var payload = """
      {
        "addressId": 2,
        "type": "EMAIL",
        "value": "anna.kovacs@example.com"
      }
      """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/contacts")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("addressId", is(2))
                .body("type", equalTo("EMAIL"))
                .body("value", equalTo("anna.kovacs@example.com"));
    }

    @Test
    @DisplayName("GET /api/contacts/{id} nem létező -> 404 ProblemDetail")
    void getContact_notFound() {
        given()
                .accept("application/problem+json")
                .when()
                .get("/api/contacts/{id}", 999999)
                .then()
                .statusCode(404)
                .contentType(containsString("application/problem+json"))
                .body("status", is(404));
    }

    @Test
    @DisplayName("PATCH /api/contacts/{id} -> 200 vagy 404 (ContactUpdateReq figyelem: personId kell, nem addressId)")
    void updateContact_ok_or404() {
        var upd = """
      {
        "personId": 1,
        "type": "MOBILE",
        "value": "+3612345678"
      }
      """;
        given()
                .contentType(ContentType.JSON)
                .body(upd)
                .when()
                .patch("/api/contacts/{id}", 1)
                .then()
                .statusCode(anyOf(is(200), is(404)));
    }

    private Integer createContact() {
        var uniq = UUID.randomUUID().toString().substring(0, 8);
        var payload = """
      {
        "addressId": 2,
        "type": "EMAIL",
        "value": "anna.kovacs-%s@example.com"        
      }""".formatted(uniq, uniq);

        Integer id
                = given()
                        .contentType(ContentType.JSON)
                        .body(payload)
                        .when()
                        .post("/api/contacts")
                        .then()
                        .statusCode(201)
                        .body("id", notNullValue())
                        .extract().path("id");

        return id;
    }

    private void deleteContactIgnore404(long id) {
        given()
                .when()
                .delete("/api/contacts/{id}", id)
                .then()
                .statusCode(anyOf(is(204), is(404))); // 404 is ok: már nincs mit törölni
    }

    @Test
    @DisplayName("create → delete → GET=404 (független, újrafuttatható)")
    void createThenDelete_isolatedAndRerunnable() {
        var contactId = createContact();
        try {
            // 1) törlés
            given()
                    .when()
                    .delete("/api/contacts/{id}", contactId)
                    .then()
                    .statusCode(204);

            // 2) ellenőrzés: már nem létezik
            given()
                    .accept("application/problem+json")
                    .when()
                    .get("/api/contacts/{id}", contactId)
                    .then()
                    .statusCode(404)
                    .contentType(containsString("application/problem+json"))
                    .body("status", is(404));
        } finally {
            // Biztonsági takarítás, ha a fenti assertek előtt dőlne el a teszt:
            deleteContactIgnore404(contactId);
        }
    }

}
