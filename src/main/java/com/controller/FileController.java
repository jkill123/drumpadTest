package com.controller;

import com.domain.FileResponse;
//import com.storage.StorageProperties;
import com.storage.StorageService;
import com.utils.CurrentDateTime;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class FileController {

    private String currentDir ="";

    private StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listAllFiles(Model model) {

        model.addAttribute("files", storageService.loadAll(Paths.get(currentDir)).map(
                path -> ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/download/")
                        .path(path.getFileName().toString())
                        .toUriString())
                .collect(Collectors.toList()));

        return "listFiles";
    }

    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource resource = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @PostMapping("/upload-file")
    @ResponseBody
    public FileResponse uploadFile(@RequestParam("filename") String filename,
                                   @RequestParam("logo") MultipartFile logo,
                                   @RequestParam("file") MultipartFile file) throws IOException {

        System.out.println(filename);
        String uploadPath = "/home/jkill/dev/drumpadTest/src/main/java/com/temp2/";//todo

        String time = CurrentDateTime.currentDate();
        currentDir = uploadPath + "/" + time;

        if (file != null && !file.getOriginalFilename().isEmpty()){
        }
            File uploadDir = new File(currentDir);

            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            String resultLogoName = uuidFile + "." + logo.getOriginalFilename();
            file.transferTo(new File(currentDir + "/" + resultFilename));
            logo.transferTo(new File(currentDir + "/" + resultLogoName));

//        String name = storageService.store(file, Paths.get(uploadPath));

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(resultFilename)
                .toUriString();
        return new FileResponse(resultFilename, uri, file.getContentType(), file.getSize());
    }

//    @PostMapping("/upload-multiple-files")
//    @ResponseBody
//    public List<FileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) throws IOException {
//        List<FileResponse> collect = new ArrayList<>();
//        for (MultipartFile file : files) {
//            FileResponse fileResponse = uploadFile("lol", file, file);
//            collect.add(fileResponse);
//        }
//        return collect;
//    }
}