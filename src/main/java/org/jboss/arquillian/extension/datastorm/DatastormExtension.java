package org.jboss.arquillian.extension.datastorm;

import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.core.spi.LoadableExtension;

public class DatastormExtension implements LoadableExtension {

	@Override
	public void register(ExtensionBuilder builder) {
		System.out.println("Invoking Datastorm appenders");
		builder.service(AuxiliaryArchiveAppender.class, DatastormAppender.class)
				.service(AuxiliaryArchiveAppender.class, SWTAppender.class);
	}

}
