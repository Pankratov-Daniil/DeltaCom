FROM jboss/wildfly:10.1.0.Final

ENV WILDFLY_USER admin
ENV WILDFLY_PASS admin
ENV JBOSS_HOME /opt/jboss/wildfly
ENV JBOSS_CLI ${JBOSS_HOME}/bin/jboss-cli.sh

RUN $JBOSS_HOME/bin/add-user.sh -u $WILDFLY_USER -p $WILDFLY_PASS --silent

ADD /WildflyConf/activemq-rar-5.14.5.rar $JBOSS_HOME/standalone/deployments/activemq-rar-5.14.5.rar 
ADD /WildflyConf/modules $JBOSS_HOME/modules/
ADD /WildflyConf/customisation $JBOSS_HOME/customization/

# Configure Wildfly server
RUN echo "=> Starting WildFly server" && \
		bash -c '$JBOSS_HOME/bin/standalone.sh &' && \
    echo "=> Waiting for the server to boot" && \
		bash -c 'until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do sleep 1; done' && \
	echo "=> Creating an activemq resource adapter" && \
		$JBOSS_CLI -c --file=$JBOSS_HOME/customization/activemq-res-adapter.cli && \
	echo "=> Generating certificate" && \
		keytool -genkeypair -alias wildfly -storetype jks -keyalg RSA -keysize 2048 -keypass password1 -keystore ${JBOSS_HOME}/standalone/configuration/wildfly.keystore.jks -storepass password1 -dname "CN=appserver,OU=DeltaCom,O=DeltaCom,L=Ko0LeR,ST=NC,C=RU" -validity 730 -v && \
    echo "=> Shutting down WildFly and Cleaning up" && \
		$JBOSS_CLI --connect --command=":shutdown" && \
		rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ $JBOSS_HOME/standalone/log/* && \
		rm -f /tmp/*.jar
	  

	  
ADD target/AdvertisingStand.war /opt/jboss/wildfly/standalone/deployments/

# Expose http and admin ports
EXPOSE 8080 8787 9990

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]