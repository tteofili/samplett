This module contains a very small and raw implementation of a multi thread web server.
It runs as a standalone java application using java.nio package for the communication/socket facilities.
It exposes resources using HTTP GET calls.
The resources are served by repositories (ResourceRepository interface).
This is because ideally resources could be not only on file system, but also on the web, on JCR repositories, etc.

After running a 'mvn clean install' the server can be started launching the following script (generated with Application Assembler Maven Plugin):
  Linux: sh target/appassembler/bin/wsrv $numOfThreads $repositoryType $repositoryRootNode
  Windows: target/appassembler/bin/wsrv.bat $numOfThreads $repositoryType $repositoryRootNode
where:
  - $numOfThreads is the number of threads that should be used to handle client connections
  - $repositoryType is the type of repository which is laying behind the webserver (available options are : fs or url)
  - $repositoryRootNode is the rootNode from which the specified repository has to 'start' looking for resources

example for 'fs' repository based server:
  sh target/appassembler/bin/wsrv 100 fs .

example for 'url' repository based server
  sh target/appassembler/bin/wsrv 100 url http://people.apache.org/~tommaso
note that, if the root is an HTTP resource, then the server will work like a proxy

then try the http://localhost:8080 url in your browser to see the results

The components used for this project are:
 - java.nio for the communication protocol
 - commons.io to ease reads/writes and I/O conversions (e.g. stream>byte[])
 - org.testng for unit testing
 - org.mockito to mock objects in order to improve unit tests isolation

Known extensions points:
 - HTTP cache should be handled
 - Could introduce a better framework for 'services' instantiation like ResourceRepository (ServiceLoader, OSGi, Guice, etc.)
 - Eventually support other HTTP methods (e.g. HEAD, OPTIONS)

A FindBugs reports and API javadoc pages can be obtained running the 'mvn site' command.
