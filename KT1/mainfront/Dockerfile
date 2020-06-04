FROM node:alpine3.11

LABEL maintainer="danijelradakovic@uns.ac.rs"

WORKDIR /usr/src/server 
COPY . .
RUN ["npm", "install"]
EXPOSE 4200
CMD [ "npm", "run", "build", "--prod"]   
