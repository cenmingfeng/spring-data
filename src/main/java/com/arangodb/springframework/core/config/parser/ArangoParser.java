/*
 * DISCLAIMER
 *
 * Copyright 2017 ArangoDB GmbH, Cologne, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 */

package com.arangodb.springframework.core.config.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.data.config.BeanComponentDefinitionBuilder;
import org.springframework.data.config.ParsingUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.arangodb.springframework.core.ArangoFactoryBean;
import com.arangodb.springframework.core.config.ArangoBeanNames;

/**
 * @author Mark Vollmary
 *
 */
public class ArangoParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(final Element element, final ParserContext parserContext) {
		final Object source = parserContext.extractSource(element);
		final BeanComponentDefinitionBuilder componentBuilder = new BeanComponentDefinitionBuilder(element,
				parserContext);
		final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ArangoFactoryBean.class);
		ParsingUtils.setPropertyValue(builder, element, ArangoFactoryBean.PROPERTY_NAME_USERNAME,
			ArangoFactoryBean.PROPERTY_NAME_USERNAME);
		ParsingUtils.setPropertyValue(builder, element, ArangoFactoryBean.PROPERTY_NAME_PASSWORD,
			ArangoFactoryBean.PROPERTY_NAME_PASSWORD);
		ParsingUtils.setPropertyValue(builder, element, ArangoFactoryBean.PROPERTY_NAME_HOST,
			ArangoFactoryBean.PROPERTY_NAME_HOST);
		ParsingUtils.setPropertyValue(builder, element, ArangoFactoryBean.PROPERTY_NAME_PORT,
			ArangoFactoryBean.PROPERTY_NAME_PORT);

		parserContext.pushContainingComponent(new CompositeComponentDefinition(ArangoBeanNames.ARANGO, source));
		final String id = element.getAttribute("id");
		final BeanComponentDefinition component = componentBuilder.getComponent(builder,
			StringUtils.hasText(id) ? id : ArangoBeanNames.ARANGO);
		parserContext.registerBeanComponent(component);
		return component.getBeanDefinition();
	}

}
