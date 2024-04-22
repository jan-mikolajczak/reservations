# Zacznij od Node.js do budowy aplikacji
FROM node:16-alpine as builder

WORKDIR /app

# Skopiuj pliki package.json oraz package-lock.json
COPY package*.json ./

# Zainstaluj zależności
RUN npm install

# Skopiuj resztę plików
COPY . .

# Zbuduj aplikację
RUN npm run build

# Nowy etap - Serwowanie aplikacji
FROM nginx:1.19-alpine

# Skopiuj konfigurację nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Kopia plików statycznych z etapu budowy do serwera nginx
COPY --from=builder /app/dist/angular-16-crud /usr/share/nginx/html

# Otwieranie portu 80
EXPOSE 8081

# Start serwera nginx
CMD ["nginx", "-g", "daemon off;"]
