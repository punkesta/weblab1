### CI / CD

- CI: автоматична збірка та тести через GitHub Actions
- CI Status
  ![Status SVG](https://github.com/<твій_юзер>/weblab1/actions/workflows/ci.yml/badge.svg)

- CD: Docker image публікується у GitHub Container Registry при створенні тега v*, наприклад `v1.0.0`.

Приклад запуску локально:

```bash
./gradlew clean bootJar -x test
docker compose up --build
