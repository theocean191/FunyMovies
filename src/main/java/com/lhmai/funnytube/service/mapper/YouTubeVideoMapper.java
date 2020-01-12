package com.lhmai.funnytube.service.mapper;

import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.lhmai.funnytube.service.dto.YouTubeVideoDto;
import org.springframework.stereotype.Component;

@Component
public class YouTubeVideoMapper {

    public YouTubeVideoDto toYouTubeVideoDto(Video item) {

        VideoSnippet snippet = item.getSnippet();
        snippet.getDescription();
        String id = item.getId();

        return YouTubeVideoDto.builder()
                .id(item.getId())
                .description(snippet.getDescription())
                .title(snippet.getTitle())
                .build();
    }
}
