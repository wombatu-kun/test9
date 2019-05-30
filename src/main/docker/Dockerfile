FROM openjdk:8-jdk-alpine
VOLUME /data
ARG APP_PATH=/usr/local/lettercounter
ENV APP_PATH=${APP_PATH}
RUN mkdir -p ${APP_PATH}
ADD @project.build.finalName@.jar ${APP_PATH}/
COPY lib ${APP_PATH}/lib
CMD java -jar ${APP_PATH}/@project.build.finalName@.jar
