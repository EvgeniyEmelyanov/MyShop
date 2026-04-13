# MyShop Android App

Pet project интернет-магазина, разработанный с упором на архитектуру и разделение ответственности между слоями.

## 📱 Функционал

- Каталог товаров
- Карточка товара
- Корзина (добавление, изменение количества)
- Избранное
- Массовое добавление товаров в корзину

## 🏗 Архитектура

Проект построен с разделением на слои:

- **UI** — Fragment + ViewModel  
- **Domain** — UseCase + бизнес-логика  
- **Data** — Repository + Room  

Основные принципы:
- MVVM
- Clean Architecture
- SOLID

## ⚙️ Технологии

- Kotlin
- Android SDK
- Fragments
- Navigation Component
- ViewBinding
- Coroutines
- Hilt (Dependency Injection)
- Room (локальная база данных)
- Retrofit (в процессе интеграции)

## 🧠 Что реализовано с точки зрения архитектуры

- UseCase слой для бизнес-логики  
- Repository как единая точка доступа к данным  
- Разделение моделей (data / domain)  
- Внедрение DI через Hilt (замена ручного AppGraph)  
- Переход на Coroutines  

## 🚀 Как запустить проект

1. Клонировать репозиторий:
```bash
git clone https://github.com/EvgeniyEmelyanov/MyShop.git
