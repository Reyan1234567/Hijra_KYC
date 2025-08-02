package com.example.hijra_kyc.util;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.io.File;
import java.util.Base64;

@Slf4j
@Service
public class FileUpload {
    public void createFile(String base64, String parent, String child, String type) throws IOException {
        try{
            if (!type.equals("png") && !type.equals("jpg") && !type.equals("jpeg") && !type.equals("webm")) {
                throw new RuntimeException("file type not supported");
            }

            String encodedBit = base64.split(",")[1];
            byte[] fileByte = Base64.getDecoder().decode(encodedBit);

            System.out.println(type);
            //check, if really an image

            //creating the path's parent path
            File uploadDir = new File(parent);
            //check existence and create if path doesn't exist
            if (!uploadDir.exists()) {
                var success = uploadDir.mkdirs();
            }

            //the full path which includes the file to be created
            File fileToBeUploaded = new File(child);


//          creates the file in a lower storage and detail
            Thumbnails.of(new ByteArrayInputStream(fileByte))
                    .size(1000, 1000)
                    .outputQuality(1)
                    .toFile(fileToBeUploaded);
//          could use Files.write(Path, bytes), were bytes is the decoded image,
//          and the the Path is of type path representing the path(Path.get("..."))}
//          or Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING); if file is of type Multi-part
    }
        catch(IOException e){
            log.error("File operation failed ",e);
            throw new IOException(e);
        }
        catch(Exception e){
            log.error("Image saving failed", e);
            throw new RuntimeException("Failed to save profile image");
        }
}}
