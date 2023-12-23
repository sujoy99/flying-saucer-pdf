package com.starter.lab.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static String os = System.getProperty("os.name").toLowerCase();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("static/images/**")
//                .addResourceLocations("classpath:static/images/")
//                .setCacheControl(CacheControl.noCache());
        exposeDirectory("images", registry);
    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry){
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        /*
         * if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
         */
        registry.addResourceHandler("/" + dirName + "/**")
//                .addResourceLocations("/"+ dirName + "/");
//                .addResourceLocations("file://" + uploadPath +"/");     // for linux
//                .addResourceLocations("file:///" + uploadPath + "\");   // for windows
                .addResourceLocations( osPlatform() + uploadPath + File.separator);

    }

    private String osPlatform(){
        return os.contains("win") ? "file:///" : "file://";
    }
}
