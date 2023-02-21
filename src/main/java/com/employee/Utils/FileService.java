package com.employee.Utils;

import com.employee.Payload.StoreFileInDB;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface FileService {

    String uploadImage(String path, MultipartFile file) throws IOException;

    public String addFile(MultipartFile upload) throws IOException;

    public StoreFileInDB downloadFile(String id) throws IOException;
}
