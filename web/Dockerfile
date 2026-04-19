FROM node:25-alpine3.22

WORKDIR /app

COPY package.json .

RUN npm ci

COPY . .

ENTRYPOINT [ "npm","run","start" ]