package org.jboss.arquillian.extension.datastorm;

import java.util.Collection;

import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

public class SWTAppender implements AuxiliaryArchiveAppender {

	@Override
	public Archive<?> createAuxiliaryArchive() {
		System.out.println("Appending SWT");
		MavenDependencyResolver dependencyResolver = DependencyResolvers
				.use(MavenDependencyResolver.class).goOffline()
				.loadMetadataFromPom("pom.xml");
		Collection<JavaArchive> swtArchives = dependencyResolver.artifact(
				"org.eclipse:swt").resolveAs(JavaArchive.class);
		JavaArchive swtArchive = ShrinkWrap
				.create(JavaArchive.class, "swt.jar");
		for (JavaArchive archive : swtArchives) {
			swtArchive.merge(archive);
		}
		return swtArchive;
	}

}
