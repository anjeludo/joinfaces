/*
 * Copyright 2016-2018 the original author or authors.
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

package org.joinfaces.autoconfigure;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.annotation.HandlesTypes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.core.annotation.AnnotationUtils;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class ServletContainerInitializerRegistrationBean<T extends ServletContainerInitializer> implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

	private final Class<T> servletContainerInitializerClass;

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		factory.addInitializers(servletContext -> {
			HandlesTypes handlesTypes = AnnotationUtils.findAnnotation(getServletContainerInitializerClass(), HandlesTypes.class);

			Set<Class<?>> classes = null;

			if (handlesTypes != null && handlesTypes.value().length > 0) {
				classes = getClasses(handlesTypes);
			}

			BeanUtils.instantiateClass(getServletContainerInitializerClass()).onStartup(classes, servletContext);
		});
	}

	protected abstract Set<Class<?>> getClasses(HandlesTypes handlesTypes);


}
