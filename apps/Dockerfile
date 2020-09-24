FROM centos

RUN yum install -y java

VOLUME /tmp
ADD ${ARTIFACT_HOME}/build/libs/${ARTIFACT_NAME}-${ARTIFACT_VERSION}.jar ${ARTIFACT_NAME}.jar

RUN sh -c 'touch /${ARTIFACT_NAME}.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/${ARTIFACT_NAME}.jar"]