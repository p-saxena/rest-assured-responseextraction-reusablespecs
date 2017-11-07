package com.responseextraction.rootpath;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Created by watchmaster on 9/10/17.
 */
public class RootpathExamples {

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
     * multiple asserts in a single test
     * using root path
     * if there is only one root path, instead of specifying it in test method, we can also specify it in @BeforeClass as:
     * RestAssured.rootPath="query.results.rate";
     */

    @Test
    public void test001(){
        given()
                .parameters(parameters)
                .when()
                .get("/yql")
                .then()

                .root("query.results.rate")
                .body("Name", hasItem("USD/INR"))
                .body("id", hasItem("USDAUD"))
                .body("Name", hasItems("USD/INR","USD/AUD","USD/THB"))

                .root("query")
                .body("count", equalTo(4))
                .body("count", lessThan(8));
    }
}
