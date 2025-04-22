package edu.gupt.service.impl;

import edu.gupt.service.FileService;
import edu.gupt.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private AliOssUtil aliOssUtil;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        // 在这里可以添加文件验证逻辑，如文件大小、类型检查等  
        // 如果验证失败，可以抛出异常或返回一个错误消息  
  
        // 调用AliOssUtil的上传方法  
        return aliOssUtil.upload(file);  
    }
}