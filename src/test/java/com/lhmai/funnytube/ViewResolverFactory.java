package com.lhmai.funnytube;

import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ViewResolverFactory {

    public static ViewResolver thymleafViewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");

        templateEngine.setTemplateResolver(templateResolver);
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }
}
