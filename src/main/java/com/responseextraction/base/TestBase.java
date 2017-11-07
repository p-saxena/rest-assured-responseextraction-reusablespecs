package com.responseextraction.base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

/**
 * Created by watchmaster on 8/31/17.
 */
public class TestBase {

    /**
     * do initialization which is part of 'given' section in @test.
     */
    @BeforeTest
    public static void init()
    {
        RestAssured.baseURI="https://query.yahooapis.com";
        RestAssured.basePath="/v1/public";
    }
}
