package edu.gupt.controller;

import edu.gupt.result.Result;
import edu.gupt.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            String url = fileService.uploadFile(file);
            return Result.success(url);
        } catch (IOException e) {
            return Result.fail("文件上传失败:"+e.getCause());
        }
    }
}
