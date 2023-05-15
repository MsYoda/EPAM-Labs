# EPAM-Labs

Проект представляет собой небольшой REST сервис, написанный с использованием Java/Spring.
Функционал сервиса:
1) Принимает на вход число и возвращает 2 случайных числа, одно из которых больше заданного, а другое меньше.
2) Осуществляет валидацию входных параметров (входное число должно быть не больше 100 и не меньшe 0).
3) Сохраняет результаты вычислений в in-memory cache.
4) Имеет синхронизированный счётчик обращений к сервису, доступный по своему endpoint.
5) С помощью Spring Data сохраняет результаты вычислений в базу данных (без неё не запускается).
