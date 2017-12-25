# Use an official Python runtime as a parent image
#FROM python:2.7-slim


FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} phonebook.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/phonebook.jar"]

#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG JAR_FILE
#ADD ${JAR_FILE} phonebook.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/phonebook.jar"]

#FROM java:8
#VOLUME /tmp
#ADD phonebook.jar phonebook.jar
#RUN bash -c 'touch /phonebook.jar'
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/phonebook.jar"]


# Set the working directory to /phonebook
#WORKDIR /phonebook

# Copy the current directory contents into the container at /phonebook
#ADD . /phonebook

# Install any needed packages specified in requirements.txt
#RUN echo install --trusted-host pypi.python.org -r requirements.txt
#RUN bash -c 'touch /phonebook.jar'

# Make port 80 available to the world outside this container
#EXPOSE 80

# Define environment variable
#ENV JAVA_HOME /usr/lib/jvm/java-8-oracle


# Run app.py when the container launches
#CMD ["bash"]