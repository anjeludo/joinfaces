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

package org.joinfaces.autoconfigure.myfaces;

import java.util.Set;

import javax.servlet.annotation.HandlesTypes;

import org.apache.myfaces.ee6.MyFacesContainerInitializer;
import org.joinfaces.autoconfigure.JsfClassFactory;
import org.joinfaces.autoconfigure.JsfClassFactoryConfiguration;
import org.joinfaces.autoconfigure.ServletContainerInitializerRegistrationBean;

/**
 * Servlet context initializer of MyFaces.
 * @author Marcelo Fernandes
 */
public class MyfacesServletContextInitializer extends ServletContainerInitializerRegistrationBean<MyFacesContainerInitializer> {

	/**
	 * Constant of another faces config name of MyFaces.
	 */
	public static final String ANOTHER_FACES_CONFIG = "META-INF/standard-faces-config.xml";

	public MyfacesServletContextInitializer() {
		super(MyFacesContainerInitializer.class);
	}

	@Override
	protected Set<Class<?>> getClasses(HandlesTypes handlesTypes) {
		JsfClassFactoryConfiguration jsfClassFactoryConfiguration = JsfClassFactoryConfiguration.builder()
				.handlesTypes(handlesTypes)
				.excludeScopedAnnotations(true)
				.anotherFacesConfig(ANOTHER_FACES_CONFIG)
				.build();
		JsfClassFactory jsfClassFactory = new JsfClassFactory(jsfClassFactoryConfiguration);
		JoinFacesAnnotationProvider.setAnnotatedClasses(jsfClassFactory.getAnnotatedClassMap());
		JoinFacesAnnotationProvider.setUrls(jsfClassFactory.getURLs());
		return jsfClassFactory.getAllClasses();
	}
}
