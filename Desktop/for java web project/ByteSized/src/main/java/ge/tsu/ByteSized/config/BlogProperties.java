package ge.tsu.ByteSized.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

@ConfigurationProperties(prefix = "blog")
public class BlogProperties {

    private String platformName;
    private DataSize maxImageUploadSize;

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public DataSize getMaxImageUploadSize() {
        return maxImageUploadSize;
    }

    public void setMaxImageUploadSize(DataSize maxImageUploadSize) {
        this.maxImageUploadSize = maxImageUploadSize;
    }
}
