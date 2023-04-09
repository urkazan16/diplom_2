package site.nomoreparties.stellarburgers.constants.request;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Header {
    protected static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api/";

    protected RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();
    }

    protected RequestSpecification getRequestAuthSpec(String body) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("authorization", body)
                .setBaseUri(BASE_URL)
                .build();
    }
}
