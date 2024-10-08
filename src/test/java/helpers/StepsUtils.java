package helpers;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class StepsUtils {
    
    
    // Prepare base request with allure filter
    public static RequestSpecification requestSpecsBuilder(String url){
        return given().baseUri(url)
                .contentType(ContentType.JSON).accept(ContentType.JSON).filter(new AllureFilter());
    }

}
