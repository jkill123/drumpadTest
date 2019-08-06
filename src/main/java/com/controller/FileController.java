package com.controller;

import com.domain.FileResponse;
import com.domain.Message;
import com.google.gson.Gson;
import com.model.IdListREsponse;
import com.model.ObjectListResponse;
import com.repos.MessageRepo;
import com.service.SendService;
import com.storage.MediaTypeUtils;
import com.storage.StorageService;
import com.utils.IntArrayFromString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class FileController {

    // Папка в которой сохраняются файлы загружаемые админом через админ панель
    private static final String currentDir = "/home/jkill/dev/drumpadTest/src/main/java/com/temp2";//todo

    private final ServletContext servletContext;
    private final StorageService storageService;
    private final MessageRepo messageRepo;
    private final SendService sendService;

    public FileController(StorageService storageService, ServletContext servletContext, MessageRepo messageRepo, SendService sendService) {
        this.storageService = storageService;
        this.servletContext = servletContext;
        this.messageRepo = messageRepo;
        this.sendService = sendService;
    }

    /***
     * Контроллер для загрузки музфкального пака на сервер админом
     * имя пака
     * @param filename
     * логотоп пака
     * @param logo
     * сам файл архив пак
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload-file")
    @ResponseBody
    public FileResponse uploadFile(@RequestParam("filename") String filename,
                                   @RequestParam("logo") MultipartFile logo,
                                   @RequestParam("file") MultipartFile file) throws IOException {

        System.out.println(filename);
        // Проверка условия перед сохранением и созданием папки
        if (file != null && !file.getOriginalFilename().isEmpty()){

            File uploadDir = new File(currentDir);

            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
        }
        // генерация случайного уникального имени
        String uuidFile = UUID.randomUUID().toString();

        // имя файла для сохранения в бд
        String resultFilename = uuidFile + "." + file.getOriginalFilename();
        String resultLogoName = uuidFile + "." + logo.getOriginalFilename();
        // сохранение файла в файловую систему
        file.transferTo(new File(currentDir + "/" + resultFilename));
        logo.transferTo(new File(currentDir + "/" + resultLogoName));

        // Сохранение имен файлов в базу данных
        Message message = new Message();
        message.setLogo(resultLogoName);
        message.setFileOriginalName(filename);
        message.setFileDataBaseName(resultFilename);
        messageRepo.save(message);

        return new FileResponse(resultFilename, file.getContentType(), file.getSize());
    }


    /***
     * Контроллер выгружает файлы для клиента используя параметр адресной строки в виде имени файла
     * имя которого сохраняется в базу данных при добавлении админом пака
     * @param fileName
     * @return
     * @throws IOException
     */
    @RequestMapping("/download1/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile1(
            @PathVariable String fileName) throws IOException {
        // определение типа файла
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);

        System.out.println("fileName: " + fileName);
        System.out.println("mediaType: " + mediaType);

        // получение файла из файловой системы
        File file = new File(currentDir + "/" + fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getIdList", method = RequestMethod.POST)
    public @ResponseBody String getIdList(@RequestParam ("id") String id){

        if(id.equals("1234")){

            String resultGs = sendService.getIds();

            return resultGs;
        }else
            return String.valueOf(new Exception("go hell kid"));
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getObjects", method = RequestMethod.POST)

    public @ResponseBody String getObjects(@RequestParam ("id") String id,
                                           @RequestParam ("list") String list){
        if(id.equals("1234")){

        int[] result = IntArrayFromString.arrayFromString(list);

        String resultGs = sendService.getObjects(result);

        return resultGs;}
        else return String.valueOf(new Exception("go hell kid"));
    }
}