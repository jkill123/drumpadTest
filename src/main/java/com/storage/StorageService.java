package com.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();

//    String store(MultipartFile file);

    String store(MultipartFile file, Path fileDir);

//    Stream<Path> loadAll();

    Stream<Path> loadAll(Path fileDir);

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}
