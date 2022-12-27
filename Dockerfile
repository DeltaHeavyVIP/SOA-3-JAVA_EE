#define base docker image
FROM quay.io/wildfly/wildfly:26.1.1.Final
COPY service-1-client/deployment/standalone.xml /opt/jboss/wildfly/standalone/configuration/

COPY service-1-client/target/service-1-client-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
COPY service-1-server/target/service-1-server-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/jboss/wildfly/standalone/deployments/
COPY service-1-client/deployment/postgresql-42.5.1.jar /opt/jboss/wildfly/standalone/deployments/