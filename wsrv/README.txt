This module contains a very small and raw implementation of a multi thread web server.
It runs on top of Jetty with 'mvn clean jetty:run' and exposes files via HTTP gets.
The sample web.xml contains some configuration parameters:
- the thread pool size
- the base dir for the files / resources to show

It allows different implementations of the HTTP GET method via different servlets.
- WSRVBaseServlet is the basic abstract one, cannot be actually instantiated but offers the basics of the request handlign
- DefaultWSRVServlet is the default actually instantiable extension of WSRVBaseServlet which allows to delegate threads for fetching resources from the filesystem
- RepositoryAwareWSRVServlet is the second instantiable extension of WSRVBaseServlet which allows for plugging of different repositories other than file system, but the repository browsing is not constrained in a thread
- WSRVThreadedBaseServlet delegates each handling of GET methods to a separate thread which in turns uses threads from the same pool to browse the filesystem (this is the default one)

