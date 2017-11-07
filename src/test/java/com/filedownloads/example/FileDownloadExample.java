package com.filedownloads.example;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

/**
 * Created by watchmaster on 9/15/17.
 */
public class FileDownloadExample {

    /**
     * download a file and compare it with an expected file
     * check file size
     */
    @Test
    public void verifyDownloadedFile(){

        // read input file
        File inputFile=new File(System.getProperty("user.dir")+File.separator+"geckodriver-v0.18.0-arm7hf.tar.gz");

        // length of input file
        int expectedSize=(int) inputFile.length();

        System.out.println("The size of expected file is: "+expectedSize+ "bytes");

        // Do a file downlaod and compare it with expected file

        byte[] actualValue=given()
                .when()
                .get("https://github.com/mozilla/geckodriver/releases/download/v0.18.0/geckodriver-v0.18.0-arm7hf.tar.gz")
                .then()
                .extract()
                .asByteArray();

        System.out.println("The size of actual file is: "+actualValue.length+ "bytes");

        Assert.assertEquals(expectedSize,actualValue.length);

    }
}
