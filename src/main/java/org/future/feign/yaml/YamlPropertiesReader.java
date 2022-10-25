package org.future.feign.yaml;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zpf
 * @description yaml manager
 * @createTime 2022-10-19 10:54
 */
public class YamlPropertiesReader {

    private YamlPropertiesFactoryBean yamlPropertiesFactoryBean;

    private String[] profiles;

    public YamlPropertiesReader() {
    }

    public YamlPropertiesReader(YamlPropertiesFactoryBean yamlPropertiesFactoryBean){
        this.yamlPropertiesFactoryBean = yamlPropertiesFactoryBean;
    }


    public static YamlPropertiesReader initFactoryBean() {
        return new YamlPropertiesReader(new YamlPropertiesFactoryBean());
    }


    public YamlPropertiesReader profile(String... profiles) {
        this.profiles = profiles;
        return this;
    }


    public YamlPropertiesReader initYamlFactory() {
        Assert.isTrue(ArrayUtils.isNotEmpty(profiles),"profiles can not be empty!");
        ClassPathResource[] classPathResources = null;
        if (ArrayUtils.isEmpty(profiles) || Objects.equals(profiles[0], "-0")) {
            classPathResources = new ClassPathResource[]{new ClassPathResource("application.yml")};
        } else {
            classPathResources = new ClassPathResource[profiles.length];
            for (int i = 0; i < profiles.length; i++) {
                ClassPathResource classPathResource =
                        new ClassPathResource("application-" + profiles[i] + ".yml");
                if (classPathResource.exists()) {
                    classPathResources[i] = classPathResource;
                }
            }
            if (CollectionUtils.isEmpty(Arrays.stream(classPathResources)
                    .filter(Objects::nonNull).collect(Collectors.toList()))) {
                classPathResources[0] = new ClassPathResource("application.yml");
            }
        }
        yamlPropertiesFactoryBean.setResources(classPathResources);
        return this;
    }

    public YamlPropertiesFactoryBean getYamlPropertiesFactoryBean() {
        return yamlPropertiesFactoryBean;
    }
}
