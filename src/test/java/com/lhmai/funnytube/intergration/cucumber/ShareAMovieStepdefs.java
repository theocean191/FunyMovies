package com.lhmai.funnytube.intergration.cucumber;

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
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.htmlunit.webdriver.WebConnectionHtmlUnitDriver;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.constraints.NotEmpty;
import java.util.Map;
import java.util.UUID;

public class ShareAMovieStepdefs {

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


    @Given("^I am logged in$")
    public void iAmLoggedIn() {
        UserRegistrationDto newAccount = registrNewAccount();
        login(newAccount.getUsername(), newAccount.getPassword());
    }

    private void login(@NotEmpty String username, @NotEmpty String password) {
        driver.navigate().to("http://localhost/login");
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-submit")).click();
    }

    private UserRegistrationDto registrNewAccount() {
        UserRegistrationDto user = UserRegistrationDto.builder()
                .username(UUID.randomUUID().toString())
                .password("mypassword")
                .confirmPassword("mypassword")
                .build();
        userService.save(user);
        return user;
    }

    @And("^I am at share a movie page$")
    public void iAmAtShareAMoviePage() {
        driver.navigate().to("http://localhost/share");
    }

    @When("^When I enter url \"([^\"]*)\"$")
    public void whenIEnterUrl(String url) throws Throwable {
        driver.findElement(By.id("movie-url")).sendKeys(url);
        driver.findElement(By.id("share-movie")).click();
    }

    @Then("^The url should be shared successfully$")
    public void theUrlShouldBeSharedSuccessfully() {
        String currentUrl = driver.getCurrentUrl();
        Map<String, String> params = UrlUtils.getQueryParams(currentUrl);
        Assert.assertNotNull(params.get("success"));
    }

    @Then("^I should be failed to share the movie$")
    public void iShouldBeFailedToShareTheMovie() {
        String currentUrl = driver.getCurrentUrl();
        Map<String, String> params = UrlUtils.getQueryParams(currentUrl);
        Assert.assertNull(params.get("success"));
    }


}
