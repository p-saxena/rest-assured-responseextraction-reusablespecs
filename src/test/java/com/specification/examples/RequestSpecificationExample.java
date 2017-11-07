package com.specification.examples;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by watchmaster on 9/11/17.
 */
public class RequestSpecificationExample {

    static RequestSpecBuilder builder;
    static RequestSpecification rspec;

    @BeforeClass
    public static void init()
    {
        RestAssured.baseURI="https://query.yahooapis.com";
        RestAssured.basePath="/v1/public";

        builder=new RequestSpecBuilder();

        builder.addParam("q","select * from yahoo.finance.xchange where pair in(\"USDTHB\",\"USDINR\",\"USDCAD\",\"USDAUD\")");
        builder.addParam("format","json");
        builder.addParam("env","store://datatables.org/alltableswithkeys");

        rspec=builder.build();
    }

    /**
     * assert on count value
     */

    @Test
    public void test001(){
        given()
                .spec(rspec)
                .when()
                .get("/yql")
                .then()
                .statusCode(200);
    }
}
