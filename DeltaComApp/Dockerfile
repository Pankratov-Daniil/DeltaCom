FROM jboss/wildfly:10.1.0.Final

ENV WILDFLY_USER admin
ENV WILDFLY_PASS admin
ENV DB_NAME DeltaComMySQL
ENV DB_URI deltacom-mysql:3306
ENV DB_USER root
ENV DB_PASS 123
ENV DB_LINK deltacom?characterEncoding=UTF-8
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
	echo "=> Adding MySQL connector" && \
		$JBOSS_CLI --connect --command="module add --name=com.mysql --resources=${JBOSS_HOME}/customization/mysql-connector-java-5.1.34.jar --dependencies=javax.api,javax.transaction.api" && \
    echo "=> Adding MySQL driver" && \
		$JBOSS_CLI --connect --command="/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-class-name=com.mysql.jdbc.Driver,driver-xa-datasource-class-name=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource)" && \
    echo "=> Creating a new datasource" && \
		$JBOSS_CLI --connect --command="data-source add \
			--name=${DB_NAME}DS \
			--jndi-name=java:jboss/datasources/${DB_NAME}DS \
			--user-name=${DB_USER} \
			--password=${DB_PASS} \
			--driver-name=mysql \
			--connection-url=jdbc:mysql://${DB_URI}/${DB_LINK} \
			--use-ccm=false \
			--max-pool-size=25 \
			--use-java-context=true \
			--jta=true \
			--blocking-timeout-wait-millis=5000 \
			--enabled=true" && \
	echo "=> Creating an activemq resource adapter" && \
		$JBOSS_CLI -c --file=$JBOSS_HOME/customization/activemq-res-adapter.cli && \
    echo "=> Shutting down WildFly and Cleaning up" && \
		$JBOSS_CLI --connect --command=":shutdown" && \
		rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ $JBOSS_HOME/standalone/log/* && \
		rm -f /tmp/*.jar
	  

	  
ADD target/DeltaCom.war /opt/jboss/wildfly/standalone/deployments/

# Expose http and admin ports
EXPOSE 8080 8787 9990

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]