package ca.on.ehealth.assessment;

import ca.on.ehealth.assessment.configuration.TestInitializer;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Same Applicaton context
 * @author yuxundu
 */

@AutoConfigureMockMvc
@ContextConfiguration(initializers = TestInitializer.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)

public abstract class AbstractIntegrationTest {
    @BeforeAll
    public static void commonSetup() {
        // e.g. provide WireMock stubs
    }
}
