package api.demo;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.ExtractableResponse;
import com.jayway.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;


public class RestClient {
    private String baseUrl;
    private String version = "";
    private String currentRequest;
    private String contentType;
    private Map<String, String> params = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> urlParams = new HashMap<>();
    private List<Response> responses = new ArrayList<>();

    public RestClient(){
        this.baseUrl = getEnv();
    }

    public static void main(String[] args) throws Throwable {
        RestClient rc = new RestClient();
        String a = RestUtils.getProfileFromJson("WebSession.json", "create");
        System.out.println(a);
    }

//    public String getServiceId() throws IOException {
//        return api.demo.RestUtils.getValueOfKeyFromJson("serviceId", currentRequest).get(0);
//    }

    public String getEnv(){
        //TODO: Get ENV in a smart way
        return "";
    }

    /**
     * If the version has not been set, it returns "v1" as default
     * @return the current version
     */
    public String getVersion(){
        if (version.equals(""))
            return "v1";
        else
            return version;
    }

    /**
     * Sets the current version. This will be used in the next request.
     * @param version the version of the api that will be used for the request
     */
    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setCurrentRequest(String currentRequest) {
        this.currentRequest = currentRequest;
    }

    public String getCurrentRequest() {
        return currentRequest;
    }

    public Map<String, String> getUrlParams() {
        return urlParams;
    }


    public List<Response> getResponses() {
        return responses;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void sendGetRequest(String path, String param) throws Throwable{
        String temp = this.baseUrl + path + param;
        System.out.println(param);
        System.out.println(temp);
        ExtractableResponse response = given()
                .contentType("application/json")
                .log().all()
                .get(this.baseUrl + path + param)
                .then()
                .log().all()
                .extract();
        responses.add(new Response(response.body().asString(), response.statusCode(), response.headers().toString()));
    }


    public void sendPostRequest(String path, String body) throws Throwable {
        RequestSpecification rs = given().contentType(ContentType.JSON);
        if(headers.size() > 0) {
            rs = rs.headers(headers);
        }
        if(urlParams.size() > 0) {
            rs = rs.queryParams(urlParams);
        }
        if(params.size() > 0) {
            rs =  rs.params(params);
        }
        if(!body.equals("")) {
            rs = rs.body(body);
        }
        ExtractableResponse response = rs
                .log().all()
                .post(this.baseUrl + path)
                .then()
                .log().all()
                .extract();
        responses.add(new Response(response.body().asString(), response.statusCode(), response.headers().toString()));
    }

    public void sendDeleteRequest(String path, String param) throws Throwable {
        System.out.println(path);
        System.out.println(param);
        ExtractableResponse response = given()
                .contentType(ContentType.JSON)
                .log().all()
                .delete(this.baseUrl + path + param)
                .then()
                .log().all()
                .extract();
        responses.add(new Response(response.body().asString(), response.statusCode(), response.headers().toString()));
        System.out.println(getLastResponse().toString());
    }

    public void sendPutRequest(String path, String body) throws Throwable {
        ExtractableResponse response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .put(this.baseUrl + path)
                .then()
                .log().all()
                .extract();
        responses.add(new Response(response.body().asString(), response.statusCode(), response.headers().toString()));
    }

    public Response getLastResponse() {
        return responses.get(responses.size() - 1);
    }
}


