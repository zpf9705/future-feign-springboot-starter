package org.future.feign.yaml;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Properties;

/**
 * @author zpf
 * @description yaml 取值工具
 * @createTime 2022-10-15 16:52
 */
public class YamlPropertiesProvider {

    private YamlPropertiesReader yamlPropertiesReader;

    /**
     * 初始化环境参数
     */
    public static YamlPropertiesProvider instance() {
        return new YamlPropertiesProvider();
    }

    /**
     * 按环境源读取配置文件
     */
    public YamlPropertiesProvider reader(YamlPropertiesReader yamlPropertiesReader) {
        this.yamlPropertiesReader = yamlPropertiesReader;
        return this;
    }

    /**
     * 返回构建对象
     */
    public YamlPropertiesProvider build() {
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = yamlPropertiesReader.getYamlPropertiesFactoryBean();
        Assert.notNull(yamlPropertiesFactoryBean,"YamlPropertiesProvider can not be null!");
        Assert.notNull(yamlPropertiesFactoryBean.getObject(), "YamlPropertiesProvider not find Properties!");
        Assert.isTrue(!Objects.equals(yamlPropertiesFactoryBean.getObject().size(),0),
                "YamlPropertiesProvider Properties size not is zero!");
        return this;
    }

    /**
     * 获取配置文件
     */
    public Properties getProperties() {
        return yamlPropertiesReader.getYamlPropertiesFactoryBean().getObject();
    }

    /**
     * 获取配置文件字符属性
     */
    public String getPropertiesStr(String key) {
        Properties properties = getProperties();
        if (Objects.isNull(properties)) {
            return "";
        }
        return properties.getProperty(key);
    }

    /**
     * 获取配置文件对象属性
     */
    public Object getPropertiesObj(Object key) {
        Properties properties = getProperties();
        if (Objects.isNull(properties)) {
            return "";
        }
        return properties.get(key);
    }
}
