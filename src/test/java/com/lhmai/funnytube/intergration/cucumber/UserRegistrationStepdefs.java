package com.lhmai.funnytube.intergration.cucumber;

import com.lhmai.funnytube.ViewResolverFactory;
import com.lhmai.funnytube.controller.UserRegistrationController;
import com.lhmai.funnytube.service.UserService;
import com.lhmai.funnytube.service.dto.UserRegistrationDto;
import com.lhmai.funnytube.util.UrlUtils;
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
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.htmlunit.webdriver.WebConnectionHtmlUnitDriver;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ViewResolver;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Map;

public class UserRegistrationStepdefs extends SpringIntegrationTest {
    @LocalServerPort
    int randomServerPort;
    @Autowired
    private UserRegistrationController userRegistrationController;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private UserService userService;
    private HtmlUnitDriver driver;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        ViewResolver viewResolver = ViewResolverFactory.thymleafViewResolver();
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userRegistrationController).setViewResolvers(viewResolver)
                .build();
        WebConnectionHtmlUnitDriver otherDriver = new WebConnectionHtmlUnitDriver();
        driver = MockMvcHtmlUnitDriverBuilder.mockMvcSetup(mockMvc).withDelegate(otherDriver).javascriptEnabled(true).build();
    }


    @Given("^I am on register page$")
    public void iAmOnRegisterPage() {
        driver.navigate().to("http://localhost/registration");
    }

    @When("^I enter username \"([^\"]*)\" and password \"([^\"]*)\" and confirmation \"([^\"]*)\"$")
    public void iEnterUsernameAndPasswordAndConfirmation(String username, String password, String confirmation) throws Throwable {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("confirmPassword")).sendKeys(confirmation);
        driver.findElement(By.id("submit")).click();
    }

    @Then("^user should be registered successfully$")
    public void userShouldBeRegisteredSuccessfully() throws UnsupportedEncodingException, MalformedURLException {
        String currentUrl = driver.getCurrentUrl();
        Map<String, String> params = UrlUtils.getQueryParams(currentUrl);
        Assert.assertNotNull(params.get("success"));
    }


    @Then("^user should be failed to register$")
    public void userShouldBeFailedToRegister() {
        String currentUrl = driver.getCurrentUrl();
        Map<String, String> params = UrlUtils.getQueryParams(currentUrl);
        Assert.assertNull(params.get("success"));
    }


    @And("^exist username \"([^\"]*)\"$")
    public void existUsername(String username) throws Throwable {
        UserRegistrationDto user = UserRegistrationDto.builder()
                .username(username)
                .password("123456")
                .confirmPassword("123456")
                .build();
        userService.save(user);
    }


}
