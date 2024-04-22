FROM maven:3.8.2-openjdk-17 as builder

WORKDIR /app

COPY ./pom.xml ./pom.xml

# Budowanie zależności aplikacji
# RUN mvn dependency:go-offline -B
RUN mvn dependency:resolve

# Kopia reszty plików
COPY ./src ./src

# Budowanie aplikacji
RUN mvn package -DskipTests

# Nowy etap budowy - Uruchamianie aplikacji
FROM openjdk:17-jdk

WORKDIR /app

# Kopia pliku .jar z etapu budowy
COPY --from=builder /app/target/spring-backend.jar ./

EXPOSE 8080

# Uruchamianie aplikacji
ENTRYPOINT ["java","-jar","spring-backend.jar"]