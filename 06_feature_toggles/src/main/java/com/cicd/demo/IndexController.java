package com.cicd.demo;

import com.cicd.demo.config.FeatureConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private final FeatureConfig featureConfig;

    public IndexController(FeatureConfig featureConfig) {
        this.featureConfig = featureConfig;
    }

    @GetMapping("/")
    public String hello() {
        return String.format("Hello! Profile is: %s, Features are: %s", featureConfig.getProfile(), featureConfig);
    }
}
