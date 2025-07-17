FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/ShopEase.jar app.jar
EXPOSE 1234
ENTRYPOINT ["java", "-jar", "app.jar"]