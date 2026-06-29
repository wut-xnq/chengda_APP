FROM openjdk:17

VOLUME /home/pan/gateway

WORKDIR /home/pan/gateway

COPY cm-gateway.jar gateway.jar

EXPOSE 9999

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java $JAVA_OPTS -jar gateway.jar