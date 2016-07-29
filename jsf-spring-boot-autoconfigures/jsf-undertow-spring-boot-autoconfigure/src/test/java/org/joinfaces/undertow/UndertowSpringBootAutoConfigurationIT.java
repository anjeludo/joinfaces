/*
 * Copyright 2016-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.joinfaces.undertow;

import java.io.IOException;
import java.net.MalformedURLException;

import io.undertow.servlet.api.DeploymentInfo;

import org.testng.annotations.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = UndertowSpringBootAutoConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Test
public class UndertowSpringBootAutoConfigurationIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private UndertowSpringBootAutoConfiguration undertowSpringBootAutoConfiguration;

	public void customize() throws MalformedURLException, IOException {
		UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();

		this.undertowSpringBootAutoConfiguration.customize(factory);

		UndertowDeploymentInfoCustomizer undertowDeploymentInfoCustomizer
			= factory.getDeploymentInfoCustomizers().iterator().next();

		DeploymentInfo deploymentInfo = new DeploymentInfo();

		undertowDeploymentInfoCustomizer.customize(deploymentInfo);

		assertThat(deploymentInfo.getResourceManager().getResource("test.txt"))
			.isNotNull();
	}

	public void customizeTomcat() throws MalformedURLException, IOException {
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();

		this.undertowSpringBootAutoConfiguration.customize(factory);

		assertThat(factory.getContextPath())
			.isEqualTo("");
	}
}