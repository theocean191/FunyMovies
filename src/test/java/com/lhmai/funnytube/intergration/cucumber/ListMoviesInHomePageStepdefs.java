package com.lhmai.funnytube.intergration.cucumber;

import com.lhmai.funnytube.domain.MovieEntity;
import com.lhmai.funnytube.repository.MovieRepository;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.htmlunit.webdriver.WebConnectionHtmlUnitDriver;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

public class ListMoviesInHomePageStepdefs extends SpringIntegrationTest {
    @Autowired
    private MovieRepository movieRepository;


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

    @Given("^I have a list of videos in database$")
    public void iHaveAListOfVideosInDatabase() {
        movieRepository.saveAll(createMockMovieEntities());
    }

    private List<MovieEntity> createMockMovieEntities() {
        MovieEntity movie1 = MovieEntity.builder()
                .url("https://www.youtube.com/watch?v=Pkoai6HCMcg")
                .tubeMovieId("Pkoai6HCMcg")
                .build();
        MovieEntity movie2 = MovieEntity.builder()
                .url("https://www.youtube.com/watch?v=hzQAHITIUhg")
                .tubeMovieId("hzQAHITIUhg")
                .build();

        return Arrays.asList(movie1, movie2);
    }

    @And("^I am at home page$")
    public void iAmAtHomePage() {
        driver.navigate().to("http://localhost/home");
    }

    @Then("^I should see all videos listed$")
    public void iShouldSeeAllVideosListed() {
        List<WebElement> elements = driver.findElements(By.className("movie-container"));
        boolean videoListPresent = elements.size() > 0;
        Assert.assertTrue(videoListPresent);
    }
}
