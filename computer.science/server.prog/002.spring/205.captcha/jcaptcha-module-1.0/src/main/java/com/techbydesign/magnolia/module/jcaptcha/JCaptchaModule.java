package com.techbydesign.magnolia.module.jcaptcha;

import info.magnolia.cms.core.Content;
import info.magnolia.cms.module.AbstractModule;
import info.magnolia.cms.module.InitializationException;
import info.magnolia.cms.module.InvalidConfigException;

/**
 * If you need to hook into Magnolia's administration interface, you might want
 * to extends AbstractAdminModule. If your module only has content, use SimpleContentModule.
 *
 * @see AbstractModule
 * @see info.magnolia.module.admininterface.AbstractAdminModule
 * @see info.magnolia.module.admininterface.SimpleContentModule
 */
public class JCaptchaModule extends AbstractModule {
    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AbstractModule.class);

    public void init(Content configNode) throws InvalidConfigException, InitializationException {
        logger.info("Initializing module : " + getName());
    }
}