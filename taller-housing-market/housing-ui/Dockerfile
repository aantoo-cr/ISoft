FROM node:20 AS build

WORKDIR /app

# Dependencias
COPY package*.json ./
RUN npm install

COPY . .

# Build
RUN npm run build

# Stage 2: Nginx
FROM nginx:alpine

RUN rm -rf /usr/share/nginx/html/*

COPY --from=build /app/dist/housing-ui/ /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
