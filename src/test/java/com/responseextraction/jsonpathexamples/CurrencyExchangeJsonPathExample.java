package com.responseextraction.jsonpathexamples;

import com.responseextraction.base.TestBase;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * Created by watchmaster on 8/31/17.
 */
public class CurrencyExchangeJsonPathExample extends TestBase {

    /**
     * extracting information from json response using jsonpath in rest assured
     * json path is way of addressing elements within json objects.
     * json path lets us traverse through a json object and access information within json object
     * we will be using yahoo currency exchange service for this demo
     * we will be extracting count value from the response
     */

    @Test
    public void test001(){
        int count=given()
                .param("q","select * from yahoo.finance.xchange where pair in(\"USDTHB\",\"USDINR\",\"USDCAD\",\"USDAUD\")")
                .param("format","json")
                .param("env","store://datatables.org/alltableswithkeys")
                .param("diagnostics","true")
                .when()
                .get("/yql")
                .then()
                .log()
                .body()
                .extract()
                .path("query.count");
        System.out.println("The value of count is "+count);
    }

    /**
     * we will be extracting name value from the first rate array
     */

    @Test
    public void test002(){
        String name=given()
                .param("q","select * from yahoo.finance.xchange where pair in(\"USDTHB\",\"USDINR\",\"USDCAD\",\"USDAUD\")")
                .param("format","json")
                .param("env","store://datatables.org/alltableswithkeys")
                .param("diagnostics","true")
                .when()
                .get("/yql")
                .then()
                .log()
                .body()
                .extract()
                .path("query.results.rate[0].Name");
        System.out.println("The value of name is "+name);
    }

    /**
     * we will be getting the size of rate array
     */

    @Test
    public void test003(){
        int sizeOfRate=given()
                .param("q","select * from yahoo.finance.xchange where pair in(\"USDTHB\",\"USDINR\",\"USDCAD\",\"USDAUD\")")
                .param("format","json")
                .param("env","store://datatables.org/alltableswithkeys")
                .param("diagnostics","true")
                .when()
                .get("/yql")
                .then()
                .log()
                .body()
                .extract()
                .path("query.results.rate.size()");
        System.out.println("The size of the rate is "+sizeOfRate);
    }

    /**
     * we will be getting all the names from the rate array in response
     */

    @Test
    public void test004(){
        List<String> names=given()
                .param("q","select * from yahoo.finance.xchange where pair in(\"USDTHB\",\"USDINR\",\"USDCAD\",\"USDAUD\")")
                .param("format","json")
                .param("env","store://datatables.org/alltableswithkeys")
                .param("diagnostics","true")
                .when()
                .get("/yql")
                .then()
                .log()
                .body()
                .extract()
                .path("query.results.rate.Name");
        System.out.println("The list of names is "+names);
    }

    /**
     * we will be getting all the values from rate index for name=USD/INR
     * findAll is a groovy method which takes closure as an argument and returns a collection
     */

    @Test
    public void test005(){
        List<String> values=given()
                .param("q","select * from yahoo.finance.xchange where pair in(\"USDTHB\",\"USDINR\",\"USDCAD\",\"USDAUD\")")
                .param("format","json")
                .param("env","store://datatables.org/alltableswithkeys")
                .param("diagnostics","true")
                .when()
                .get("/yql")
                .then()
                .extract()
                .path("query.results.rate.findAll{it.Name=='USD/INR'}");
        System.out.println("The list of values are "+values);
    }

    /**
     * we will be getting only the rate value from rate index for name=USD/INR
     */

    @Test
    public void test006(){
        List<String> value=given()
                .param("q","select * from yahoo.finance.xchange where pair in(\"USDTHB\",\"USDINR\",\"USDCAD\",\"USDAUD\")")
                .param("format","json")
                .param("env","store://datatables.org/alltableswithkeys")
                .param("diagnostics","true")
                .when()
                .get("/yql")
                .then()
                .extract()
                .path("query.results.rate.findAll{it.Name=='USD/INR'}.Rate");
        System.out.println("The rate value is "+value);
    }

    /**
     * we will be getting the list of names which have rate >10
     */

    @Test
    public void test007(){
        List<String> names=given()
                .param("q","select * from yahoo.finance.xchange where pair in(\"USDTHB\",\"USDINR\",\"USDCAD\",\"USDAUD\")")
                .param("format","json")
                .param("env","store://datatables.org/alltableswithkeys")
                .param("diagnostics","true")
                .when()
                .get("/yql")
                .then()
                .extract()
                .path("query.results.rate.findAll{it.Rate>'10'}.Name" );
        System.out.println("The list of names are "+names);
    }

    /**
     * we will be getting all the values that start with id=USDT
     */

    @Test
    public void test008(){
        List<String> names=given()
                .param("q","select * from yahoo.finance.xchange where pair in(\"USDTHB\",\"USDINR\",\"USDCAD\",\"USDAUD\")")
                .param("format","json")
                .param("env","store://datatables.org/alltableswithkeys")
                .param("diagnostics","true")
                .when()
                .get("/yql")
                .then()
                .extract()
                .path("query.results.rate.findAll{it.id==~/USDT.*/}" );
        System.out.println("The list of values are "+names);
    }

    /**
     * we will be getting all the values that end with id=UD
     */

    @Test
    public void test009(){
        List<String> names=given()
                .param("q","select * from yahoo.finance.xchange where pair in(\"USDTHB\",\"USDINR\",\"USDCAD\",\"USDAUD\")")
                .param("format","json")
                .param("env","store://datatables.org/alltableswithkeys")
                .param("diagnostics","true")
                .when()
                .get("/yql")
                .then()
                .extract()
                .path("query.results.rate.findAll{it.id==~/.*UD/}" );
        System.out.println("The list of values are "+names);
    }
}
