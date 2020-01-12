package com.lhmai.funnytube.service.mapper;

import com.lhmai.funnytube.domain.MovieEntity;
import com.lhmai.funnytube.service.YouTubeService;
import com.lhmai.funnytube.service.dto.MovieDto;
import com.lhmai.funnytube.service.dto.YouTubeVideoDto;
import com.lhmai.funnytube.util.SpringSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieMapper {
    @Autowired
    private YouTubeService youTubeService;

    public MovieDto toMovieDto(MovieEntity movie) {

        String embeddedUrl = youTubeService.getEmbeddedUrl(movie.getTubeMovieId());

        return MovieDto.builder()
                .id(movie.getTubeMovieId())
                .embeddedUrl(embeddedUrl)
                .description(movie.getDescription())
                .title(movie.getTitle())
                .sharedBy(movie.getSharedBy())
                .build();
    }

    public List<MovieDto> toMovieDtoList(List<MovieEntity> videoDtos) {
        return videoDtos.stream().map(this::toMovieDto).collect(Collectors.toList());
    }

    public MovieEntity toMovieEntity(YouTubeVideoDto movie) {
        String embeddedUrl = youTubeService.getEmbeddedUrl(movie.getId());

        return MovieEntity.builder()
                .tubeMovieId(movie.getId())
                .embeddedUrl(embeddedUrl)
                .description(movie.getDescription())
                .title(movie.getTitle())
                .sharedBy(SpringSecurityUtils.getCurrentUserLogin())
                .build();
    }
}
