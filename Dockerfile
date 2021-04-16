FROM tomee:11-jre-8.0.6-plus
WORKDIR /


COPY /dockerFiles/context.xml /usr/local/tomee/conf/context.xml
COPY /target/Run_connect.war /usr/local/tomee/webapps/Run_connect.war

EXPOSE 8080

CMD ["catalina.sh", "run"]


# how to build:
#1: mvn compile
#2: docker build -t run_connect:VERSION

# how to run:
#1: docker run -dp 8080:8080 run_connect:VERSION

# Todo:
# maybe the database connection doenst work directly. Then we'll have to find the driver and import it just like conext (row5)