FROM amazoncorretto:17.0.2

COPY ./build/libs/api-0.0.1-SNAPSHOT.jar /data/

COPY ./dd-java-agent.jar /data/

# Remote Debug Option
# -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8989

CMD java $JVM_OPTS -jar /data/api-0.0.1-SNAPSHOT.jar
