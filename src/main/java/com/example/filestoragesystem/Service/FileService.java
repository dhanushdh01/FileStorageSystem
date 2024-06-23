package com.example.filestoragesystem.Service;

import com.example.filestoragesystem.entity.File;
import com.example.filestoragesystem.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    void storeFile(MultipartFile file, User user) throws IOException;

    List<File> getFilesByUser(User user);

    void downloadFile(Long fileId, User user) throws IOException;
}
