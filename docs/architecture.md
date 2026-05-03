# ManageMyWallet - Architecture Diagrams

## 1. High-Level System Architecture

```
┌─────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                     ManageMyWallet Android App                                          │
├─────────────────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                                         │
│  ┌───────────────────────────────────────────────────────────────────────────────────────────────────┐  │
│  │                                         UI Layer (View)                                           │  │
│  │  ┌──────────────┐  ┌────────────────┐  ┌────────────────┐  ┌────────────────┐  ┌───────────────┐ │  │
│  │  │  Dashboard   │  │  Transactions  │  │  Alerts/       │  │  Categories/   │  │   Settings/   │ │  │
│  │  │  Screen      │  │  List/Detail   │  │  Rules         │  │   Analytics    │  │   Security    │ │  │
│  │  └──────┬───────┘  └───────┬────────┘  └───────┬────────┘  └───────┬────────┘  └──────┬────────┘ │  │
│  │         │                  │                    │                  │                    │          │  │
│  └─────────┼──────────────────┼────────────────────┼──────────────────┼────────────────────┼──────────┘  │
│            │                  │                    │                  │                    │             │
│  ┌─────────▼──────────────────▼────────────────────▼──────────────────▼────────────────────▼──────────┐  │
│  │                                    ViewModel Layer                                                 │  │
│  │  ┌──────────────┐  ┌────────────────┐  ┌────────────────┐  ┌────────────────┐  ┌───────────────┐ │  │
│  │  │ Dashboard    │  │ Transaction    │  │ Alert          │  │ Analytics      │  │ Settings      │ │  │
│  │  │ ViewModel    │  │ ViewModel      │  │ ViewModel      │  │ ViewModel      │  │ ViewModel     │ │  │
│  │  └──────┬───────┘  └───────┬────────┘  └───────┬────────┘  └───────┬────────┘  └──────┬────────┘ │  │
│  │         │                  │                    │                  │                    │          │  │
│  └─────────┼──────────────────┼────────────────────┼──────────────────┼────────────────────┼──────────┘  │
│            │                  │                    │                  │                    │             │
│  ┌─────────▼──────────────────▼────────────────────▼──────────────────▼────────────────────▼──────────┐  │
│  │                                    Repository Layer                                                │  │
│  │  ┌──────────────────────────────────────┐  ┌─────────────────────────────────────────────────────┐ │  │
│  │  │      TransactionRepository           │  │              AlertRepository                        │ │  │
│  │  │  - CRUD operations                   │  │  - Manage alert rules                               │ │  │
│  │  │  - Query by date/category/merchant   │  │  - Check thresholds against spending                │ │  │
│  │  │  - Aggregate spending data           │  │  - Trigger notifications                            │ │  │
│  │  └──────────────────┬───────────────────┘  └──────────────────┬──────────────────────────────────┘ │  │
│  │                     │                                        │                                     │  │
│  └─────────────────────┼────────────────────────────────────────┼─────────────────────────────────────┘  │
│                        │                                        │                                         │
│  ┌─────────────────────▼────────────────────────────────────────▼─────────────────────────────────────┐  │
│  │                                    Data Layer                                                      │  │
│  │  ┌─────────────────────────────────┐  ┌──────────────────────────────────────────────────────────┐ │  │
│  │  │      Room Database (SQLCipher)  │  │              Security Module                             │ │  │
│  │  │                                 │  │                                                          │ │  │
│  │  │  ┌───────────────────────────┐  │  │  ┌────────────────────────────────────────────────────┐  │ │  │
│  │  │  │  Transactions Table       │  │  │  │  Android Keystore (AES-256-GCM)                    │  │ │  │
│  │  │  │  AlertRules Table         │  │  │  │  - DB encryption key                               │  │ │  │
│  │  │  │                           │  │  │  │  - Shared secrets                                  │  │ │  │
│  │  │  │  DAOs:                    │  │  │  │  - Biometric auth                                  │  │ │  │
│  │  │  │  - TransactionDao         │  │  │  │  - Screen security                                 │  │ │  │
│  │  │  │  - AlertRuleDao           │  │  │  │  - Data wiping                                     │  │ │  │
│  │  │  └───────────────────────────┘  │  │  └────────────────────────────────────────────────────┘  │ │  │
│  │  └─────────────────────────────────┘  └──────────────────────────────────────────────────────────┘ │  │
│  └──────────────────────────────────────────────────────────────────────────────────────────────────────┘  │
│                                                                                                             │
│  ┌───────────────────────────────────────────────────────────────────────────────────────────────────────┐ │
│  │                                    External Interfaces                                                │ │
│  │  ┌──────────────────────────┐  ┌─────────────────────────────────┐  ┌───────────────────────────────┐ │ │
│  │  │   SMS BroadcastReceiver  │  │   NotificationManager           │  │   BiometricPrompt             │ │ │
│  │  │   - Receives SMS         │  │   - Spending alerts             │  │   - Fingerprint/Face unlock   │ │ │
│  │  │   - Parses transactions  │  │   - Threshold warnings          │  │   - App entry gate            │ │ │
│  │  │   - Stores to DB         │  │   - Daily/weekly summaries      │  │                               │ │ │
│  │  └──────────────────────────┘  └─────────────────────────────────┘  └───────────────────────────────┘ │ │
│  └───────────────────────────────────────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────────────────────────────────────┘
```

## 2. Data Flow - SMS to Dashboard

```
┌──────────────┐     ┌──────────────────┐     ┌────────────────┐     ┌──────────────────┐     ┌──────────────┐
│   SMS        │────▶│   SmsReceiver    │────▶│   SmsParser    │────▶│   Transaction    │────▶│   Room DB    │
│   Received   │     │   (Broadcast)    │     │   (Regex/ML)   │     │   Repository     │     │   (Encrypted)│
└──────────────┘     └──────────────────┘     └────────────────┘     └──────────────────┘     └──────┬───────┘
                                                                                                      │
                                                                                                      │ LiveData
                                                                                                      ▼
                                                                                             ┌──────────────────┐
                                                                                             │   Dashboard      │
                                                                                             │   ViewModel      │
                                                                                             └──────┬───────────┘
                                                                                                    │
                                                                                                    ▼
                                                                                             ┌──────────────────┐
                                                                                             │   Dashboard      │
                                                                                             │   Screen         │
                                                                                             └──────────────────┘
                                                                                                    │
                                                                                                    ▼
                                                                                             ┌──────────────────┐
                                                                                             │   Alert Check    │
                                                                                             │   (Thresholds)   │
                                                                                             └──────┬───────────┘
                                                                                                    │ If exceeded
                                                                                                    ▼
                                                                                             ┌──────────────────┐
                                                                                             │   Notification   │
                                                                                             │   Pushed         │
                                                                                             └──────────────────┘
```

## 3. Security Architecture

```
┌─────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                    Security Layers                                                      │
├─────────────────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                                         │
│  Layer 1: Entry Gate                                                                                    │
│  ┌───────────────────────────────────────────────────────────────────────────────────────────────────┐  │
│  │  BiometricPrompt (Fingerprint/Face) → Unlocks app session → Session expires after X minutes       │  │
│  └───────────────────────────────────────────────────────────────────────────────────────────────────┘  │
│                                                                                                         │
│  Layer 2: Data at Rest                                                                                  │
│  ┌───────────────────────────────────────────────────────────────────────────────────────────────────┐  │
│  │  SQLite Database → SQLCipher (AES-256) → Key from Android Keystore → Encrypted on disk            │  │
│  │  SharedPreferences → EncryptedSharedPreferences → Key from Android Keystore                       │  │
│  └───────────────────────────────────────────────────────────────────────────────────────────────────┘  │
│                                                                                                         │
│  Layer 3: Runtime Protection                                                                            │
│  ┌───────────────────────────────────────────────────────────────────────────────────────────────────┐  │
│  │  FLAG_SECURE → Blocks screenshots, screen recording, recent apps thumbnail                        │  │
│  │  Memory → Sensitive data cleared from memory after use                                            │  │
│  │  Root Detection → Warns/blocks on rooted devices (optional)                                       │  │
│  └───────────────────────────────────────────────────────────────────────────────────────────────────┘  │
│                                                                                                         │
│  Layer 4: Network Isolation                                                                             │
│  ┌───────────────────────────────────────────────────────────────────────────────────────────────────┐  │
│  │  NO INTERNET PERMISSION → App cannot make any network calls                                       │  │
│  │  NO CLOUD BACKUP → android:allowBackup=false + dataExtractionRules exclude all data               │  │
│  │  NO ANALYTICS → No Firebase, no crashlytics, no tracking                                          │  │
│  └───────────────────────────────────────────────────────────────────────────────────────────────────┘  │
│                                                                                                         │
│  Layer 5: Key Management                                                                                │
│  ┌───────────────────────────────────────────────────────────────────────────────────────────────────┐  │
│  │  Android Keystore → Hardware-backed (TEE/StrongBox) → Keys never leave secure hardware            │  │
│  │  User Authentication Required → Key unusable without biometric                                    │  │
│  │  Secure Wipe → On logout: delete DB + delete keys + overwrite files 3x                            │  │
│  └───────────────────────────────────────────────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────────────────────────────────────────────┘
```

## 4. Screen Flow / Navigation

```
┌──────────────────────┐
│   Splash Screen      │
│   (Biometric Auth)   │
└──────────┬───────────┘
           │ Auth Success
           ▼
┌─────────────────────────────────────────────────────────────────────────────────────┐
│                              Main Activity (Bottom Navigation)                      │
├──────────────────────────┬──────────────────────────┬──────────────────────────────┤
│                          │                          │                              │
│  ┌────────────────────┐  │  ┌────────────────────┐  │  ┌────────────────────────┐  │
│  │  DASHBOARD         │  │  │  TRANSACTIONS      │  │  │  SETTINGS            │  │
│  │                    │  │  │                    │  │  │                      │  │
│  │  - Monthly spend   │  │  │  - List view       │  │  │  - Alert rules       │  │
│  │  - Recent txns     │  │  │  - Search/Filter   │  │  │  - Categories        │  │
│  │  - Category chart  │  │  │  - Detail view     │  │  │  - Security settings │  │
│  │  - Quick actions   │  │  │  - Add manual      │  │  │  - Export data       │  │
│  │                    │  │  │                    │  │  │  - Wipe all data     │  │
│  └────────────────────┘  │  └────────────────────┘  │  └────────────────────────┘  │
│                          │                          │                              │
└──────────────────────────┴──────────────────────────┴──────────────────────────────┘
           │                           │                           │
           │                           │                           │
           ▼                           ▼                           ▼
┌──────────────────────┐    ┌──────────────────────┐    ┌──────────────────────┐
│  Transaction Detail  │    │  Add Transaction     │    │  Alert Rule Config   │
│  - Full info         │    │  - Manual entry      │    │  - Type/Amount       │
│  - SMS raw view      │    │  - Category select   │    │  - Time period       │
│  - Flag/Notes        │    │  - Date picker       │    │  - Toggle on/off     │
└──────────────────────┘    └──────────────────────┘    └──────────────────────┘

           ┌──────────────────────┐
           │  Analytics Screen    │
           │  - Pie chart         │
           │  - Bar chart         │
           │  - Trend line        │
           │  - Date range filter │
           └──────────────────────┘
```

## 5. Package Structure

```
com.managemywallet/
├── WalletApplication.kt              # App lifecycle, init
├── AppDatabase.kt                    # Room DB setup
│
├── security/                         # All security-related
│   ├── AndroidKeystoreManager.kt     # Key generation/storage
│   ├── BiometricAuthManager.kt       # Fingerprint/Face auth
│   ├── ScreenSecurityHelper.kt       # FLAG_SECURE
│   └── DataWiper.kt                  # Secure deletion
│
├── sms/                              # SMS handling
│   ├── TransactionSmsReceiver.kt     # Broadcast receiver
│   └── SmsParser.kt                  # Regex parsing logic
│
├── data/                             # Data layer
│   ├── dao/
│   │   ├── TransactionDao.kt
│   │   └── AlertRuleDao.kt
│   ├── entity/
│   │   ├── Transaction.kt
│   │   └── AlertRule.kt
│   └── repository/
│       ├── TransactionRepository.kt
│       └── AlertRepository.kt
│
├── ui/                               # UI layer
│   ├── MainActivity.kt
│   ├── dashboard/
│   │   ├── DashboardFragment.kt
│   │   ├── DashboardViewModel.kt
│   │   └── adapter/
│   ├── transactions/
│   │   ├── TransactionListFragment.kt
│   │   ├── TransactionDetailFragment.kt
│   │   ├── AddTransactionFragment.kt
│   │   ├── TransactionViewModel.kt
│   │   └── adapter/
│   ├── alerts/
│   │   ├── AlertRulesFragment.kt
│   │   ├── AlertRuleConfigFragment.kt
│   │   └── AlertViewModel.kt
│   ├── analytics/
│   │   ├── AnalyticsFragment.kt
│   │   └── AnalyticsViewModel.kt
│   ├── settings/
│   │   ├── SettingsFragment.kt
│   │   └── SettingsViewModel.kt
│   └── auth/
│       └── BiometricAuthFragment.kt
│
├── alert/                            # Notification/alert system
│   ├── SpendingAlertChecker.kt       # Threshold evaluation
│   └── NotificationHelper.kt         # Push notifications
│
├── utils/                            # Shared utilities
│   ├── DateUtils.kt
│   ├── CurrencyUtils.kt
│   └── Constants.kt                  # No secrets, only config
│
└── di/                               # Dependency injection
    └── AppModule.kt
```

## 6. Alert System Flow

```
┌──────────────────┐     ┌──────────────────┐     ┌────────────────────┐
│  New Transaction │────▶│ AlertRepository  │────▶│ SpendingAlert      │
│  Added to DB     │     │ (get active rules)│    │ Checker            │
└──────────────────┘     └──────────────────┘     └──────────┬─────────┘
                                                              │
                                              ┌───────────────┼───────────────┐
                                              │               │               │
                                              ▼               ▼               ▼
                                    ┌────────────────┐ ┌──────────────┐ ┌──────────────┐
                                    │ Single Txn     │ │ Daily Limit  │ │ Category     │
                                    │ > Threshold?   │ │ Exceeded?    │ │ Limit Hit?   │
                                    └───────┬────────┘ └──────┬───────┘ └──────┬───────┘
                                            │                 │                │
                                            ▼                 ▼                ▼
                                    ┌─────────────────────────────────────────────────┐
                                    │              Fire Local Notification            │
                                    │  - "Spent ₹5000 at Amazon - exceeds ₹3000 limit"│
                                    │  - Vibration + Sound + LED                      │
                                    └─────────────────────────────────────────────────┘
```
