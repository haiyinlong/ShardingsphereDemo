FROM eclipse-temurin:17-jre-alpine
WORKDIR  /app
COPY ./target/ShardingsphereDemo-0.0.1-SNAPSHOT.jar ShardingsphereDemo.jar
EXPOSE 8080
CMD ["java","-jar","ShardingsphereDemo.jar"]
