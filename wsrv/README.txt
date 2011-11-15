This module contains a very small and raw implementation of a multi thread web server.
It runs as a standalone java application using java.nio package for the communication/socket facilities.
It exposes resources using HTTP GET calls.
The resources are served by repositories (ResourceRepository interface).
This is because ideally resources could be not only on file system, but also on the web, on JCR repositories, etc.

The server can be started launching the following command:
  java -cp wsrv-0.0.1-SNAPSHOT.jar com.github.wsrv.nio.WebServerRunner $numOfThreads $repositoryType $repositoryRootNode
where:
  - $numOfThreads is the number of threads that should be used to handle client connections
  - $repositoryType is the type of repository which is laying behind the webserver (available options are : fs or url)
  - $repositoryRootNode is the rootNode from which the specified repository has to 'start' looking for resources
