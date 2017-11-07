 package com.checkresponsetime.example;

 import io.restassured.RestAssured;
 import io.restassured.builder.RequestSpecBuilder;
 import io.restassured.builder.ResponseSpecBuilder;
 import io.restassured.specification.RequestSpecification;
 import io.restassured.specification.ResponseSpecification;
 import org.testng.annotations.BeforeClass;
 import org.testng.annotations.Test;

 import java.util.HashMap;
 import java.util.Map;
 import java.util.concurrent.TimeUnit;

 import static io.restassured.RestAssured.given;
 import static org.hamcrest.Matchers.lessThan;
 import static org.hamcrest.core.IsEqual.equalTo;

 /**
 * Created by watchmaster on 9/16/17.
 */
public class VerifyTime {

    static RequestSpecBuilder builder;
    static RequestSpecification rspec;

    static ResponseSpecBuilder responsebuilder;
    static ResponseSpecification responseSpec;

    static Map<String,Object> responseHeaders = new HashMap<String,Object>();

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "https://query.yahooapis.com";
        RestAssured.basePath = "/v1/public";

        builder = new RequestSpecBuilder();

        builder.addParam("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")");
        builder.addParam("format","json");
        builder.addParam("env","store://datatables.org/alltableswithkeys");

        rspec= builder.build();

        //Building response
        responseHeaders.put("Content-Type","application/json;charset=utf-8");
        responseHeaders.put("Server","ATS");

        responsebuilder= new ResponseSpecBuilder();
        responsebuilder.expectBody("query.count",equalTo(6));
        responsebuilder.expectStatusCode(200);
        responsebuilder.expectHeaders(responseHeaders);
        responsebuilder.expectResponseTime(lessThan(5L), TimeUnit.SECONDS);

        responseSpec= responsebuilder.build();


    }

    // 1)Get the time value
    @Test
    public void test001() {
        long responseTime=	given()
                .spec(rspec)
                .log()
                .all()
                .when()
                .get("/yql")
                .time();    //default time unit is millisec

        System.out.println("The time taken is: "+responseTime+" seconds");

    }

    @Test
    public void test002() {
        long responseTime=	given()
                .spec(rspec)
                .log()
                .all()
                .when()
                .get("/yql")
                .timeIn(TimeUnit.SECONDS);

        System.out.println("The time taken is: "+responseTime+" seconds");

    }

    // assert response time is less than 5 sec using response spec
     @Test
     public void test003() {

         given()
                 .spec(rspec)
                 .log()
                 .all()
                 .when()
                 .get("/yql")
                 .then()
                 .spec(responseSpec);

     }

}
