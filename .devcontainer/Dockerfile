FROM mcr.microsoft.com/devcontainers/java:17

RUN apt-get update \
    && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends gcc g++ libc6-dev ;

RUN /bin/bash -c 'echo "set -o vi" >> $HOME/.bashrc \
    && echo "alias ll=\"ls -l\"" >> $HOME/.bashrc \
    && echo "alias lla=\"ls -lsa\"" >> $HOME/.bashrc \
    && echo "alias c=clear" >> $HOME/.bashrc'