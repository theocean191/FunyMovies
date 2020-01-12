package com.lhmai.funnytube.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.lhmai.funnytube.exception.InvalidMovieIdException;
import com.lhmai.funnytube.exception.InvalidUrlException;
import com.lhmai.funnytube.exception.YouTubeApiCallException;
import com.lhmai.funnytube.service.dto.YouTubeVideoDto;
import com.lhmai.funnytube.service.mapper.YouTubeVideoMapper;
import com.lhmai.funnytube.util.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class YouTubeService {

    private String apiKey = "AIzaSyCzttNX9A0Nz6hNi-h0xGDJRi5Z9XCPpv8";

    @Autowired
    private YouTubeVideoMapper youtubeVideoMapper;

    public YouTubeVideoDto getVideoInformation(String youtubeUrl) {

        String movieId = toVideoId(youtubeUrl);

        if (StringUtils.isEmpty(movieId)) {
            throw new InvalidUrlException("No movie id found in url");
        }

        Video video = null;
        try {
            video = getVideoByYouTubeId(movieId);
        } catch (InvalidMovieIdException e) {
            throw new InvalidUrlException("No movie found for the provided url", e);
        }

        return youtubeVideoMapper.toYouTubeVideoDto(video);

    }

    private Video getVideoByYouTubeId(String videoId) {
        try {
            return tryGetVideoByYouTubeId(videoId);
        } catch (IOException e) {
            throw new YouTubeApiCallException("Failed while calling YouTube api to get video inforamtion for id " + videoId, e);
        }
    }

    private Video tryGetVideoByYouTubeId(String videoId) throws IOException {
        YouTube youtube = createYouTubeClient();
        YouTube.Videos.List search = youtube.videos().list("id,snippet");
        search.setKey(apiKey);
        search.setId(videoId);
        VideoListResponse response = search.execute();
        if (response.getItems().size() == 1) {
            return response.getItems().get(0);
        } else {
            throw new InvalidMovieIdException("No movie found for the given url");
        }
    }

    private String toVideoId(String youtubeUrl) {
        try {
            Map<String, String> queryParams = UrlUtils.getQueryParams(youtubeUrl);
            return queryParams.get("v");
        } catch (Exception exception) {
            throw new InvalidUrlException(exception);
        }
    }

    private YouTube createYouTubeClient() {
        return new YouTube.Builder(YouTubeAuth.HTTP_TRANSPORT, YouTubeAuth.JSON_FACTORY, httpRequest -> {
        })
                .setApplicationName("funny-movie").build();
    }

    public String getEmbeddedUrl(String tubeMovieId) {

        if (StringUtils.isEmpty(tubeMovieId)) {
            throw new IllegalArgumentException("Movie id must not be empty to get embedded url");
        }

        return "https://www.youtube.com/embed/" + tubeMovieId;
    }
}
