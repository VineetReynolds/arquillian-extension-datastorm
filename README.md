What is this?
=============

An Arquillian extension that appends DataStorm and SWT into a deployment made to an application server. Helps debug your persistence tests faster. You no longer need to debug your persistence layer through a bunch of Java method invocations (e.g. `connection.createStatement().execute(sql)` or similar crock) in a remote debugger. Use SQL.

How do I run it?
================

Add SWT to your MVN repo
------------------------

Sorry for all this trouble :|

Download the SWT distribution from http://www.eclipse.org/swt/

I'm using SWT 4.2.1-gtk-linux-x86_64 for demonstration.
Unzip the SWT distribution and install into your Maven repo:

    mvn install:install-file -DgroupId=org.eclipse -DartifactId=swt -Dversion=4.2.1-gtk-linux-x86_64 -Dfile=swt-debug.jar -Dpackaging=jar

If you're using a different version, you will need to change the referenced dependency from the project POM.

Note - use the `org.eclipse` groupId and `swt` artifactId as the appender relies on them.


Add DataStorm to your Maven repo
--------------------------------

Download the distribution from http://sourceforge.net/projects/datastorm/files/

Unzip and install into your Maven repo:

    mvn install:install-file -DgroupId=net.sf -DartifactId=datastorm -Dversion=1.0.00beta2 -Dfile=DataStorm-1.0.00beta2.jar -Dpackaging=jar

If you're using a different version, you will need to change the referenced dependency from the project POM.

Note - use the `net.sf` groupId and `datastorm` artifactId as the appender relies on them.


Install the arquillian-extension-datastorm in your Maven repo
-------------------------------------------------------------

Clone the sources. Use git (or download the sources):

    git clone https://github.com/VineetReynolds/arquillian-extension-datastorm.git
    
Install the extension. You may need to add the public Nexus repository of JBoss for other Arquillian artifacts.
    
    mvn clean install
    
    
Add the dependencies to your project POM
----------------------------------------

Add the previously installed dependencies in your POM, like:

    <dependency>
    	<groupId>org.jboss.arquillian.extension</groupId>
    	<artifactId>arquillian-extension-datastorm</artifactId>
    	<version>1.0.0-SNAPSHOT</version>
    	<scope>test</scope>
    </dependency>    
    <dependency>
    	<groupId>org.eclipse</groupId>
    	<artifactId>swt</artifactId>
    	<version>4.2.1-gtk-linux-x86_64</version>
    	<scope>test</scope>
    </dependency>    
    <dependency>
    	<groupId>net.sf</groupId>
    	<artifactId>datastorm</artifactId>
    	<version>1.0.00beta2</version>
    	<scope>test</scope>
    </dependency>


Using DataStorm in your test
----------------------------

1. Inject the DataSource into your Arquillian test class, like so:

        @Resource(mappedName="java:jboss/datasources/ExampleDS")
        private DataSource ds;

2. Invoke DataStorm from the test to debug, like:

        new DataStorm().show(ds); // Provide DataStorm with the injected datasource

3. Enjoy a faster debugging cycle for your persistence-layer tests :)

Things to do
============

1. Write an Eclipse plugin to do this, instead of depending on DataStorm and SWT. Both are not located in Maven Central or other repos. And I personally don't like launching a new "IDE" to debug.
2. Investigate workarounds for transaction timeouts and query resultsets. The default JTA transaction timeout is 30s - we dont want developers to fire away all their SQL queries before the transaction times out. That would be pointless since some of the queries run by developers may return resultsets depending on the current transaction isolation level. Querying from outside the transaction used by the SUT may result in a different resultset.