/**
 * 
 */
package org.springframework.integration.netatmo.config;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractPollingInboundChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.netatmo.inbound.PublicWeatherReceivingMessageSource;
import org.w3c.dom.Element;

/**
 * @author Nikolai Bock
 *
 */
public class NetatmoInboundChannelAdapterParser
        extends AbstractPollingInboundChannelAdapterParser {

    @Override
    protected BeanMetadataElement parseSource(Element element,
            ParserContext parserContext) {
        Class<?> clazz = determineClass(element, parserContext);
        BeanDefinitionBuilder builder = BeanDefinitionBuilder
                .rootBeanDefinition(clazz);
        builder.addConstructorArgReference(
                element.getAttribute("netatmo-template"));
        builder.addConstructorArgValue(element.getAttribute(ID_ATTRIBUTE));

        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder,
                element, "metadata-store");
        builder.addPropertyValue("latSW", element.getAttribute("latSW"));
        builder.addPropertyValue("lonSW", element.getAttribute("lonSW"));
        builder.addPropertyValue("latNE", element.getAttribute("latNE"));
        builder.addPropertyValue("lonNE", element.getAttribute("lonNE"));
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "required-data");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "filter");
        return builder.getBeanDefinition();
    }

    private static Class<?> determineClass(Element element,
            ParserContext parserContext) {
        Class<?> clazz = null;
        String elementName = element.getLocalName().trim();
        if ("inbound-channel-adapter".equals(elementName)) {
            clazz = PublicWeatherReceivingMessageSource.class;
        } else {
            parserContext.getReaderContext().error("element '" + elementName
                    + "' is not supported by this parser.", element);
        }
        return clazz;
    }

}
