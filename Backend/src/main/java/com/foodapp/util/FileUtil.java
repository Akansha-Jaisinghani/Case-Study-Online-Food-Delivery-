package com.foodapp.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FileUtil {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("pdf", "doc", "docx");

    public static boolean isValidFileType(MultipartFile file) {
        // Check if the file has a valid extension
        return ALLOWED_EXTENSIONS.stream()
                .anyMatch(extension -> file.getOriginalFilename().toLowerCase().endsWith("." + extension));
    }

    public static String saveFile(MultipartFile file, String uploadDirectory) throws IOException {
        if (!isValidFileType(file)) {
            throw new IllegalArgumentException("Invalid file type. Allowed types: PDF, DOC, DOCX.");
        }

        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = uploadDirectory + File.separator + uniqueFileName;

        File dest = new File(filePath);
        file.transferTo(dest);

        return filePath;
    }
}


