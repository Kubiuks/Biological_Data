FROM adoptopenjdk/openjdk11
RUN addgroup --system spring && adduser --system spring && usermod -aG spring spring
USER spring:spring
ARG JAR_FILE=target/*.jar
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-jar","/app.jar"]