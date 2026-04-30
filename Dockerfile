# Contenedor de compilación
FROM eclipse-temurin:25 AS buildstage

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src /app/src
COPY Wallet_MIBD /app/oracle_wallet

ENV TNS_ADMIN=/app/oracle_wallet

RUN mvn clean package

# Contenedor de ejecución
FROM eclipse-temurin:25

WORKDIR /app

COPY --from=buildstage /app/target/petshop-0.0.1-SNAPSHOT.jar /app/petshop.jar

COPY Wallet_MIBD /app/oracle_wallet

ENV TNS_ADMIN=/app/oracle_wallet

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "petshop.jar"]