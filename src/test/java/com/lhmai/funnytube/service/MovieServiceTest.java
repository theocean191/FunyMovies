package com.lhmai.funnytube.service;

import com.lhmai.funnytube.FunnytubeApplication;
import com.lhmai.funnytube.domain.MovieEntity;
import com.lhmai.funnytube.repository.MovieRepository;
import com.lhmai.funnytube.service.dto.MovieDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FunnytubeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @MockBean
    private MovieRepository movieRepository;

    @Test
    public void addYoutubeMovieFromURL_WithValidURL_SaveUrlToDatabase() throws IOException {
        when(movieRepository.save(any())).thenReturn(MovieEntity.builder()
                .url("https://www.youtube.com/watch?v=Pkoai6HCMcg")
                .tubeMovieId("Pkoai6HCMcg")
                .build());

        String youtubeUrl = "https://www.youtube.com/watch?v=Pkoai6HCMcg";
        MovieDto movie = movieService.addYoutubeMovieFromURL(youtubeUrl);

        Assert.assertNotNull(movie);
    }

    @Test
    public void addYoutubeMovieFromURL_WithInValidURL_ExceptionThrow() throws IOException {
        String someInvalidUrl = "https://www.youtube.com/watchOK";
        MovieDto movie = movieService.addYoutubeMovieFromURL(someInvalidUrl);
    }


    @Test
    public void listAll_WithMoviesInDatabase_ReturnListOfMovie() {

        List<MovieEntity> movies = createMockMovieEntities();

        when(movieRepository.findAll()).thenReturn(movies);

        List<MovieDto> movieDtos = movieService.listAll();

        Assert.assertEquals(movies.size(), movieDtos.size());
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

    @Test
    public void listAll_WithNoMovieInDatabase_ReturnEmptyList() {
        List<MovieDto> movieDtos = movieService.listAll();
        Assert.assertTrue(CollectionUtils.isEmpty(movieDtos));
    }
}
