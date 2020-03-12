package com.example.demo2.service;

import com.example.demo2.database.DBFile;
import com.example.demo2.repository.DBFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class DBFileService {
    @Autowired
    DBFileRepository dbFileRepository;

    public DBFile saveFileToDatabase(MultipartFile file){
        try{
            DBFile newDbFile = new DBFile(file.getOriginalFilename(), file.getContentType(), file.getBytes());
            return dbFileRepository.save(newDbFile);
        }
        catch (IOException e){
            throw new RuntimeException();
        }
    }

    public DBFile getFileFromDatabase(UUID id){
        return dbFileRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    public List<DBFile> listAllFiles(){
        return dbFileRepository.findAll();
    }

    public DBFile findByUUID(UUID uuid){
        return dbFileRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found!", new EntityNotFoundException()));
    }

}
