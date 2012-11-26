package org.jboss.arquillian.extension.datastorm;

import java.util.Collection;

import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

public class DatastormAppender implements AuxiliaryArchiveAppender {

	@Override
	public Archive<?> createAuxiliaryArchive() {
		System.out.println("Appending Datastorm");
		MavenDependencyResolver dependencyResolver = DependencyResolvers
				.use(MavenDependencyResolver.class).goOffline()
				.loadMetadataFromPom("pom.xml");
		Collection<JavaArchive> datastormArchives = dependencyResolver.artifact(
				"net.sf:datastorm").resolveAs(JavaArchive.class);
		JavaArchive swtArchive = ShrinkWrap
				.create(JavaArchive.class, "datastorm.jar");
		for (JavaArchive archive : datastormArchives) {
			swtArchive.merge(archive);
		}
		return swtArchive;
	}
}
