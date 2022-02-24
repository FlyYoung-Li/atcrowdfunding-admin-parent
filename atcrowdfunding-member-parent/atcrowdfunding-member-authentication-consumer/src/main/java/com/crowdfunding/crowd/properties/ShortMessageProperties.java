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
 * @create: 2021-10-24 23:19
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "short.message")
@Component
public class ShortMessageProperties {

    private String host;
    private String path;
    private String method;
    private String appcode;
    private String templateId;

}
