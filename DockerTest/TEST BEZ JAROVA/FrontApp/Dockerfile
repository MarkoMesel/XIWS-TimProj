FROM node:alpine3.11

LABEL maintainer="mmarkom0001@gmail.com"

WORKDIR /usr/src/server 
COPY . .
RUN npm install
EXPOSE 8080
CMD ["npm", "start"]   
