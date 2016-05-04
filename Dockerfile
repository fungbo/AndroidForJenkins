# This docker image is just an Android test environment used for Jenkins
# Codebase need to be mounted to docker container, then run the specific command

FROM ubuntu:14.04
MAINTAINER bfeng@thoughtworks.com
ENV REFRESHED_AT 2016_05_03

# install wget
RUN apt-get update -qq && \
    apt-get upgrade -qqy && \
    apt-get install -qqy wget

# install Oracle Java
RUN apt-get update -qq && \
    apt-get upgrade -qqy && \
    apt-get install -qqy  software-properties-common && \
    add-apt-repository ppa:webupd8team/java -y && \
    apt-get update && \
    echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y oracle-java8-installer && \
    apt-get clean

# set environment variables
ENV ANDROID_SDK_VERSION android-sdk_r24.4.1-linux
ENV ANDROID_HOME /opt/android-sdk-linux
ENV PATH ${ANDROID_HOME}/tools:${ANDROID_HOME}/platform-tools:$PATH

ENV JAVA_HOME /usr/lib/jvm/java-8-oracle
RUN ln -s ${JAVA_HOME} /usr/lib/jvm/default-java

# install expect
RUN apt-get update -qq && \
    apt-get upgrade -qqy && \
    apt-get install -qqy expect

# download Android SDK
WORKDIR /opt
RUN wget http://dl.google.com/android/${ANDROID_SDK_VERSION}.tgz && \
    tar -zxf ${ANDROID_SDK_VERSION}.tgz && \
    rm ${ANDROID_SDK_VERSION}.tgz && \
    chmod -R 775 ${ANDROID_HOME}
RUN expect -c '
set timeout -1;
spawn android - update sdk --no-ui;
expect {
    "Do you accept the license" { exp_send "y\r" ; exp_continue }
    eof
}
'
