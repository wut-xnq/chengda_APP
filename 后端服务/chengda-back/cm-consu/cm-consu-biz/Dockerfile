FROM openjdk:17

VOLUME /home/pan/consu

WORKDIR /home/pan/consu

COPY cm-consu-biz.jar consu.jar

EXPOSE 7100

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java $JAVA_OPTS -jar consu.jar