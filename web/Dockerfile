# Build stage
FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# Production stage
FROM node:18-alpine
WORKDIR /app
RUN npm install -g serve
COPY --from=builder /app/build ./build
COPY --from=builder /app/public ./public

ENV REACT_APP_API_BASE=http://localhost:8080 \
    REACT_APP_API_PREFIX=/auth/api/v1

EXPOSE 3000

HEALTHCHECK --interval=30s --timeout=10s --start-period=10s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:3000 || exit 1

CMD ["serve", "-s", "build", "-l", "3000"]
