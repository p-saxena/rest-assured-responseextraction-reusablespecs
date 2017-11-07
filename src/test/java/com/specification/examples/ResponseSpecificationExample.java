package com.specification.examples;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by watchmaster on 9/11/17.
 */
public class ResponseSpecificationExample {

    static RequestSpecBuilder builder;
    static RequestSpecification rspec;

    static ResponseSpecBuilder responseBuilder;
    static ResponseSpecification responseSpec;

    static Map<String,Object> responseHeaders=new HashMap<String, Object>();

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

        // Building Response
        responseHeaders.put("Content-Type","application/json;charset=utf-8");
        responseHeaders.put("Server","ATS");

        responseBuilder=new ResponseSpecBuilder();
        responseBuilder.expectBody("query.count",equalTo(4));
        responseBuilder.expectStatusCode(200);
        responseBuilder.expectHeaders(responseHeaders);

        responseSpec = responseBuilder.build();
    }

    /**
     * validating response headers
     */

    @Test
    public void test001(){
        given()
                .spec(rspec)
                .when()
                .get("/yql")
                .then()
                .spec(responseSpec);
    }
}
