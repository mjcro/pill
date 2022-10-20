package io.github.mjcro.pill;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public enum KnownClasses {
    H2_DATABASE(Classifier.DATABASE, "H2 Driver", "org.h2.Driver"),
    MYSQL_DATABASE(Classifier.DATABASE, "MySQL Driver", "com.mysql.cj.jdbc.Driver"),

    SPRING_CORE(Classifier.SPRING, "Spring Core", "org.springframework.core.SpringVersion"),
    SPRING_BEANS(Classifier.SPRING, "Spring Beans", "org.springframework.beans.factory.BeanFactory"),
    SPRING_CONTEXT(Classifier.SPRING, "Spring Context", "org.springframework.context.ApplicationContext"),
    SPRING_AOP(Classifier.SPRING, "Spring AOP", "org.springframework.aop.Pointcut"),
    SPRING_JDBC(Classifier.SPRING, "Spring JDBC", "org.springframework.jdbc.core.RowMapper"),
    SPRING_SECURITY_CORE(Classifier.SPRING, "Spring Security Core", "org.springframework.security.core.Authentication"),
    SPRING_SECURITY_WEB(Classifier.SPRING, "Spring Security Web", "org.springframework.security.web.SecurityFilterChain"),
    SPRING_SESSION_CORE(Classifier.SPRING, "Spring Session Core", "org.springframework.session.Session"),
    SPRING_SESSION_JDBC(Classifier.SPRING, "Spring Session JDBC", "org.springframework.session.jdbc.JdbcIndexedSessionRepository"),

    APACHE_COMMONS_LOGGING(Classifier.LOGGING, "Apache Commons Logging", "org.apache.commons.logging.Log"),
    SLF4J(Classifier.LOGGING, "SLF4j API", "org.slf4j.Logger"),

    AMAZON_AWS_CORE(Classifier.INTEGRATION, "Amazon AWS Core", "com.amazonaws.Request"),
    AMAZON_AWS_S3(Classifier.INTEGRATION, "Amazon AWS S3", "com.amazonaws.services.s3.AmazonS3"),
    JEDIS(Classifier.INTEGRATION, "Jedis", "redis.clients.jedis.Jedis"),


    JACKSON_CORE(Classifier.OTHER, "Jackson Core", "com.fasterxml.jackson.core.type.TypeReference"),
    JACKSON_DATABIND(Classifier.OTHER, "Jackson Databind", "com.fasterxml.jackson.databind.DatabindContext"),
    JACKSON_XML(Classifier.OTHER, "Jackson XML", "com.fasterxml.jackson.dataformat.xml.JacksonXmlModule"),
    JACKSON_YAML(Classifier.OTHER, "Jackson YAML", "com.fasterxml.jackson.dataformat.yaml.YAMLFactory"),
    GUAVA(Classifier.OTHER, "Google Guava", "com.google.common.base.Function"),
    APACHE_COMMONS_CODEC(Classifier.OTHER, "Apache Commons Codec", "org.apache.commons.codec.Encoder"),
    JAKARTA_VALIDATION(Classifier.OTHER, "Jakarta Validation", "jakarta.validation.Constraint"),

    VOID(null, "Object", "java.lang.Object");

    private final Classifier classifier;
    private final String name;
    private final String clazz;

    KnownClasses(Classifier classifier, String name, String clazz) {
        this.name = Objects.requireNonNull(name, "name");
        this.clazz = Objects.requireNonNull(clazz, "clazz");
        this.classifier = classifier == null ? Classifier.OTHER : classifier;
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public String getName() {
        return name;
    }

    public String getClazz() {
        return clazz;
    }

    public boolean isPresent() {
        return IsClassPresentPredicate.INSTANCE.test(getClazz());
    }

    public static void dump(PrintStream output) {
        PrintStream to = output == null ? System.out : output;

        int len = 0;
        for (KnownClasses c : KnownClasses.values()) {
            if (c.getName().length() > len) {
                len = c.getName().length();
            }
        }

        int size = len + 3;

        Arrays.stream(Classifier.values()).sorted(Comparator.comparing(Enum::name)).forEach(classifier -> {
            to.println("== " + classifier.name() + " ==");
            Arrays.stream(KnownClasses.values()).sorted(Comparator.comparing(Enum::name)).forEach(c -> {
                if (c.getClassifier() == classifier) {
                    to.println(c.getName() + " " + String.join("", Collections.nCopies(size - c.getName().length(), ".")) + (c.isPresent() ? " PRESENT" : "........"));
                }
            });
        });
    }

    public enum Classifier {
        LOGGING,
        DATABASE,
        SPRING,
        INTEGRATION,
        OTHER;
    }
}
