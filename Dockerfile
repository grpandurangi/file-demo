FROM tomcat:8.0.43-jre8
MAINTAINER gururaj.r.pandurangi@pwc.com

ADD app/target/file-demo-app-0.1.0.war /usr/local/tomcat/webapps/file-demo-app.war
ADD server.xml /usr/local/tomcat/conf/server.xml
EXPOSE 9090


