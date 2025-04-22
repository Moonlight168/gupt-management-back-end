package edu.gupt.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.SignVersion;
import edu.gupt.config.properties.AliyunProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.oss.OSSClientBuilder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Component
public class AliOssUtil {

    private final String endpoint;
    private final String region;
    private final String bucketName;
    private final CredentialsProvider credentialsProvider;

    @Autowired
    public AliOssUtil(AliyunProperties aliyunProperties) {
        this.endpoint = aliyunProperties.getEndpoint();
        this.region = aliyunProperties.getRegion();
        this.bucketName = aliyunProperties.getBucketName();
        String accessKeyId = aliyunProperties.getAccessKeyId();
        String accessKeySecret = aliyunProperties.getAccessKeySecret();
        this.credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
    }

    /**
     * 实现上传图片到OSS
     */
    public String upload(MultipartFile file) throws IOException {
        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();
        try {
            // 第一个参数是Bucket名称，第二个参数是ObjectName（包含路径），第三个参数是要上传的文件
            UUID uuid = UUID.randomUUID();
            String objectName = "avatar/" + uuid + file.getOriginalFilename();
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(file.getBytes()));
            //获取文件访问路径 过期时间设置为30天
            String url = ossClient.generatePresignedUrl(bucketName, objectName, new Date(System.currentTimeMillis() + 30L * 24 * 3600 * 1000)).toString();
            System.out.println("url:" + url);
            //文件访问路径
            return url;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return "";
    }


//        try {
//            InputStream inputStream = new FileInputStream(filePath);
//            // 创建PutObjectRequest对象。
//            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
//            // 设置该属性可以返回response。如果不设置，则返回的response为空。
//            putObjectRequest.setProcess("true");
//            // 创建PutObject请求。
//            PutObjectResult result = ossClient.putObject(putObjectRequest);
//            // 如果上传成功，则返回200。
//            System.out.println(result.getResponse().getStatusCode());
//        } catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
//        } catch (ClientException ce) {
//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
}

