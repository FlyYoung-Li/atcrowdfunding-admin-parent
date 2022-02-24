package com.crowdfunding.crowd.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-03 08:43
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@ConfigurationProperties(prefix = "aliyun.oss")
@Component
public class OSSProperties {
    private String endpoint;
    private String bucketName;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketDomain;
}
