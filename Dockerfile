#Deploy
FROM openjdk:17-slim
EXPOSE 8089
ADD target/deploy-app.jar deploy-app.jar
ENTRYPOINT ["java","-jar","deploy-app.jar"]