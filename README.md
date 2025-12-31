# InDriver Bot - LSPosed Module

Auto-accept orders and bypass payments for inDriver app.

## Features
- Auto-accept orders (7000₸+)
- Free announcements (bypass 2030₸ payment)
- 25 free calls per day
- Stealth mode

## Build
```bash
# 1. Создаём недостающие директории
mkdir -p app/src/main/java/com/indriverbot/hooks
mkdir -p app/src/main/assets
mkdir -p gradle/wrapper

# 2. Создаём gradle wrapper файлы
cat > gradle/wrapper/gradle-wrapper.properties << 'EOF'
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.5-all.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
