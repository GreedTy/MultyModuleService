FROM amazoncorretto:17.0.2

COPY ./build/libs/batch-0.0.1-SNAPSHOT.jar /data/
COPY ./entrypoint.sh /data/

COPY ./dd-java-agent.jar /data/

RUN ["chmod", "+x", "/data/entrypoint.sh"]

# Remote Debug Option
# -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8989
