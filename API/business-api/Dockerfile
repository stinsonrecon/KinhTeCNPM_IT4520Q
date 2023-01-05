FROM openjdk:8-jre-alpine
USER root
ENV TZ="Asia/Ho_Chi_Minh"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
MAINTAINER business-api
ENV LANG en_GB.UTF-8
RUN apk add --update ttf-dejavu && rm -rf /var/cache/apk/*

RUN mkdir /app
COPY target/business-api-1.0.jar /app/
CMD ["java", "-Duser.timezone=GMT+7", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/business-api-1.0.jar"]
EXPOSE 7888