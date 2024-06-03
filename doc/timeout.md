

spring.mvc.async.request-timeout
Amount of time before asynchronous request handling times out. If this value is not set, the default timeout of the underlying implementation is used.


server.tomcat.connection-timeout
Amount of time the connector will wait, after accepting a connection, for the request URI line to be presented.


server.tomcat.keep-alive-timeout
Time to wait for another HTTP request before the connection is closed. When not set the connectionTimeout is used. When set to -1 there will be no timeout.