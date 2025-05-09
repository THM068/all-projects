package com.ht.managemybills.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "social.media")
public record SocialMediaProperties(List<SocialMediaAccount> accounts) {

}
