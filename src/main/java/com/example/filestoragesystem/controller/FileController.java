package com.example.filestoragesystem.controller;

import com.example.filestoragesystem.entity.File;
import com.example.filestoragesystem.entity.User;
import com.example.filestoragesystem.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/upload")
    public String uploadForm(Model model, @AuthenticationPrincipal User user) {
        if (user == null) {
            // User is not authenticated, redirect to login
            return "redirect:/users/login";
        }
        model.addAttribute("file", new File());
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadSubmit(@RequestParam("file") MultipartFile file,
                               @AuthenticationPrincipal User user) throws IOException {
        System.out.println("File received: " + file.getOriginalFilename());
        fileService.storeFile(file, user);
        return "redirect:/files/list";
    }

    @GetMapping("/list")
    public String fileList(Model model, @AuthenticationPrincipal User user) {
        List<File> files = fileService.getFilesByUser(user);
        model.addAttribute("files", files);
        return "file-list";
    }

    @GetMapping("/download/{fileId}")
    public String downloadFile(@PathVariable Long fileId, @AuthenticationPrincipal User user) {
        try {
            fileService.downloadFile(fileId, user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/files/list";
    }
}