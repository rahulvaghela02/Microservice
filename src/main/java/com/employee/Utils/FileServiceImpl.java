package com.employee.Utils;

import com.employee.Payload.StoreFileInDB;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private GridFsTemplate template;

    @Autowired
    private GridFsOperations operations;

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //FileName:
        String name = file.getOriginalFilename();

        //Generating random file name
        String randomId = UUID.randomUUID().toString();
        String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));

        //Full Path
        String filePath = path + File.separator+fileName1;



        //Create Folder if not present
        File file1 = new File(path);
        if(!file1.exists()){
            file1.mkdir();
        }
        //file Copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return name;
    }

    @Override
    public String addFile(MultipartFile upload) throws IOException {
        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", upload.getSize());

        Object fileID = template.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(), metadata);

        return fileID.toString();
    }

    @Override
    public StoreFileInDB downloadFile(String id) throws IOException {
        GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(id)) );

        StoreFileInDB loadFile = new StoreFileInDB();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            loadFile.setFilename( gridFSFile.getFilename() );

            loadFile.setFileType( gridFSFile.getMetadata().get("_contentType").toString() );

            loadFile.setFileSize( gridFSFile.getMetadata().get("fileSize").toString() );

          loadFile.setFile(IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()));


        }

        return loadFile;
    }

    @Override
    public String uploadMultipleFiles(String path, MultipartFile[] files) {
        return null;
    }
}
