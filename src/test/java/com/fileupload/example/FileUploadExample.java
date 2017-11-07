package com.fileupload.example;

import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

/**
 * Created by watchmaster on 9/15/17.
 */
public class FileUploadExample {

    /**
     * Upload a gif file to zamzar.com and convert it into a png file
     * It is important to know how to pass api key in request. For this, refer developer page for api: https://developers.zamzar.com/docs#section-Start_a_conversion_job
     * The python guide for this api states that authentication used by api is http basic auth. API_KEY is passed as username. password is left blank.
     * source file and target format needs to specified in request
     * When sending larger amount of data to the server it's common to use the multipart form data technique.
     * Rest Assured provide methods called multiPart that allows you to specify a file/byte-array/input stream or text to upload.
     * It's also possible to supply multiple "multi-parts" entities in the same request, like we have done in the eg below
     */
    @Test
    public void uploadFileExample(){

        //PUT YOU API KEY HERE
        String apiKey="66d02902587c9d6daeea4ebe61c80b047fc7cb81";

        File inputFile = new File(System.getProperty("user.dir")+File.separator+"dancing_banana.gif");
        System.out.println(inputFile.length());

        String endpoint = "https://sandbox.zamzar.com/v1/jobs";

        given()
                .auth()
                .basic(apiKey,"")
                .multiPart("source_file",inputFile)
                .multiPart("target_format","png")
                .when()
                .post(endpoint)
                .then()
                .log()
                .all();
    }
}
