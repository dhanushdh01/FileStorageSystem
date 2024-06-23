package com.example.filestoragesystem.Service;

import com.example.filestoragesystem.entity.File;
import com.example.filestoragesystem.entity.User;
import com.example.filestoragesystem.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    public void storeFile(MultipartFile multipartFile, User user) throws IOException {
        File file = new File();
        file.setFilename(multipartFile.getOriginalFilename());
        file.setFileType(multipartFile.getContentType());
        file.setData(multipartFile.getBytes());
        fileRepository.save(file);
    }
    @Override
    public List<File> getFilesByUser(User user) {
        return fileRepository.findAllByOwner(user);
    }

    @Override
    public void downloadFile(Long fileId, User user) {
        // Implement download logic (e.g., retrieve file from storage and provide download functionality)
    }
}
