package ru.shafikova.UnpackingService.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.shafikova.UnpackingService.Models.OutputFile;
import ru.shafikova.UnpackingService.Services.UnpackingService;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/unpack")
public class Controller {
    private final UnpackingService unpackingService;

    @Autowired
    public Controller(UnpackingService unpackingService) {
        this.unpackingService = unpackingService;
    }

    @PostMapping()
    public ResponseEntity<Object> put(@RequestParam("file") MultipartFile file) throws IOException {
        OutputFile outputData = unpackingService.unpack(file);
        File unpackedFile = outputData.getFile();
        String fileName = outputData.getFileName();
        String contentType = outputData.getContentType();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(contentType));
        headers.setContentDispositionFormData(fileName, fileName);

        return new ResponseEntity<>(new FileSystemResource(unpackedFile), headers, HttpStatus.OK);
    }
}



