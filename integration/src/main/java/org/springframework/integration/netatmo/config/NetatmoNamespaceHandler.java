/**
 * 
 */
package org.springframework.integration.netatmo.config;

import org.springframework.integration.config.xml.AbstractIntegrationNamespaceHandler;

/**
 * @author Nikolai Bock
 *
 */
public class NetatmoNamespaceHandler
        extends AbstractIntegrationNamespaceHandler {

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
     */
    @Override
    public void init() {
        registerBeanDefinitionParser("inbound-channel-adapter", new NetatmoInboundChannelAdapterParser());

    }

}
