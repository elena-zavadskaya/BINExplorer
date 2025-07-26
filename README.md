## Обзор
BIN Explorer - это приложение для поиска информации о банковских картах по их BIN (Bank Identification Number). Приложение позволяет:

- Получать детальную информацию о карте по её первым 6 цифрам
- Просматривать ранее выполненные запросы
- Очищать историю поиска

## Скриншоты приложения
https://screenshots/main_screen.png
https://screenshots/history_screen.png
https://screenshots/card_info.png

## Технический стек
- Язык: Kotlin
- Архитектура: Clean Architecture (Data, Domain, Presentation)
- Библиотеки:
  Jetpack Compose (UI)
  Room (база данных)
  Retrofit (сетевые запросы)
  Hilt (внедрение зависимостей)
  Kotlin Coroutines (асинхронные операции)
  Moshi (парсинг JSON)

Структура проекта
```text
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/binexplorer/
│   │   │   ├── data/                # Слой данных
│   │   │   │   ├── local/           # Локальное хранилище (Room)
│   │   │   │   ├── model/           # Модели данных
│   │   │   │   ├── remote/          # Сетевые запросы (Retrofit)
│   │   │   │   ├── repository/      # Репозитории
│   │   │   ├── di/                  # Внедрение зависимостей (Hilt)
│   │   │   ├── domain/              # Бизнес-логика
│   │   │   │   ├── model/           # Доменные модели
│   │   │   │   ├── repository/      # Интерфейсы репозиториев
│   │   │   │   ├── interactor/      # Use cases
|   |   |   |   ├── util/            # Утилитарные компоненты
│   │   │   ├── presentation/        # UI слой
│   │   │   │   ├── components/      # Компоненты UI
│   │   │   │   ├── navigation/      # Навигация
│   │   │   │   ├── screens/         # Экранны
│   │   │   │   ├── ui/              # Тема приложения
│   │   │   │   ├── viewmodels/      # ViewModel
│   │   │   ├── BinExplorerApplication.kt # Точка входа
│   │   ├── res/                     # Ресурсы
│   │   ├── AndroidManifest.xml
```
## Ключевые компоненты
1. Слой данных (Data)
- BinHistoryDao: Интерфейс для работы с историей запросов в Room
- BinHistoryEntity: Сущность базы данных для истории запросов
- BinApiService: Интерфейс для запросов к BIN API
- BinRepositoryImpl: Реализация репозитория для работы с API

2. Доменный слой (Domain)
- BinInteractor: Логика работы с информацией о картах
- HistoryInteractor: Логика работы с историей запросов
- BinInfo, BinHistoryItem: Доменные модели

3. Презентационный слой (Presentation)
- MainActivity: Главная активность с навигацией
- BinLookupScreen: Экран поиска информации по BIN
- HistoryScreen: Экран истории запросов
- BinLookupViewModel: ViewModel для экрана поиска
- HistoryViewModel: ViewModel для экрана истории

4. UI компоненты
- BottomNavigationBar: Нижняя навигационная панель
- HistoryItemCard: Карточка элемента истории
- BinInfoCard: Карточка с информацией о карте

## Настройка проекта
### Зависимости (build.gradle)
```gradle
dependencies {
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.7.0'
    implementation 'androidx.activity:activity-compose:1.8.2'
    
    // Compose
    implementation platform('androidx.compose:compose-bom:2024.02.01')
    implementation 'androidx.compose.ui:ui'
    implementation 'androidx.compose.ui:ui-graphics'
    implementation 'androidx.compose.ui:ui-tooling-preview'
    implementation 'androidx.compose.material3:material3'
    implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0"
    implementation "androidx.navigation:navigation-compose:2.7.7"
    
    // Room
    implementation "androidx.room:room-runtime:2.6.1"
    implementation "androidx.room:room-ktx:2.6.1"
    kapt "androidx.room:room-compiler:2.6.1"
    
    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-moshi:2.9.0'
    implementation 'com.squareup.moshi:moshi-kotlin:1.15.0'
    kapt 'com.squareup.moshi:moshi-kotlin-codegen:1.15.0'
    
    // Hilt
    implementation "com.google.dagger:hilt-android:2.50"
    kapt "com.google.dagger:hilt-compiler:2.50"
    implementation "androidx.hilt:hilt-navigation-compose:1.1.0"
    
    // OkHttp
    implementation 'com.squareup.okhttp3:okhttp:4.12.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.12.0'
}
```
## Использование API
Приложение использует открытое API для получения информации о картах:
- Базовый URL: https://lookup.binlist.net/
- Эндпоинт: GET /{bin}
- Пример запроса: GET /45717360

## Особенности реализации
### Сохранение истории
```kotlin
class HistoryRepositoryImpl @Inject constructor(
    private val binHistoryDao: BinHistoryDao
) : HistoryRepository {
    
    override suspend fun saveHistoryItem(bin: String, binInfo: BinHistoryItem) {
        binHistoryDao.insert(
            BinHistoryEntity(
                bin = bin,
                scheme = binInfo.scheme,
                type = binInfo.type,
                brand = binInfo.brand,
                bankName = binInfo.bankName,
                countryName = binInfo.countryName
            )
        )
    }
    
    override fun getAllHistory(): Flow<List<BinHistoryItem>> {
        return binHistoryDao.getAll().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
}
```
### Навигация
```kotlin
NavHost(
    navController = navController,
    startDestination = ScreenRoute.SEARCH.route
) {
    composable(ScreenRoute.SEARCH.route) {
        BinLookupScreen(/* параметры */)
    }
    composable(ScreenRoute.HISTORY.route) {
        HistoryScreen(/* параметры */)
    }
}
```
### Цветовая схема
```kotlin
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF3498DB),
    secondary = Color(0xFF48C9B0),
    tertiary = Color(0xFF76D7C4),
    background = Color(0xFFF8F9F9),
    surface = Color.White,
    onBackground = Color(0xFF2C3E50),
    onSurface = Color(0xFF34495E),
    surfaceVariant = Color(0xFFE0E0E0)
)
```
## Сборка и запуск
1. Клонируйте репозиторий
2. Откройте проект в Android Studio
3. Дождитесь завершения синхронизации Gradle
4. Запустите на эмуляторе или физическом устройстве
