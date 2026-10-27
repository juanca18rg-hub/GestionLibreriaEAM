
FROM eclipse-temurin:21-jdk AS compilador

WORKDIR /compilacion


COPY Libreria/src/*.java ./

RUN mkdir /clases && javac -encoding UTF-8 -d /clases *.java

-------
FROM eclipse-temurin:21-jre


RUN groupadd -r libreria && useradd -r -g libreria libreria

WORKDIR /app

COPY --from=compilador /clases /app/clases


ENV DATOS_DIR=/app/datos
ENV LANG=C.UTF-8

RUN mkdir -p /app/datos && chown -R libreria:libreria /app


VOLUME ["/app/datos"]

USER libreria

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-Dstdout.encoding=UTF-8", "-cp", "/app/clases", "Libreria"]
