package com.example.demo2.controller;


import com.example.demo2.database.DBFile;
import com.example.demo2.service.DBFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Controller
public class AppController {
    @Autowired
    private DBFileService dbFileService;

    @GetMapping("/")
    public String get(Model model){
        model.addAttribute("listDbFiles", dbFileService.listAllFiles());
        return "index";
    }

    @PostMapping("/")
    public String saveFile(Model model, @RequestParam(name = "file") MultipartFile file){
        DBFile receivedFile = dbFileService.saveFileToDatabase(file);

        return this.get(model);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Resource> getFile(@PathVariable UUID uid){
        DBFile dbFile = dbFileService.getFileFromDatabase(uid);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + dbFile.getFileName() + "\"").body(new ByteArrayResource(dbFile.getFileData()));
    }

}
