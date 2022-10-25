package org.future.feign.annotaition;


import org.apache.commons.lang3.StringUtils;
import org.future.feign.bean.FutureFeignBean;
import org.future.feign.other.Constant;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Set;

/**
 * @author zpf
 * @description future-client register
 * @createTime 2022-10-14 10:59
 */
@SuppressWarnings("PMD.@NonNullApi parameter all")
public class FutureFeignRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware, ResourceLoaderAware {

    /**
     * if future-client no path this is default path
     */
    private static final String DEFAULT_IP_PORT = "http://localhost:80";

    /**
     * @see Environment
     */
    private Environment environment;

    /**
     * @see ResourceLoader
     */
    private ResourceLoader resourceLoader;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        registerFutureFeignClients(metadata, registry);
    }

    /**
     * @param metadata annotion register
     * @param registry bean register
     * @description: register FutureFeignClients
     * @author: zpf
     * @see EnableFutureFeignClients
     */
    private void registerFutureFeignClients(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        //获取开启注解值
        AnnotationAttributes attributes = AnnotationAttributes
                .fromMap(metadata.getAnnotationAttributes(EnableFutureFeignClients.class.getName()));
        Assert.notNull(attributes, "@EnableFutureFeignClients  AnnotationAttributes is null ！");
        String[] scanPackages = attributes.getStringArray(Constant.BASE_PACKAGES);

        ClassPathScanningCandidateComponentProvider classPathScan = getClassPathScan();

        for (String scanPackage : scanPackages) {
            Set<BeanDefinition> beanDefinitions = classPathScan.findCandidateComponents(scanPackage);

            for (BeanDefinition next : beanDefinitions) {
                if (next instanceof AnnotatedBeanDefinition) {
                    AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) next;
                    AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                    Assert.isTrue(annotationMetadata.isInterface(),
                            "@FutureFeignClient can only be specified on an interface");
                    registerFutureFeignClient(registry, beanDefinition.getMetadata(),
                            AnnotationAttributes.fromMap(annotationMetadata
                                    .getAnnotationAttributes(FutureFeignClient.class.getCanonicalName())));
                }

            }
        }
    }

    /**
     * @param registry           bean register
     * @param annotationMetadata annotion params
     * @param attributes         annotion values
     * @author: zpf
     * @description: register FutureFeignClient
     * @see FutureFeignClient
     */
    private void registerFutureFeignClient(BeanDefinitionRegistry registry,
                                           AnnotationMetadata annotationMetadata, AnnotationAttributes attributes) {
        String className = annotationMetadata.getClassName();
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(FutureFeignBean.class);
        String name = getName(attributes);
        Assert.notNull(name, "@FutureFeignClient name not be null !");
        definition.addPropertyValue(Constant.INTERFACE_CLASS, className);
        definition.addPropertyValue(Constant.URI, getUri(attributes));
        definition.addPropertyValue(Constant.PATH, attributes.get(Constant.PATH));
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        BeanDefinitionHolder holder = new BeanDefinitionHolder(definition.getBeanDefinition(),
                className, new String[]{name});
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }

    /**
     * @param attributes annotion values
     * @return annotion name
     * @description: get bean name
     * @see Constant
     */
    private String getName(AnnotationAttributes attributes) {
        String value = null;
        if (Objects.isNull(attributes)) {
            return value;
        }
        String name = attributes.getString(Constant.NAME);
        if (StringUtils.isNotBlank(name)) {
            value = name;
        }
        return value;
    }

    /**
     * @param attributes annotion values
     * @return uri
     * @description: get uri address
     * @author: zpf
     * @see Constant
     */
    private String getUri(AnnotationAttributes attributes) {
        Object uri = attributes.get(Constant.URI);
        if (Objects.isNull(uri) || StringUtils.isBlank(uri.toString())) {
            return DEFAULT_IP_PORT;
        }
        String uriObjStr = uri.toString();
        String uriStr = null;
        //        ${tms.api.uri:0}
        if (uriObjStr.contains("${")) {
            String key = uriObjStr.substring(uriObjStr.indexOf("{") + 1, uriObjStr.indexOf(":"));
            uriStr = environment.getProperty(key);
            if (StringUtils.isBlank(uriStr) && uriObjStr.contains(":") && uriObjStr.lastIndexOf("}") != -1) {
                uriStr = uriObjStr.substring(uriObjStr.indexOf(":") + 1, uriObjStr.lastIndexOf("}"));
            }
        } else {
            uriStr = uriObjStr;
        }
        Assert.isTrue(StringUtils.isNotBlank(uriStr), "@FutureFeignClient uri not be null !");
        if (!uriStr.startsWith("http://")) {
            uriStr = "http://" + uriStr;
        }
        return uriStr;
    }

    /**
     * @return spring  Class Path Scanning
     * @description: get Class Path Scanning params
     * @author: zpf
     * @see ClassPathScanningCandidateComponentProvider
     * @see FutureFeignClient
     */
    private ClassPathScanningCandidateComponentProvider getClassPathScan() {
        ClassPathScanningCandidateComponentProvider classPathScan = new ClassPathScanningCandidateComponentProvider(false, environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                //是一个独立的类 且不是一个注解
                return beanDefinition.getMetadata().isIndependent() && !beanDefinition.getMetadata().isAnnotation();
            }
        };
        //扫描过滤 带有 FutureFeignClient 注解的接口类
        classPathScan.setResourceLoader(resourceLoader);
        classPathScan.setResourcePattern("**/*.class");
        classPathScan.addIncludeFilter(new AnnotationTypeFilter(FutureFeignClient.class));
        return classPathScan;
    }
}
