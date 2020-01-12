package com.lhmai.funnytube.intergration.cucumber;

import com.lhmai.funnytube.FunnytubeApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;

@SpringBootTest(classes = FunnytubeApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@Transactional
public class SpringIntegrationTest {

}
