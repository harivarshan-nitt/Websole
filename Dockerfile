FROM node:lts

WORKDIR /usr/websole

COPY ./express-app /usr/websole

RUN npm install

CMD ["npm","start"]