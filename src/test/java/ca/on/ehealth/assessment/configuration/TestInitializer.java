package ca.on.ehealth.assessment.configuration;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Test Application context initializer
 * make use the context caching
 * @author yuxundu
 */
public class TestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        configurableApplicationContext.getEnvironment().setActiveProfiles("test");
    }
}
