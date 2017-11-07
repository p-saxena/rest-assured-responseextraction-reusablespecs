package com.responseextraction.assertions;

/**
 * Created by watchmaster on 9/10/17.
 */

import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AddingAssertions {

    static HashMap<String,Object> parameters=new HashMap<String, Object>();

    /**
     * Here we will be grouping all the request params inside a hash map to avaid repetitive code unlike currencyExchangeJsonPathExample.
     */
    @BeforeClass
    public static void init()
    {
        RestAssured.baseURI="https://query.yahooapis.com";
        RestAssured.basePath="/v1/public";

        parameters.put("q","select * from yahoo.finance.xchange where pair in(\"USDTHB\",\"USDINR\",\"USDCAD\",\"USDAUD\")");
        parameters.put("format","json");
        parameters.put("env","store://datatables.org/alltableswithkeys");
        parameters.put("diagnostics","true");
    }

    /**
     * assert on count value
     */

    @Test
    public void test001(){
        given()
                .parameters(parameters)
                .when()
                .get("/yql")
                .then()
                .body("query.count", equalTo(4));
    }

    /**
     * assert on a single name
     */

    @Test
    public void test002(){
        given()
                .parameters(parameters)
                .when()
                .get("/yql")
                .then()
                .body("query.results.rate.Name", hasItem("USD/INR"));
    }

    /**
     * assert on a multiple names
     */

    @Test
    public void test003(){
        given()
                .parameters(parameters)
                .when()
                .get("/yql")
                .then()
                .body("query.results.rate.Name", hasItems("USD/INR","USD/AUD","USD/THB"));
    }

    /**
     * assert using logical functions
     */

    @Test
    public void test004(){
        given()
                .parameters(parameters)
                .when()
                .get("/yql")
                .then()
                .body("query.count", greaterThan(3));

        given()
                .parameters(parameters)
                .when()
                .get("/yql")
                .then()
                .body("query.count", lessThan(8));
    }

    /**
     * multiple asserts in a single test
     */

    @Test
    public void test005(){
        given()
                .parameters(parameters)
                .when()
                .get("/yql")
                .then()
                .body("query.count", equalTo(4))
                .body("query.results.rate.Name", hasItems("USD/INR","USD/AUD","USD/THB"))
                .body("query.count", lessThan(8));
    }

    /**
     * using TestNG asserts
     */
    @Test
    public void test006(){
        int count=given()
                .param("q","select * from yahoo.finance.xchange where pair in(\"USDTHB\",\"USDINR\",\"USDCAD\",\"USDAUD\")")
                .param("format","json")
                .param("env","store://datatables.org/alltableswithkeys")
                .param("diagnostics","true")
                .when()
                .get("/yql")
                .then()
                .extract()
                .path("query.count");
        Assert.assertEquals(count,4);
    }
}
