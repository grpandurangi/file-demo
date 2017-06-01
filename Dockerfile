FROM jetty
MAINTAINER gururaj.r.pandurangi@pwc.com

ADD app/target/file-demo-app-0.1.0.war /var/lib/jetty/webapps/file-demo-app.war
ADD jetty-http.xml /usr/local/jetty/etc/jetty-http.xml
EXPOSE 9090


