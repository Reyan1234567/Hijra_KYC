package com.example.hijra_kyc.util;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

@Slf4j
@Service
public class FileUpload {
    public void createFile(String base64, String parent, String child, String type) throws IOException {
        try{
            if (!type.equals("png") && !type.equals("jpg") && !type.equals("jpeg") && !type.equals("webm")) {
                throw new RuntimeException("file type not supported");
            }

            //creating the path's parent path
            File uploadDir = new File(parent);
            //check existence and create if path doesn't exist
            if (!uploadDir.exists()) {
                var success = uploadDir.mkdirs();
            }

            //the full path which includes the file to be created
            File fileToBeUploaded = new File(child);

            String encodedBit = base64.split(",")[1];
            byte[] fileByte = Base64.getDecoder().decode(encodedBit);

            InputStream is=new ByteArrayInputStream(fileByte);
            BufferedImage image = ImageIO.read(is);

            int newWidth = 800;
            int newHeight=(newWidth*image.getHeight())/image.getWidth();

            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d=resizedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.drawImage(image, 0, 0, newWidth, newHeight, null);
            g2d.dispose();


            File output = new File(child);
            ImageIO.write(resizedImage, type, output);

//          creates the file in a lower storage and detail
//            Thumbnails.of(new ByteArrayInputStream(fileByte))
//                    .size(500, 500)
//                    .outputQuality(0.95)
//                    .toFile(fileToBeUploaded);
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
