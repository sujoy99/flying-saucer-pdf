package com.starter.lab.service.impl;

import com.lowagie.text.DocumentException;
import com.starter.lab.service.PdfGenerateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class PdfGenerateServiceImpl implements PdfGenerateService {

    private final Logger logger = LoggerFactory.getLogger(PdfGenerateServiceImpl.class);

    @Autowired
    private TemplateEngine pdfTemplateEngine;

    @Autowired
    private ITextRenderer renderer;

    @Value("${pdf.directory}")
    private String pdfDirectory;

    @Override
    public void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) {
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = pdfTemplateEngine.process(templateName, context);
        try {

            // Create the directory if it doesn't exist
            File directory = new File(pdfDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Use ResourceLoader to get the correct path within the Spring Boot project
            File pdfFile = new File(directory, pdfFileName + System.currentTimeMillis() + ".pdf");
//            File pdfFile = new File(directory, pdfFileName);

            // Use try-with-resources to ensure proper resource closure
            try (FileOutputStream fileOutputStream = new FileOutputStream(pdfFile)) {
//                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(htmlContent);
                renderer.layout();
                renderer.createPDF(fileOutputStream, false);
                renderer.finishPDF();

                System.out.println("PDF file generated and stored at: " + pdfFile.getAbsolutePath());
                logger.info("PDF file generated and stored at: " + pdfFile.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
