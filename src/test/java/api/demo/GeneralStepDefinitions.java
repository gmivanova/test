package api.demo;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GeneralStepDefinitions {
    private RestClient restClient;
    private Scenario scenario;

    public GeneralStepDefinitions(RestClient restClient) {
        this.restClient = restClient;

    }


    @Before
    public void beforeScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @After
    public void afterScenario() {
        if(scenario.getStatus().equals("failed")) {
            scenario.write("Log stuff here. It goes in the Cucumber report");
        }
    }

    @Given("^I load the request \\{(.*)\\} with profile \\{(.*)\\}$")
    public void I_load_the_request__with_profile_(String jsonName, String profileName) throws Throwable {
//        System.out.println(RestUtil.getProfileFromJson(jsonName, profileName));
        restClient.setCurrentRequest(RestUtils.getProfileFromJson(jsonName, profileName));
    }

    @Then("^the status code should be \\{(\\d+)\\}$")
    public void the_status_code_should_be(int statusCode) throws Throwable {
        assertEquals(statusCode, restClient.getLastResponse().getStatusCode());
    }

    @Then("^the response is valid according to the \\{(.*?)\\} schema profile \\{(.*?)\\}$")
    public void the_response_is_valid_according_to_the_schema_profile_(String schemaName, String profileName) throws Throwable {
        assertTrue(RestUtils.validateJsonSchema(restClient.getLastResponse().getMessage(),
                RestUtils.getProfileFromJson(schemaName, profileName)));
    }

    /**
     * Adds the supplied cucumber table to the parameter map used for url encoded requests
     * Clears the map before
     * @param paramsTable the cucumber table
     * @throws Throwable
     */
    @Given("^I set the parameters$")
    public void I_set_the_parameters(Map<String, String> paramsTable) throws Throwable {
        restClient.getParams().clear();
        restClient.getParams().putAll(paramsTable);
    }

    /**
     * Adds the supplied cucumber table to the headers map
     * Clears the map before
     * @param headersTable the cucumber table
     * @throws Throwable
     */
    @And("^I set the headers$")
    public void I_set_the_headers(Map<String, String> headersTable) throws Throwable {
        restClient.getHeaders().clear();
        restClient.getHeaders().putAll(headersTable);
    }

    /**
     * Sets the version used for subsequent requests
     * @param version the version that will be set
     * @throws Throwable
     */
    @And("^I set the version to \\{(.*?)\\}$")
    public void I_set_the_version_to_v_(String version) throws Throwable {
        restClient.setVersion(version);
    }

    @When("^I get the number \\{(.*?)\\}$")
    public void I_get_the_number_(String number) throws Throwable {
        restClient.sendGetRequest("http://dry-meadow-5230.herokuapp.com/number/", number);
    }

    @When("^I post to test$")
    public void I_post_to_test() throws Throwable {
        restClient.sendPostRequest("http://dry-meadow-5230.herokuapp.com/test", restClient.getCurrentRequest());
    }
}
