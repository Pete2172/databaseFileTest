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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    @ResponseBody
    public ResponseEntity<Resource> saveFile(Model model, @RequestParam(name = "file") MultipartFile file){
        DBFile receivedFile = dbFileService.saveFileToDatabase(file);
        DBFile databaseFile = dbFileService.getFileFromDatabase(receivedFile.getId());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + receivedFile.getFileName() + "\"").body(new ByteArrayResource(databaseFile.getFileData()));
    }
}
