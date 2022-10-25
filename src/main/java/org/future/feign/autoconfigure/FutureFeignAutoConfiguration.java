package org.future.feign.autoconfigure;

import org.future.feign.yaml.YamlPropertiesProvider;
import org.future.feign.yaml.YamlPropertiesReader;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author zpf
 * @description FutureFeign auto configure
 * @createTime 2022-10-15 14:38
 */
@Configuration
@ConditionalOnJava(JavaVersion.EIGHT)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class FutureFeignAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(YamlPropertiesFactoryBean.class)
    public YamlPropertiesReader yamlPropertiesReader(@Value("${spring.profiles.active:-0}")String profiles){
        return YamlPropertiesReader.initFactoryBean()
                .profile(profiles)
                .initYamlFactory();
    }

    @Bean
    @ConditionalOnBean(YamlPropertiesReader.class)
    public YamlPropertiesProvider yamlPropertiesProvider(ObjectProvider<YamlPropertiesReader> readerObjectProvider){
        return YamlPropertiesProvider.instance()
                .reader(readerObjectProvider.getIfAvailable())
                .build();
    }
}
