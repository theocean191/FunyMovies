package com.lhmai.funnytube.service;

import com.lhmai.funnytube.FunnytubeApplication;
import com.lhmai.funnytube.service.dto.YouTubeVideoDto;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FunnytubeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class YoutubeServiceTest {

    private YouTubeService youtubeService = new YouTubeService();

    @Test
    public void getVideoInformation_WithValidUrl_ReturnYouTubeInforamtion() throws IOException {
        String url = "https://www.youtube.com/watch?v=lY5_6eIqTsE";
        YouTubeVideoDto videoInformation = youtubeService.getVideoInformation(url);
        Assert.assertNotNull(videoInformation);
        Assert.assertTrue(StringUtils.isNotEmpty(videoInformation.getTitle()));
    }

    @Test
    public void getVideoInformation_WithValidUrlId_ReturnNull() throws IOException {
        String url = "https://www.youtube.com/watch?v=lY5_6eIqTsExxxxxx_my_invalid_Id";
        YouTubeVideoDto videoInformation = youtubeService.getVideoInformation(url);
        Assert.assertNull(videoInformation);
    }

    @Test
    public void getEmbeddedHtml_WithIdString_ReturnValidEmbeddedUrl() {
        String givenYoutubeId = "JOAWOLaxcCA";
        String expected = "https://www.youtube.com/embed/" + givenYoutubeId;

        String result = youtubeService.getEmbeddedUrl(givenYoutubeId);

        Assert.assertEquals(expected, result);
    }

}
