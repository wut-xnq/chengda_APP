FROM openjdk:17

VOLUME /home/pan/upms

WORKDIR /home/pan/upms

COPY cm-upms-biz.jar upms.jar

EXPOSE 4000

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java $JAVA_OPTS -jar upms.jar