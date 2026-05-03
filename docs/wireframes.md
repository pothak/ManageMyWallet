# ManageMyWallet - Screen Wireframes

## Screen 1: Biometric Auth

Full screen, primary blue (#2563EB) background:
- Center: Wallet icon (large)
- Below icon: "ManageMyWallet"
- Biometric prompt overlay: "Unlock Wallet / Use fingerprint or face"
- Bottom: "Cancel" button

---

## Screen 2: Dashboard

```
┌─────────────────────────────┐
│ Dashboard                   │
│                             │
│ ┌─────────────────────────┐ │
│ │ NET THIS MONTH          │ │
│ │ ₹24,500.00              │ │
│ │ Income ₹45K | Expense   │ │
│ │            ₹20.5K       │ │
│ └─────────────────────────┘ │
│  (Blue card, rounded)       │
│                             │
│ Recent Transactions         │
│ ┌─────────────────────────┐ │
│ │ ┃ Swiggy     -₹450      │ │
│ └─────────────────────────┘ │
│ ┌─────────────────────────┐ │
│ │ ┃ Amazon    -₹2,499     │ │
│ └─────────────────────────┘ │
│                             │
│ By Category                 │
│ ┌─────────────────────────┐ │
│ │ ┃ Food        ₹3,200    │ │
│ │ ┃ Shopping    ₹8,500    │ │
│ └─────────────────────────┘ │
├─────────────────────────────┤
│ 📊  │  📋  │  📈  │  ⚙️   │
└─────────────────────────────┘
```

---

## Screen 3: Transactions List

```
┌─────────────────────────────┐
│ Transactions                │
│ ┌─────────────────────────┐ │
│ │ 🔍 Search...            │ │
│ └─────────────────────────┘ │
│                             │
│ ┌─────────────────────────┐ │
│ │ ┃ Swiggy     -₹450      │ │
│ └─────────────────────────┘ │
│ ┌─────────────────────────┐ │
│ │ ┃ Amazon    -₹2,499     │ │
│ └─────────────────────────┘ │
│ (Scrollable)                │
│                             │
│ [Empty state if no data]    │
│   No transactions yet       │
│   SMS will appear here      │
└─────────────────────────────┘
```

---

## Screen 4: Transaction Detail

```
┌─────────────────────────────┐
│ ← Transaction Details       │
│                             │
│ ┌─────────────────────────┐ │
│ │    -₹2,499.00           │ │
│ │    Amazon               │ │
│ └─────────────────────────┘ │
│                             │
│ ┌─────────────────────────┐ │
│ │ Category: Shopping      │ │
│ │ Date: 15 Jan, 3:45 PM   │ │
│ │ Bank: HDFC Bank         │ │
│ │ Ref: A1234567890        │ │
│ └─────────────────────────┘ │
│                             │
│ ┌─────────────────────────┐ │
│ │  [ Delete Transaction ] │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

---

## Screen 5: Analytics

```
┌─────────────────────────────┐
│ Analytics                   │
│                             │
│ ┌─────────────────────────┐ │
│ │   [Donut Pie Chart]     │ │
│ │   Category breakdown    │ │
│ └─────────────────────────┘ │
│                             │
│ ┌─────────────────────────┐ │
│ │   [Bar Chart]           │ │
│ │   Daily spending trend  │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

---

## Screen 6: Settings

```
┌─────────────────────────────┐
│ Settings                    │
│                             │
│ Security                    │
│ ┌─────────────────────────┐ │
│ │ Biometric Unlock   [✓]  │ │
│ │ Auto-Lock Timeout  ›    │ │
│ │ Security Details   ›    │ │
│ └─────────────────────────┘ │
│                             │
│ Data                        │
│ ┌─────────────────────────┐ │
│ │ Export Data        ›    │ │
│ │ Wipe All Data      ›    │ │
│ └─────────────────────────┘ │
│                             │
│ Privacy                     │
│ ┌─────────────────────────┐ │
│ │ Your Data Stays Private │ │
│ │ • Stored on device only │ │
│ │ • Encrypted             │ │
│ │ • No internet           │ │
│ │ • No tracking           │ │
│ │ • Open source           │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

---

## Design Tokens

| Token | Value |
|-------|-------|
| Primary | #2563EB |
| Primary Light | #60A5FA |
| Primary Surface | #EFF6FF |
| Background | #F8FAFC |
| Surface | #FFFFFF |
| Income | #10B981 |
| Expense | #EF4444 |
| Warning | #F59E0B |
| Text Primary | #0F172A |
| Text Medium | #475569 |
| Text Light | #94A3B8 |
| Divider | #E2E8F0 |

| Type | Size | Weight |
|------|------|--------|
| Headline | 28sp | Bold |
| Title | 20sp | Bold |
| Body | 16sp | Regular |
| Caption | 12sp | Regular |

| Spacing | Value |
|---------|-------|
| Page padding | 16dp |
| Card margin | 8dp |
| Card padding | 16dp |
| Item gap | 8dp |
| Section gap | 24dp |
| Card radius | 16dp |
| Input radius | 12dp |
