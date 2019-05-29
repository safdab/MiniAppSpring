####
# This docker image is a multi-stage image that first build the apps with Maven then create the image.
#
# To build it :
# docker build -f src/main/docker/Dockerfile -t tpfinal .
#
# Then run the container using:
# docker run -ti --rm -p 8080:8080 tpfinal
###




FROM openjdk:8-jdk-alpine

LABEL maintainer="BARRY JARNOUEN LAKBIRI"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/*-SNAPSHOT.jar

ADD ${JAR_FILE} tpFinal.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tpFinal.jar"]