package com.lhmai.funnytube.intergration.cucumber;

import com.lhmai.funnytube.domain.UserEntity;
import com.lhmai.funnytube.service.UserService;
import com.lhmai.funnytube.service.dto.UserRegistrationDto;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.htmlunit.webdriver.WebConnectionHtmlUnitDriver;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class LoginInHomePageStepdefs extends SpringIntegrationTest {

    @Autowired
    private UserService userService;

    private HtmlUnitDriver driver;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(springSecurityFilterChain)
                .build();
        WebConnectionHtmlUnitDriver otherDriver = new WebConnectionHtmlUnitDriver();
        driver = MockMvcHtmlUnitDriverBuilder.mockMvcSetup(mockMvc).withDelegate(otherDriver).javascriptEnabled(true).build();
    }

    @Given("^I am at login page$")
    public void iAmAtHomePage() {
        driver.navigate().to("http://localhost/login");
    }

    @And("^exist account with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void existAccountWithUsernameAndPassword(String username, String password) throws Throwable {
        UserRegistrationDto user = UserRegistrationDto.builder()
                .username(username)
                .password(password)
                .confirmPassword(password)
                .build();
        UserEntity save = userService.save(user);
    }

    @When("^I enter username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iEnterUsernameAndPasswordToLogin(String username, String password) throws Throwable {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-submit")).click();
    }

    @Then("^user should be login successfully$")
    public void userShouldBeLoginSuccessfully() {
        boolean success = driver.getPageSource().contains("login-success-zone");
        Assert.assertTrue(success);
    }

    @Then("^user should be failed to login$")
    public void userShouldBeFailedToLogin() {
        boolean notSuccess = !driver.getPageSource().contains("login-success-zone");
        Assert.assertTrue(notSuccess);
    }

}
