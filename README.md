# ManageMyWallet

An Android application for tracking personal finances by automatically parsing transaction SMS messages from Indian banks. Privacy-focused with complete offline operation - no internet permission required.

## Features

- **Automatic SMS Parsing**: Intercepts and parses transaction SMS from 8 major Indian banks
- **Intelligent Categorization**: 17 default categories with merchant learning system
- **Spending Analytics**: Visual charts and spending trends
- **Configurable Alerts**: Spending limit notifications
- **Strong Security**: SQLCipher encryption, biometric auth, root detection
- **Complete Privacy**: No internet access, no cloud backup, no tracking

## Supported Banks

HDFC, ICICI, SBI, Axis, Kotak Mahindra, Yes Bank, IDFC First, IndusInd

## Prerequisites

Before building the project, ensure you have the following installed:

### 1. Java Development Kit (JDK) 17 or higher
```bash
# Using Homebrew (recommended for macOS)
brew install openjdk@21

# Or download from https://jdk.java.net/
```

### 2. Android SDK
```bash
# Install Android command line tools via Homebrew
brew install --cask android-commandlinetools

# Then install required SDK components
export JAVA_HOME=/opt/homebrew/opt/openjdk@21
export ANDROID_SDK_ROOT=/opt/homebrew/share/android-sdk
mkdir -p $ANDROID_SDK_ROOT
sdkmanager --sdk_root=$ANDROID_SDK_ROOT "platform-tools" "platforms;android-34" "build-tools;34.0.0"
sdkmanager --sdk_root=$ANDROID_SDK_ROOT --licenses
```

### 3. Gradle
The project uses Gradle wrapper (`./gradlew`), so no separate Gradle installation is needed.

## Environment Setup

Add the following to your shell profile (`~/.zshrc` for zsh or `~/.bash_profile` for bash):

```bash
# Java
export JAVA_HOME=/opt/homebrew/opt/openjdk@21
export PATH="$JAVA_HOME/bin:$PATH"

# Android SDK
export ANDROID_HOME=/opt/homebrew/share/android-sdk
export ANDROID_SDK_ROOT=/opt/homebrew/share/android-sdk
export PATH="$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH"
```

Then reload your shell:
```bash
source ~/.zshrc  # or source ~/.bash_profile
```

## Building the Application

### Debug Build (for testing)
```bash
./gradlew assembleDebug
```
The APK will be generated at: `app/build/outputs/apk/debug/app-debug.apk`

### Release Build (for distribution)
```bash
./gradlew assembleRelease
```
The APK will be generated at: `app/build/outputs/apk/release/app-release-unsigned.apk`

## Installing on Device

1. Enable "Developer Options" and "USB Debugging" on your Android device
2. Connect device via USB
3. Install the APK:
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Running Tests

```bash
# Unit tests
./gradlew test

# Instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest
```

## Project Structure

```
app/src/main/java/com/managemywallet/
├── WalletApplication.kt          # App initialization
├── AppDatabase.kt                # Room DB with SQLCipher
├── data/
│   ├── dao/                      # Database DAOs
│   ├── entity/                   # Database entities
│   └── repository/               # Data repositories
├── ui/
│   ├── MainActivity.kt           # Main activity
│   ├── dashboard/                # Dashboard screen
│   ├── transactions/             # Transaction screens
│   ├── analytics/                # Analytics screen
│   └── settings/                 # Settings screen
├── sms/                          # SMS parsing
├── security/                     # Security modules
└── alert/                        # Alert system
```

## Tech Stack

- **Language**: Kotlin 1.9.20
- **Platform**: Android (minSdk 23, targetSdk 34)
- **Architecture**: MVVM
- **Database**: Room + SQLCipher (AES-256 encryption)
- **UI**: Material 3, ViewBinding
- **Charts**: MPAndroidChart
- **Build**: Gradle 8.2.0

## Security Features

- AES-256 database encryption via SQLCipher
- Android Keystore for key management (hardware-backed)
- Biometric authentication (fingerprint/face)
- Screen security (prevents screenshots)
- Inactivity auto-lock
- Root/emulator detection
- Secure data wiping (3-pass overwrite)
- No internet permission - complete offline operation

## License

MIT License - see LICENSE file for details

## Documentation

Additional documentation is available in the `docs/` directory:
- [Architecture Overview](docs/architecture.md)
- [UI Wireframes](docs/wireframes.md)
- [Supported SMS Formats](docs/indian-bank-sms-formats.md)
