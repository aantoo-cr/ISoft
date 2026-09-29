# ============================
# Etapa 1: Build de Angular
# ============================
FROM node:20 AS build

WORKDIR /app

# 1. Dependencias
COPY package*.json ./
RUN npm ci

# 2. Código fuente
COPY . .

# 3. Build de producción
RUN npm run build -- --configuration production

# ============================
# Etapa 2: Nginx con archivos estáticos
# ============================
FROM nginx:alpine

# El output por defecto de Angular 17:
# dist/ui-uf-dolar-project/browser
COPY --from=build /app/dist/ui-uf-dolar-project/browser/ /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]


# Stage 1: Build the Angular application
#FROM node:20 AS build

#WORKDIR /app

# Copy package.json and package-lock.json
#COPY package*.json ./

# Install dependencies
#RUN npm install

# Copy the rest of the application code
#COPY . .

# Build the application
#RUN npm run build

# Stage 2: Serve the application with Nginx
#FROM nginx:alpine

# Copy the build output to replace the default nginx contents
#COPY --from=build /app/dist/ui-uf-dolar-project /usr/share/nginx/html

# Expose port 80
#EXPOSE 80

# Start Nginx server
#CMD ["nginx", "-g", "daemon off;"]
