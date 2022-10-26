package com.inspiration.future.feign.yaml;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Properties;

/**
 * @author zpf
 * @description yaml get utils
 * @createTime 2022-10-15 16:52
 */
public class YamlPropertiesProvider {

    private YamlPropertiesReader yamlPropertiesReader;


    public static YamlPropertiesProvider instance() {
        return new YamlPropertiesProvider();
    }


    public YamlPropertiesProvider reader(YamlPropertiesReader yamlPropertiesReader) {
        this.yamlPropertiesReader = yamlPropertiesReader;
        return this;
    }


    public YamlPropertiesProvider build() {
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = yamlPropertiesReader.getYamlPropertiesFactoryBean();
        Assert.notNull(yamlPropertiesFactoryBean,"YamlPropertiesProvider can not be null!");
        Assert.notNull(yamlPropertiesFactoryBean.getObject(), "YamlPropertiesProvider not find Properties!");
        Assert.isTrue(!Objects.equals(yamlPropertiesFactoryBean.getObject().size(),0),
                "YamlPropertiesProvider Properties size not is zero!");
        return this;
    }


    public Properties getProperties() {
        return yamlPropertiesReader.getYamlPropertiesFactoryBean().getObject();
    }


    public String getPropertiesStr(String key) {
        Properties properties = getProperties();
        if (Objects.isNull(properties)) {
            return "";
        }
        return properties.getProperty(key);
    }


    public Object getPropertiesObj(Object key) {
        Properties properties = getProperties();
        if (Objects.isNull(properties)) {
            return "";
        }
        return properties.get(key);
    }
}
