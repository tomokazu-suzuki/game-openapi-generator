FROM maven:3-eclipse-temurin-17

ENV GEN_DIR /opt/openapi-generator
WORKDIR ${GEN_DIR}
VOLUME  ${MAVEN_HOME}/.m2/repository

# Required from a licensing standpoint
COPY ./LICENSE ${GEN_DIR}

# Required to compile openapi-generator
COPY ./google_checkstyle.xml ${GEN_DIR}

# All poms are copied, then we go offline, to allow for better caching of code changes without fetching all dependencies each time
COPY ./modules/openapi-generator-gradle-plugin/pom.xml ${GEN_DIR}/modules/openapi-generator-gradle-plugin/
COPY ./modules/openapi-generator-maven-plugin/pom.xml ${GEN_DIR}/modules/openapi-generator-maven-plugin/
COPY ./modules/openapi-generator-online/pom.xml ${GEN_DIR}/modules/openapi-generator-online/
COPY ./modules/openapi-generator-cli/pom.xml ${GEN_DIR}/modules/openapi-generator-cli/
COPY ./modules/openapi-generator-core/pom.xml ${GEN_DIR}/modules/openapi-generator-core/
COPY ./modules/openapi-generator/pom.xml ${GEN_DIR}/modules/openapi-generator/
COPY ./generators/game-api-server/pom.xml ${GEN_DIR}/generators/game-api-server/
COPY ./pom.xml ${GEN_DIR}
RUN mvn dependency:go-offline

# Modules are copied individually here to allow for caching of docker layers between major.minor versions
COPY ./modules/openapi-generator-gradle-plugin ${GEN_DIR}/modules/openapi-generator-gradle-plugin
COPY ./modules/openapi-generator-maven-plugin ${GEN_DIR}/modules/openapi-generator-maven-plugin
COPY ./modules/openapi-generator-online ${GEN_DIR}/modules/openapi-generator-online
COPY ./modules/openapi-generator-cli ${GEN_DIR}/modules/openapi-generator-cli
COPY ./modules/openapi-generator-core ${GEN_DIR}/modules/openapi-generator-core
COPY ./modules/openapi-generator ${GEN_DIR}/modules/openapi-generator
COPY ./generators/game-api-server ${GEN_DIR}/generators/game-api-server

# Pre-compile openapi-generator-cli
RUN mvn -B -am -pl "modules/openapi-generator-cli" package

# Pre-compile openapi-generator
RUN mvn -B -am -pl "modules/openapi-generator" package

WORKDIR ${GEN_DIR}/generators/game-api-server
RUN mvn install:install-file -Dfile=${GEN_DIR}/modules/openapi-generator/target/openapi-generator-7.9.0-SNAPSHOT.jar -DgroupId=org.openapitools -DartifactId=openapi-generator -Dversion=7.9.0-SNAPSHOT -Dpackaging=jar
RUN mvn install:install-file -Dfile=${GEN_DIR}/modules/openapi-generator/target/openapi-generator-7.9.0-SNAPSHOT-tests.jar -DgroupId=org.openapitools -DartifactId=openapi-generator-test -Dversion=7.9.0-SNAPSHOT -Dpackaging=jar
RUN mvn package

# # This exists at the end of the file to benefit from cached layers when modifying docker-entrypoint.sh.
WORKDIR ${GEN_DIR}
COPY docker-entrypoint.sh /usr/local/bin/
RUN ln -s /usr/local/bin/docker-entrypoint.sh /usr/local/bin/openapi-generator

ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]

CMD ["help"]
