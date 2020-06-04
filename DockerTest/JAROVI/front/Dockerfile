FROM node:alpine3.11

LABEL maintainer="mmarkom0001@gmail.com"

WORKDIR /usr/src/server 
COPY . .
RUN npm install
EXPOSE 3000
CMD ["npm", "start"]   
