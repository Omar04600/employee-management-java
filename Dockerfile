FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY server/ server/
COPY database/ database/
COPY lib/ lib/

RUN javac -cp "lib/*" -d . server/*.java database/*.java

RUN find . -name "*.class"

EXPOSE 8081

CMD ["java", "-cp", ".:lib/*", "server.Server"]