package com.employee.Controller;

import com.employee.Payload.FileResponse;
import com.employee.Payload.StoreFileInDB;
import com.employee.Utils.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${project.image}")
    private String path;

    @PostMapping("/uploadInLocal")
    public ResponseEntity<FileResponse> fileUpload(@RequestParam("image")MultipartFile image){
        String fileName = null;
        try {
            fileName = this.fileService.uploadImage(path, image);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new FileResponse(null,"Image is Not uploaded due to some error On Server "), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new FileResponse(fileName,"file Successfully Uploaded"), HttpStatus.OK);
    }

    @PostMapping("/uploadInDB")
    public ResponseEntity<?> fileUploadInDB(@RequestParam("file")MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileService.addFile(file), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        StoreFileInDB loadFile = fileService.downloadFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loadFile.getFileType() ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFilename() + "\"")
                .body(new ByteArrayResource(loadFile.getFile()));
    }

    @PostMapping("/uploadMultipleFiles")
    public ResponseEntity<?> uploadMultipleFiles(@RequestParam("images") MultipartFile[] files ){

        this.fileService.uploadMultipleFiles(path,files);

        this.logger.info("{} numbers of files uploaded",files.length);
        Arrays.stream(files).forEach(multipartFile -> {
            logger.info("file name : {} ",multipartFile.getOriginalFilename());
            logger.info("file Type : {} ",multipartFile.getContentType());
            logger.info("file Size : {} ", multipartFile.getSize());
            System.out.println("*****");

        });

        return ResponseEntity.ok("Multiple Files has been uploaded");
    }

}
