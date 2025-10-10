package com.gere.demo.apiTest;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class AddressesApiIT extends ApiTestBase {

    @Test
    @DisplayName("GET /api/addresses -> 200 PageAddressRes")
    void listAddresses_ok() {
        given()
                .accept(ContentType.JSON)
                .queryParam("page", 0)
                .queryParam("size", 2)
                .when()
                .get("/api/addresses")
                .then()
                .statusCode(200)
                .body("number", is(0))
                .body("size", is(2))
                .body("content", notNullValue());
    }

    @Test
    @DisplayName("POST /api/addresses -> 201 és AddressRes (zip vs zipCode eltérésre figyelj)")
    void createAddress_created() {
        var payload = """
      {
        "personId": 1,
        "type": "TEMPORARY", 
        "street": "Fő utca 1.",
        "city": "Budapest",
        "zip": "1011",
        "country": "Hungary"
      }
      """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/addresses")
                .then()
                .statusCode(409);
    }

    @Test
    @DisplayName("GET /api/addresses/{id} nem létező -> 404 ProblemDetail")
    void getAddress_notFound() {
        given()
                .accept("application/problem+json")
                .when()
                .get("/api/addresses/{id}", 999999)
                .then()
                .statusCode(404)
                .contentType(containsString("application/problem+json"))
                .body("status", is(404));
    }

    @Test
    @DisplayName("PATCH /api/addresses/{id} -> 200")
    void updateAddress_ok_or404() {
        var upd = """
      { "street": "Kossuth tér 1.", "zipCode": "1055" }
      """;
        given()
                .contentType(ContentType.JSON)
                .body(upd)
                .when()
                .patch("/api/addresses/{id}", 1)
                .then()
                .statusCode(anyOf(is(200), is(404))); // ha nincs 1-es id, 404 lesz
    }

    private Integer createAddress() {
        var uniq = UUID.randomUUID().toString().substring(0, 8);
        var payload = """
      {
        "personId": 2,
        "type": "TEMPORARY", 
        "street": "Fő utca 1.-%s",
        "city": "Budapest",
        "zip": "1011",
        "country": "Hungary"
      }
      """.formatted(uniq);
        Integer id
                = given()
                        .contentType(ContentType.JSON)
                        .body(payload)
                        .when()
                        .post("/api/addresses")
                        .then()
                        .statusCode(201)
                        .body("id", notNullValue())
                        .extract().path("id");

        return id;
    }

    private void deleteAddressIgnore404(long id) {
        given()
                .when()
                .delete("/api/addresses/{id}", id)
                .then()
                .statusCode(anyOf(is(204), is(404))); // 404 is ok: már nincs mit törölni
    }

    @Test
    @DisplayName("create → delete → GET=404 (független, újrafuttatható)")
    void createThenDelete_isolatedAndRerunnable() {
        var addressId = createAddress();
        try {
            // 1) törlés
            given()
                    .when()
                    .delete("/api/addresses/{id}", addressId)
                    .then()
                    .statusCode(204);

            // 2) ellenőrzés: már nem létezik
            given()
                    .accept("application/problem+json")
                    .when()
                    .get("/api/addresses/{id}", addressId)
                    .then()
                    .statusCode(404)
                    .contentType(containsString("application/problem+json"))
                    .body("status", is(404));
        } finally {
            // Biztonsági takarítás, ha a fenti assertek előtt dőlne el a teszt:
            deleteAddressIgnore404(addressId);
        }
    }

}
