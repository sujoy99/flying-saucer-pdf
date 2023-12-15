package com.starter.lab.config;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.starter.lab.service.impl.PdfGenerateServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;

@Configuration
public class PdfThymeleafConfig {

    private Logger logger = LoggerFactory.getLogger(PdfThymeleafConfig.class);

    @Value("${font.directory}")
    private String fontDirectory;

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public SpringTemplateEngine pdfTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(pdfTemplateResolver());
        return templateEngine;
    }

    @Bean
    public ClassLoaderTemplateResolver pdfTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/pdf-templates/");
        templateResolver.setSuffix(".html"); // Assuming your PDF templates have HTML extension
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(2); // Set the order appropriately (higher than the HTML resolver)
        return templateResolver;
    }

    @Bean
    public ITextRenderer iTextRenderer() throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = (ITextFontResolver) renderer.getSharedContext().getFontResolver();

        // Add the path to your Bangla fonts
        String fontPath = "classpath:fonts/";

        Resource fontResource = resourceLoader.getResource(fontPath + "kalpurush.ttf");
        Resource fontResource1 = resourceLoader.getResource(fontPath + "Nikosh.ttf");
        Resource fontResource2 = resourceLoader.getResource(fontPath + "SUTOM.TTF");
        Resource fontResource3 = resourceLoader.getResource(fontPath + "DestinyMJ.ttf");
//        Resource fontResource3 = resourceLoader.getResource(fontPath + "kalpurush_ANSI.ttf");
        logger.info("Font URL: " + fontResource.getURL());
        logger.info("Font URL: " + fontResource1.getURL());
        logger.info("Font URL: " + fontResource2.getURL());
        logger.info("Font URL: " + fontResource3.getURL());

        // Register Bangla fonts
//        fontResolver.addFont(fontResource.getURL().toString(), true);

//        renderer.getFontResolver().addFont(fontResource.getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        renderer.getFontResolver().addFont(fontResource.getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        renderer.getFontResolver().addFont(fontResource1.getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        renderer.getFontResolver().addFont(fontResource2.getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        renderer.getFontResolver().addFont(fontResource3.getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return renderer;
    }
}
