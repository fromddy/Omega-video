package come.omega.omagedecode;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@Configuration
@EnableConfigurationProperties(MinIoItem.class)
public class MinIo {

    @Autowired
    public MinIoItem minIoItem;

    @Bean
    public MinioClient client() {
        return MinioClient.builder().endpoint(minIoItem.endpoint).credentials(minIoItem.accessKey, minIoItem.secretKey).build();
    }

    @Autowired
    private MinioClient client;

    public void putObject(File file, String bucketName , String objectName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (file == null || file.length()==0) {
            throw new RuntimeException();
        }
        client.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(new FileInputStream(file), file.length(), -1)
                        .build());

    }

    public InputStream getObject(String bucketName, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return client.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

}