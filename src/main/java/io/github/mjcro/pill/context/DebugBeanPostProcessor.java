package io.github.mjcro.pill.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Objects;

/**
 * Outputs all beans seen by this post processor.
 */
public class DebugBeanPostProcessor implements BeanPostProcessor {
    private final PrintStream output;

    public DebugBeanPostProcessor() {
        this(System.err);
    }

    public DebugBeanPostProcessor(OutputStream outputStream) {
        this(new PrintStream(Objects.requireNonNull(outputStream, "outputStream"), true));
    }

    public DebugBeanPostProcessor(PrintStream printStream) {
        this.output = Objects.requireNonNull(printStream, "printStream");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        output.printf(
                "Bean '%s' is backed by '%s'\n",
                beanName,
                bean.getClass().getName()
        );
        return bean;
    }
}
