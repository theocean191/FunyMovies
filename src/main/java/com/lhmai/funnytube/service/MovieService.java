package com.lhmai.funnytube.service;

import com.lhmai.funnytube.domain.MovieEntity;
import com.lhmai.funnytube.exception.InvalidUrlException;
import com.lhmai.funnytube.repository.MovieRepository;
import com.lhmai.funnytube.service.dto.MovieDto;
import com.lhmai.funnytube.service.dto.YouTubeVideoDto;
import com.lhmai.funnytube.service.mapper.MovieMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private YouTubeService youtubeService;

    public MovieDto addYoutubeMovieFromURL(String youtubeUrl) {

        YouTubeVideoDto youTubeVideo = youtubeService.getVideoInformation(youtubeUrl);

        if (youTubeVideo == null) {
            throw new InvalidUrlException();
        }

        MovieEntity movieEntity = movieMapper.toMovieEntity(youTubeVideo);

        movieRepository.save(movieEntity);

        return movieMapper.toMovieDto(movieEntity);
    }

    /**
     * The information of youtube videos may out of date
     * since this API getting information stored in database
     */

    public List<MovieDto> listAll() {
        List<MovieEntity> allMovies = movieRepository.findAll();
        return movieMapper.toMovieDtoList(allMovies);
    }
}
