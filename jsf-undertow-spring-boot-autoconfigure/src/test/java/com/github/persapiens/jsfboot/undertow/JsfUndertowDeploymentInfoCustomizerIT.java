package com.github.persapiens.jsfboot.undertow;

import io.undertow.servlet.api.DeploymentInfo;
import java.io.IOException;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringApplicationConfiguration(classes = UndertowSpringBootAutoConfiguration.class)
@WebAppConfiguration
@Test
public class JsfUndertowDeploymentInfoCustomizerIT extends AbstractTestNGSpringContextTests {

    @Autowired
    private UndertowProperties undertowProperties;

	public void testGetResource() throws IOException {
        UndertowDeploymentInfoCustomizer undertowDeploymentInfoCustomizer 
            = new JsfUndertowDeploymentInfoCustomizer(undertowProperties);
        
        DeploymentInfo deploymentInfo = new DeploymentInfo();
        
        undertowDeploymentInfoCustomizer.customize(deploymentInfo);
        
		assertThat(deploymentInfo.getResourceManager().getResource("test.txt")).isNotNull();
	}

}
