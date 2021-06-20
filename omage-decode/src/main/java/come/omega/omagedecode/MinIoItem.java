package come.omega.omagedecode;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "min.io")
public class MinIoItem{
    public String endpoint;
    public String accessKey;
    public String secretKey;

    public String bucket360;
    public String bucket720;
    public String bucket1080;
    public String getEndpoint() {
        return endpoint;
    }
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    public String getAccessKey() {
        return accessKey;
    }
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
    public String getSecretKey() {
        return secretKey;
    }
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    public String getBucket360() {
        return bucket360;
    }
    public void setBucket360(String bucket360) {
        this.bucket360 = bucket360;
    }
    public String getBucket720() {
        return bucket720;
    }
    public void setBucket720(String bucket720) {
        this.bucket720 = bucket720;
    }
    public String getBucket1080() {
        return bucket1080;
    }
    public void setBucket1080(String bucket1080) {
        this.bucket1080 = bucket1080;
    }
}