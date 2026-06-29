FROM openjdk:17

VOLUME /home/pan/auth

WORKDIR /home/pan/auth

COPY cm-auth.jar auth.jar

EXPOSE 3000

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java $JAVA_OPTS -jar auth.jar