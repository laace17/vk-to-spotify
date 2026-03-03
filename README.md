Pet-project на Java для переноса треков из ВКонтакте в Spotify

Проект реализует полный pipeline:
1. Извлечение треков из JSON VK
2. Подготовка данных (разделение автор/название)
3. Получение access token Spotify
4. Поиск треков через Spotify Web API
5. Добавление треков в Liked Songs

---

## Архитектура проекта

### VKTrackInfo
Парсит файл `catalog0.json`, извлекает список треков из структуры VK playlist JSON

### GetAuthorAndNameTrack
Читает файл `all_tracks` (формат: `Artist — Track`)
и разделяет данные на: `author.txt` и `name.txt`

### SpotifyTokenRequest
Реализует Authorization Code Flow:
- отправляет POST-запрос к `https://accounts.spotify.com/api/token`
- получает access_token

### FinalLikedTrackAdd
Основная логика:
- читает `author.txt` и `name.txt`
- делает запрос к `GET /v1/search`
- получает `track_id`
- добавляет трек в Liked Songs через:
  `PUT /v1/me/tracks?ids=<track_id>`

---

## Технологии

- Java
- HttpURLConnection
- org.json
- Spotify Web API
- VK JSON parsing

---

## Как работает поток данных

VK JSON ->  all_tracks -> author.txt + name.txt -> Spotify Search API -> track_id -> Spotify Library API (Liked Songs)

---

## Требования

- Java 11+
- Spotify Developer Account
- Права `user-library-modify`

---

## Ограничения

- Используется limit=1 при поиске (может вернуть ремикс/другую версию)
- Отсутствует автоматическое обновление токена
- Требуется ручная настройка access_token

---

## Возможные улучшения

- Добавить автоматическое обновление токена (refresh_token)
- Реализовать создание плейлиста вместо Liked Songs
- Добавить обработку "не найдено"
- Добавить нормализацию названий (feat., remastered и т.д.)
- Перейти на OAuth библиотеку вместо ручной реализации

---

## Автор

Михаил
