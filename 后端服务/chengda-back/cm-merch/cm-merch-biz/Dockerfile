FROM openjdk:17

VOLUME /home/pan/merch

WORKDIR /home/pan/merch

COPY cm-merch-biz.jar merch.jar

EXPOSE 7200

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java $JAVA_OPTS -jar merch.jar