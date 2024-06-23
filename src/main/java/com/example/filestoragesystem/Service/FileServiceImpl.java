package com.example.filestoragesystem.Service;

import com.example.filestoragesystem.entity.File;
import com.example.filestoragesystem.entity.User;
import com.example.filestoragesystem.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public void storeFile(MultipartFile multipartFile, User user) throws IOException {
        File file = new File();
        file.setFilename(multipartFile.getOriginalFilename());
        file.setFileType(multipartFile.getContentType());
        file.setData(multipartFile.getBytes());
        file.setOwner(user);
        fileRepository.save(file);
    }

    @Override
    public List<File> getFilesByUser(User user) {
        return fileRepository.findAllByOwner(user);
    }

    @Override
    @Transactional
    public void downloadFile(Long fileId, User user) throws IOException {
        Optional<File> optionalFile = fileRepository.findById(fileId);
        if (optionalFile.isPresent()) {
            File file = optionalFile.get();
            // Check if the user is the owner of the file (security check)
            if (file.getOwner().getId().equals(user.getId())) {
                // Stream the file data to the response or perform download logic
                // Example: Writing file data to System.out (replace with actual file streaming logic)
                InputStream inputStream = file.getDataAsStream(); // Implement this method in your File entity or repository
                int bytesRead;
                byte[] buffer = new byte[4096]; // Buffer size
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    // Example: Writing to System.out (replace with HttpServletResponse for actual download)
                    System.out.write(buffer, 0, bytesRead);
                }
            } else {
                throw new SecurityException("Unauthorized access to file download");
            }
        } else {
            throw new IllegalArgumentException("File not found with ID: " + fileId);
        }
    }
}
