FROM node:20.17.0 AS build
WORKDIR /app
COPY ./ks-front/package*.json .
RUN npm install

COPY ./ks-front/angular.json .
COPY ./ks-front/tsconfig.app.json .
COPY ./ks-front/tsconfig.json .
COPY ./ks-front/tsconfig.spec.json .
COPY ./ks-front/src ./src
RUN npm run build

FROM nginx:stable
COPY --from=build /app/dist/ks-front/browser /usr/share/nginx/html
COPY ./ops/ks-front-nginx-default.conf /etc/nginx/conf.d/default.conf
EXPOSE 80

# ENTRYPOINT ["/bin/bash"]
