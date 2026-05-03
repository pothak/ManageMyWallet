# Indian Bank SMS Transaction Formats

A structured reference guide covering common SMS message formats from major Indian banks for 10 transaction types.

## Target Banks
HDFC Bank, ICICI Bank, SBI, Axis Bank, Kotak Mahindra Bank, Yes Bank, IDFC FIRST Bank, IndusInd Bank

## Transaction Types
1. UPI Transactions
2. Credit Card Spends
3. Debit Card / POS Transactions
4. Net Banking (NEFT/IMPS/RTGS)
5. ATM Transactions
6. Online Purchases
7. Recurring / NACH / Autopay
8. Refunds / Credits
9. EMI Conversions
10. Failed / Reversed Transactions

---

## 1. UPI Transactions

### HDFC Bank
**Examples:**
- `Rs.1,500.00 debited from a/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 towards UPI transfer to MERCHANT NAME via UPI. Ref: HD12345678901234. Bal: Rs.25,432.10`
- `INR 500.00 credited to a/c XXXXXXXXXX1234 on 15-01-2026 at 14:30 from JOHN DOE via UPI. Ref: HD12345678901234`

**Keywords:** `UPI`, `UPI transfer`, `via UPI`, `debited`, `credited`
**Amount Formats:** `Rs.X,XXX.XX`, `INR XXX.XX`
**Reference IDs:** `Ref: HD` + 14 digits, or `Ref: ` + 12-16 alphanumeric
**Masking:** `a/c XXXXXXXXXX1234` (10 X + last 4 digits)

### ICICI Bank
**Examples:**
- `Your Account XX1234 has been debited by INR 2,500.00 on 15-01-26 14:30:25 IST towards UPI-JOHN DOE via UPI. Ref No: 000012345678. Avl Bal: INR 15,230.45`
- `Your A/c XX1234 is credited with Rs.1,000.00 on 15-JAN-26 through NEFT-IMPS from JANE DOE. Ref: 000012345678`

**Keywords:** `UPI-`, `via UPI`, `debited`, `credited`, `A/c`, `Account`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref No: ` + 12 digits (numeric), `Ref: ` + alphanumeric
**Masking:** `XX1234`, `A/c XX1234`

### SBI
**Examples:**
- `Rs 1,500.00 debited from A/c XXXXXX1234 on 15-01-2026 at 14:30:25 through UPI transfer to MERCHANT. Ref: SBI123456789. Balance: INR 45,678.90`
- `Rs 500.00 credited to A/c XXXXXX1234 on 15-JAN-2026 14:30 via UPI from RAVI KUMAR. Ref: SBI123456789`

**Keywords:** `UPI`, `UPI transfer`, `via UPI`, `debited`, `credited`, `A/c`
**Amount Formats:** `Rs X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: SBI` + 9-12 digits
**Masking:** `A/c XXXXXX1234` (6 X + last 4)

### Axis Bank
**Examples:**
- `Dear Customer, INR 3,200.00 debited from A/c XXXXXXXXXX5678 on 15-Jan-2026 14:30:25 via UPI to MERCHANT NAME. UPI Ref: 4265XXXXXXXXXXX1234. Bal: INR 18,765.43`
- `Rs.500.00 credited to your Axis Bank A/c XXXXXXXX5678 via UPI on 15/01/2026 14:30:25. Ref: 4265XXXXXXXXXXX1234`

**Keywords:** `UPI`, `via UPI`, `UPI Ref`, `debited`, `credited`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `UPI Ref: ` + 12 digits or `Ref: ` + 4 digits + Xs + digits
**Masking:** `A/c XXXXXXXXXX5678`, `XXXXXXXX5678` (10 X or 8 X + last 4)

### Kotak Mahindra Bank
**Examples:**
- `Rs.2,500.00 debited from Kotak A/c XXXXXX1234 on 15-01-2026 at 14:30:25 for UPI payment to MERCHANT. Ref No: KTK12345678901. Bal: Rs.12,345.67`
- `Rs.1,000.00 credited to your Kotak A/c XXXXXX1234 on 15/01/26 via UPI from PRIYA SINGH. Ref: KTK12345678901`

**Keywords:** `Kotak`, `UPI`, `UPI payment`, `via UPI`, `debited`, `credited`
**Amount Formats:** `Rs.X,XXX.XX`
**Reference IDs:** `Ref No: KTK` + 11-14 digits, `Ref: KTK` + digits
**Masking:** `Kotak A/c XXXXXX1234` (6 X + last 4)

### Yes Bank
**Examples:**
- `Rs.1,800.00 debited from A/c XXXXXX1234 on 15-01-2026 14:30:25 via UPI to MERCHANT NAME. UPI Ref: 412XXXXXXXXXXX5678. Bal: Rs.8,765.43`
- `INR 500.00 credited to A/c XXXXXX1234 via UPI on 15/01/26 from AMIT PATEL. Ref: YES12345678901`

**Keywords:** `UPI`, `via UPI`, `UPI Ref`, `debited`, `credited`
**Amount Formats:** `Rs.X,XXX.XX`, `INR XXX.XX`
**Reference IDs:** `UPI Ref: ` + 4 digits + Xs + digits, `Ref: YES` + digits
**Masking:** `A/c XXXXXX1234` (6 X + last 4)

### IDFC FIRST Bank
**Examples:**
- `Dear Customer, Rs.3,500.00 debited from A/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 via UPI to MERCHANT. Ref: IDFC123456789. Bal: Rs.22,456.78`
- `Rs.1,200.00 credited to your IDFC FIRST Bank A/c XXXXXXXXXX1234 via UPI from NEHA GUPTA on 15/01/26. Ref: IDFC123456789`

**Keywords:** `IDFC`, `UPI`, `via UPI`, `debited`, `credited`
**Amount Formats:** `Rs.X,XXX.XX`
**Reference IDs:** `Ref: IDFC` + 9-12 digits
**Masking:** `A/c XXXXXXXXXX1234` (10 X + last 4)

### IndusInd Bank
**Examples:**
- `Dear Customer, INR 2,100.00 debited from A/c XXXXXX1234 on 15-01-2026 at 14:30:25 via UPI to MERCHANT NAME. Ref: INDUS12345678901. Bal: INR 9,876.54`
- `Rs.750.00 credited to your IndusInd Bank A/c XXXXXX1234 via UPI on 15/01/26 from RAJESH SHARMA. Ref: INDUS12345678901`

**Keywords:** `IndusInd`, `UPI`, `via UPI`, `debited`, `credited`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: INDUS` + 11-14 digits
**Masking:** `A/c XXXXXX1234` (6 X + last 4)

---

## 2. Credit Card Spends

### HDFC Bank
**Examples:**
- `Rs.5,499.00 spent on HDFC Bank Credit Card ending 1234 on 15-01-2026 at 14:30:25 at AMAZON INDIA. Ref: CC123456789012`
- `Alert! INR 1,250.00 spent on Credit Card XXXXXXXXXXXX1234 on 15/01/26 at FLIPKART. Ref: HD1234567890`

**Keywords:** `spent`, `Credit Card`, `ending`, `Alert!`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: CC` + 12-14 digits, `Ref: HD` + 10-12 digits
**Masking:** `Card ending 1234`, `XXXXXXXXXXXX1234` (12 X + last 4)

### ICICI Bank
**Examples:**
- `Your ICICI Bank Credit Card XX1234 has been used for INR 3,200.00 on 15-01-26 14:30 at SWIGGY. Ref No: 000123456789. Available Credit: INR 45,000.00`
- `Alert: Rs.899.00 spent on Credit Card ending 1234 on 15/JAN/2026 at Myntra. Ref: 000123456789`

**Keywords:** `Credit Card`, `spent`, `Alert`, `XX1234`, `Available Credit`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref No: ` + 12 digits, `Ref: ` + 12 digits
**Masking:** `XX1234`, `Card ending 1234`

### SBI
**Examples:**
- `Rs.2,750.00 spent on SBI Card ending 1234 on 15-01-2026 at 14:30:25 at MMYT. Ref: SBI1234567890. Your total outstanding is Rs.15,234.56`
- `Alert: INR 5,999.00 spent on Credit Card XXXXXXXXXXXX1234 at CROMA. Ref: SB123456789`

**Keywords:** `spent`, `SBI Card`, `Credit Card`, `ending`, `Alert`, `outstanding`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: SBI` + 10-12 digits, `Ref: SB` + 9-11 digits
**Masking:** `Card ending 1234`, `XXXXXXXXXXXX1234`

### Axis Bank
**Examples:**
- `Dear Customer, INR 4,500.00 spent on Axis Bank Credit Card XXXXXXXXXXXX5678 on 15-Jan-2026 at 14:30 at ZOMATO. Ref: AX123456789012. Limit Avail: INR 32,100.00`
- `Rs.1,999.00 spent on Credit Card ending 5678 on 15/01/26 at BOOKMYSHOW. Ref: AXIS12345678`

**Keywords:** `spent`, `Credit Card`, `Axis Bank`, `Limit Avail`, `Ref`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: AX` + 12 digits, `Ref: AXIS` + digits
**Masking:** `XXXXXXXXXXXX5678`, `Card ending 5678`

### Kotak Mahindra Bank
**Examples:**
- `Rs.3,899.00 spent on Kotak Credit Card XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 at MAKEMYTRIP. Ref No: KTK123456789. Avail Limit: Rs.18,765.00`
- `INR 650.00 spent on Credit Card ending 1234 on 15/01/26 at STARBUCKS. Ref: KTK123456789`

**Keywords:** `spent`, `Kotak Credit Card`, `Avail Limit`, `Ref No`
**Amount Formats:** `Rs.X,XXX.XX`, `INR XXX.XX`
**Reference IDs:** `Ref No: KTK` + 9-11 digits, `Ref: KTK` + digits
**Masking:** `XXXXXXXXXX1234` (10 X + last 4), `Card ending 1234`

### Yes Bank
**Examples:**
- `Dear Customer, Rs.7,200.00 spent on Yes Bank Credit Card XXXXXXXXXX1234 on 15-01-2026 at 14:30 at CROMA. Ref: YES12345678901. Available Limit: Rs.25,000.00`
- `INR 1,450.00 spent on Credit Card ending 1234 on 15/01/26 at TATA CLiQ. Ref: YES12345678901`

**Keywords:** `spent`, `Yes Bank Credit Card`, `Available Limit`, `Ref`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: YES` + 11-13 digits
**Masking:** `XXXXXXXXXX1234` (10 X + last 4), `Card ending 1234`

### IDFC FIRST Bank
**Examples:**
- `Dear Customer, Rs.2,150.00 spent on IDFC FIRST Bank Credit Card XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 at URBAN LADDER. Ref: IDFC123456789. Limit: Rs.50,000.00`
- `INR 3,800.00 spent on Credit Card ending 1234 on 15/01/26 at IKEA. Ref: IDFC123456789`

**Keywords:** `spent`, `IDFC FIRST Bank Credit Card`, `Limit`, `Ref`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: IDFC` + 9-12 digits
**Masking:** `XXXXXXXXXX1234` (10 X + last 4), `Card ending 1234`

### IndusInd Bank
**Examples:**
- `Dear Customer, INR 4,299.00 spent on IndusInd Credit Card XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 at TANIQ. Ref: INDUS1234567890. Avail Credit: INR 28,500.00`
- `Rs.899.00 spent on Credit Card ending 1234 on 15/01/26 at AJIO. Ref: INDUS1234567890`

**Keywords:** `spent`, `IndusInd Credit Card`, `Avail Credit`, `Ref`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: INDUS` + 10-12 digits
**Masking:** `XXXXXXXXXX1234` (10 X + last 4), `Card ending 1234`

---

## 3. Debit Card / POS Transactions

### HDFC Bank
**Examples:**
- `Rs.899.00 debited from a/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 at RELIANCE SMART BAZAAR via POS. Ref: HD123456789012. Bal: Rs.12,345.67`
- `INR 2,500.00 debited from a/c XX1234 at BIG BAZAAR on 15/01/26 via POS terminal. Ref: HD123456789`

**Keywords:** `debited`, `via POS`, `POS terminal`, `a/c`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: HD` + 12-14 digits, `Ref: HD` + 9-11 digits
**Masking:** `a/c XXXXXXXXXX1234`, `a/c XX1234`

### ICICI Bank
**Examples:**
- `Your Account XX1234 has been debited by INR 1,200.00 on 15-01-26 14:30:25 IST at SPENCERS via POS. Ref No: 000012345678. Avl Bal: INR 23,456.78`
- `Rs.550.00 debited from A/c XX1234 at DMART on 15/JAN/26 via POS. Ref: 000012345678`

**Keywords:** `debited`, `via POS`, `Account`, `A/c`, `Avl Bal`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref No: ` + 12 digits, `Ref: ` + 12 digits
**Masking:** `XX1234`, `A/c XX1234`

### SBI
**Examples:**
- `Rs.3,450.00 debited from A/c XXXXXX1234 on 15-01-2026 at 14:30:25 at CROMA via POS. Ref: SBI123456789. Balance: INR 34,567.89`
- `INR 750.00 debited from A/c XX1234 at MORE MEGASTORE on 15/JAN/2026 via POS terminal. Ref: SBI123456789`

**Keywords:** `debited`, `via POS`, `POS terminal`, `A/c`, `Balance`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: SBI` + 9-12 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

### Axis Bank
**Examples:**
- `Dear Customer, INR 2,899.00 debited from A/c XXXXXXXXXX5678 on 15-Jan-2026 14:30:25 at VISHAL MEGA MART via POS. Ref: AX123456789012. Bal: INR 15,678.90`
- `Rs.1,100.00 debited from A/c XX5678 at NATURES BASKET on 15/01/26 via POS. Ref: AXIS12345678`

**Keywords:** `debited`, `via POS`, `A/c`, `Bal`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: AX` + 12 digits, `Ref: AXIS` + digits
**Masking:** `A/c XXXXXXXXXX5678`, `A/c XX5678`

### Kotak Mahindra Bank
**Examples:**
- `Rs.1,650.00 debited from Kotak A/c XXXXXX1234 on 15-01-2026 at 14:30:25 at D MART via POS. Ref No: KTK12345678901. Bal: Rs.8,765.43`
- `INR 450.00 debited from A/c XX1234 at LOCAL STORE on 15/01/26 via POS terminal. Ref: KTK12345678901`

**Keywords:** `debited`, `Kotak`, `via POS`, `POS terminal`, `A/c`, `Bal`
**Amount Formats:** `Rs.X,XXX.XX`, `INR XXX.XX`
**Reference IDs:** `Ref No: KTK` + 11-14 digits, `Ref: KTK` + digits
**Masking:** `Kotak A/c XXXXXX1234`, `A/c XX1234`

### Yes Bank
**Examples:**
- `Rs.2,300.00 debited from A/c XXXXXX1234 on 15-01-2026 14:30:25 at BIG BAZAAR via POS. Ref: YES12345678901. Bal: Rs.15,432.10`
- `INR 680.00 debited from A/c XX1234 at SPENCERS on 15/01/26 via POS. Ref: YES12345678901`

**Keywords:** `debited`, `via POS`, `A/c`, `Bal`
**Amount Formats:** `Rs.X,XXX.XX`, `INR XXX.XX`
**Reference IDs:** `Ref: YES` + 11-13 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

### IDFC FIRST Bank
**Examples:**
- `Dear Customer, Rs.4,500.00 debited from A/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 at RELIANCE SMART via POS. Ref: IDFC123456789. Bal: Rs.22,345.67`
- `INR 320.00 debited from A/c XX1234 at KIRANA STORE on 15/01/26 via POS. Ref: IDFC123456789`

**Keywords:** `debited`, `via POS`, `A/c`, `Bal`
**Amount Formats:** `Rs.X,XXX.XX`, `INR XXX.XX`
**Reference IDs:** `Ref: IDFC` + 9-12 digits
**Masking:** `A/c XXXXXXXXXX1234`, `A/c XX1234`

### IndusInd Bank
**Examples:**
- `Dear Customer, INR 1,899.00 debited from A/c XXXXXX1234 on 15-01-2026 at 14:30:25 at VISHAL MEGA MART via POS. Ref: INDUS1234567890. Bal: INR 9,876.54`
- `Rs.550.00 debited from A/c XX1234 at MORE STORE on 15/01/26 via POS. Ref: INDUS1234567890`

**Keywords:** `debited`, `via POS`, `A/c`, `Bal`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: INDUS` + 10-12 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

---

## 4. Net Banking (NEFT/IMPS/RTGS)

### HDFC Bank
**Examples:**
- `Rs.15,000.00 debited from a/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 via NEFT to JOHN DOE. Ref: HD123456789012. UTR: HDFC1234567890123456. Bal: Rs.35,678.90`
- `INR 5,500.00 credited to a/c XXXXXXXXXX1234 on 15/01/26 via IMPS from RAVI SINGH. Ref: HD123456789012. UTR: 123456789012`

**Keywords:** `NEFT`, `IMPS`, `RTGS`, `debited`, `credited`, `UTR`, `a/c`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: HD` + 12-14 digits, `UTR: ` + 16 digits or 12 digits
**Masking:** `a/c XXXXXXXXXX1234`

### ICICI Bank
**Examples:**
- `Your Account XX1234 has been debited by INR 25,000.00 on 15-01-26 14:30:25 IST via NEFT to PRIYA SHARMA. Ref No: 000012345678. UTR: ICIC1234567890123. Avl Bal: INR 50,230.45`
- `Your A/c XX1234 is credited with Rs.8,000.00 on 15-JAN-26 through IMPS from AMIT PATEL. Ref: 000012345678. UTR: 123456789012`

**Keywords:** `NEFT`, `IMPS`, `RTGS`, `debited`, `credited`, `UTR`, `A/c`, `Account`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref No: ` + 12 digits, `UTR: ` + 15-16 digits or 12 digits
**Masking:** `XX1234`, `A/c XX1234`

### SBI
**Examples:**
- `Rs.50,000.00 debited from A/c XXXXXX1234 on 15-01-2026 at 14:30:25 via RTGS to NEHA KUMARI. Ref: SBI123456789. UTR: SBIN1234567890123. Balance: INR 1,25,678.90`
- `Rs 12,500.00 credited to A/c XXXXXX1234 on 15-JAN-2026 via NEFT from KUMAR SINGH. Ref: SBI123456789. UTR: 123456789012`

**Keywords:** `NEFT`, `IMPS`, `RTGS`, `debited`, `credited`, `UTR`, `A/c`, `Balance`
**Amount Formats:** `Rs.X,XXX.XX`, `Rs X,XXX.XX`, `INR X,XX,XXX.XX` (Indian comma style)
**Reference IDs:** `Ref: SBI` + 9-12 digits, `UTR: ` + 16 digits or 12 digits
**Masking:** `A/c XXXXXX1234`

### Axis Bank
**Examples:**
- `Dear Customer, INR 35,000.00 debited from A/c XXXXXXXXXX5678 on 15-Jan-2026 14:30:25 via IMPS to RAJESH GUPTA. Ref: AX123456789012. UTR: AXIS12345678901234. Bal: INR 78,900.00`
- `Rs.18,000.00 credited to your Axis Bank A/c XXXXXXXX5678 via NEFT on 15/01/2026 from SUNITA DEVI. Ref: AXIS12345678. UTR: 123456789012`

**Keywords:** `NEFT`, `IMPS`, `RTGS`, `debited`, `credited`, `UTR`, `A/c`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: AX` + 12 digits, `UTR: AXIS` + digits or 12 digits
**Masking:** `A/c XXXXXXXXXX5678`, `XXXXXXXX5678`

### Kotak Mahindra Bank
**Examples:**
- `Rs.10,000.00 debited from Kotak A/c XXXXXX1234 on 15-01-2026 at 14:30:25 via NEFT to VIKRAM MEHTA. Ref No: KTK12345678901. UTR: KKBK1234567890123. Bal: Rs.45,678.90`
- `INR 7,500.00 credited to your Kotak A/c XXXXXX1234 via IMPS on 15/01/26 from ANITA JOSHI. Ref: KTK12345678901. UTR: 123456789012`

**Keywords:** `NEFT`, `IMPS`, `RTGS`, `debited`, `credited`, `UTR`, `Kotak A/c`, `Bal`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref No: KTK` + 11-14 digits, `UTR: KKBK` + digits or 12 digits
**Masking:** `Kotak A/c XXXXXX1234`

### Yes Bank
**Examples:**
- `Rs.20,000.00 debited from A/c XXXXXX1234 on 15-01-2026 14:30:25 via RTGS to POOJA RAI. Ref: YES12345678901. UTR: YESB1234567890123. Bal: Rs.65,432.10`
- `INR 9,000.00 credited to A/c XXXXXX1234 via NEFT on 15/01/26 from MANISH VERMA. Ref: YES12345678901. UTR: 123456789012`

**Keywords:** `NEFT`, `IMPS`, `RTGS`, `debited`, `credited`, `UTR`, `A/c`, `Bal`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: YES` + 11-13 digits, `UTR: YESB` + digits or 12 digits
**Masking:** `A/c XXXXXX1234`

### IDFC FIRST Bank
**Examples:**
- `Dear Customer, Rs.30,000.00 debited from A/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 via NEFT to DEEPIKA NAIR. Ref: IDFC123456789. UTR: IDFB1234567890123. Bal: Rs.1,25,678.90`
- `INR 15,000.00 credited to your IDFC FIRST Bank A/c XXXXXXXXXX1234 via IMPS on 15/01/26 from SANJAY PILLAI. Ref: IDFC123456789. UTR: 123456789012`

**Keywords:** `NEFT`, `IMPS`, `RTGS`, `debited`, `credited`, `UTR`, `A/c`, `Bal`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`, `Rs.1,25,678.90` (Indian comma style)
**Reference IDs:** `Ref: IDFC` + 9-12 digits, `UTR: IDFB` + digits or 12 digits
**Masking:** `A/c XXXXXXXXXX1234`

### IndusInd Bank
**Examples:**
- `Dear Customer, INR 40,000.00 debited from A/c XXXXXX1234 on 15-01-2026 at 14:30:25 via RTGS to ASHOK PANDIT. Ref: INDUS1234567890. UTR: INDB1234567890123. Bal: INR 2,15,678.90`
- `Rs.22,000.00 credited to your IndusInd Bank A/c XXXXXX1234 via NEFT on 15/01/26 from MEERA SHAH. Ref: INDUS1234567890. UTR: 123456789012`

**Keywords:** `NEFT`, `IMPS`, `RTGS`, `debited`, `credited`, `UTR`, `A/c`, `Bal`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`, `INR 2,15,678.90` (Indian comma style)
**Reference IDs:** `Ref: INDUS` + 10-12 digits, `UTR: INDB` + digits or 12 digits
**Masking:** `A/c XXXXXX1234`

---

## 5. ATM Transactions

### HDFC Bank
**Examples:**
- `Rs.5,000.00 debited from a/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 via ATM withdrawal at HDFC ATM MG ROAD. Ref: HD123456789012. Bal: Rs.18,765.43`
- `INR 2,000.00 withdrawn from a/c XX1234 at ATM on 15/01/26 at SBI ATM PARK STREET. Ref: HD123456789`

**Keywords:** `ATM withdrawal`, `ATM`, `withdrawn`, `debited`, `a/c`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: HD` + 12-14 digits, `Ref: HD` + 9-11 digits
**Masking:** `a/c XXXXXXXXXX1234`, `a/c XX1234`

### ICICI Bank
**Examples:**
- `Your Account XX1234 has been debited by INR 10,000.00 on 15-01-26 14:30:25 IST via ATM withdrawal at ICICI ATM CONNAUGHT PLACE. Ref No: 000012345678. Avl Bal: INR 35,678.90`
- `Rs.3,000.00 debited from A/c XX1234 at ATM on 15/JAN/26 at AXIS ATM SECTOR 21. Ref: 000012345678`

**Keywords:** `ATM withdrawal`, `ATM`, `debited`, `Account`, `A/c`, `Avl Bal`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref No: ` + 12 digits, `Ref: ` + 12 digits
**Masking:** `XX1234`, `A/c XX1234`

### SBI
**Examples:**
- `Rs.15,000.00 debited from A/c XXXXXX1234 on 15-01-2026 at 14:30:25 via ATM withdrawal at SBI ATM SECTOR 15. Ref: SBI123456789. Balance: INR 78,900.00`
- `Rs 5,000.00 withdrawn from A/c XX1234 at ATM on 15/JAN/2026 at HDFC ATM MG ROAD. Ref: SBI123456789`

**Keywords:** `ATM withdrawal`, `ATM`, `withdrawn`, `debited`, `A/c`, `Balance`
**Amount Formats:** `Rs.X,XXX.XX`, `Rs X,XXX.XX`
**Reference IDs:** `Ref: SBI` + 9-12 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

### Axis Bank
**Examples:**
- `Dear Customer, INR 8,000.00 debited from A/c XXXXXXXXXX5678 on 15-Jan-2026 14:30:25 via ATM withdrawal at AXIS ATM JANPATH. Ref: AX123456789012. Bal: INR 42,100.00`
- `Rs.2,500.00 debited from A/c XX5678 at ATM on 15/01/26 at ICICI ATM KAROL BAGH. Ref: AXIS12345678`

**Keywords:** `ATM withdrawal`, `ATM`, `debited`, `A/c`, `Bal`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: AX` + 12 digits, `Ref: AXIS` + digits
**Masking:** `A/c XXXXXXXXXX5678`, `A/c XX5678`

### Kotak Mahindra Bank
**Examples:**
- `Rs.12,000.00 debited from Kotak A/c XXXXXX1234 on 15-01-2026 at 14:30:25 via ATM withdrawal at KOTAK ATM LINKING ROAD. Ref No: KTK12345678901. Bal: Rs.55,678.90`
- `INR 4,000.00 debited from A/c XX1234 at ATM on 15/01/26 at SBI ATM ANDHERI. Ref: KTK12345678901`

**Keywords:** `ATM withdrawal`, `ATM`, `debited`, `Kotak A/c`, `A/c`, `Bal`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref No: KTK` + 11-14 digits, `Ref: KTK` + digits
**Masking:** `Kotak A/c XXXXXX1234`, `A/c XX1234`

### Yes Bank
**Examples:**
- `Rs.7,000.00 debited from A/c XXXXXX1234 on 15-01-2026 14:30:25 via ATM withdrawal at YES BANK ATM LOKHANDWALA. Ref: YES12345678901. Bal: Rs.28,765.43`
- `INR 1,500.00 debited from A/c XX1234 at ATM on 15/01/26 at HDFC ATM VERSOVA. Ref: YES12345678901`

**Keywords:** `ATM withdrawal`, `ATM`, `debited`, `A/c`, `Bal`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: YES` + 11-13 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

### IDFC FIRST Bank
**Examples:**
- `Dear Customer, Rs.10,000.00 debited from A/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 via ATM withdrawal at IDFC FIRST BANK ATM POWAI. Ref: IDFC123456789. Bal: Rs.65,432.10`
- `INR 3,500.00 debited from A/c XX1234 at ATM on 15/01/26 at KOTAK ATM BANDRA. Ref: IDFC123456789`

**Keywords:** `ATM withdrawal`, `ATM`, `debited`, `A/c`, `Bal`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: IDFC` + 9-12 digits
**Masking:** `A/c XXXXXXXXXX1234`, `A/c XX1234`

### IndusInd Bank
**Examples:**
- `Dear Customer, INR 9,500.00 debited from A/c XXXXXX1234 on 15-01-2026 at 14:30:25 via ATM withdrawal at INDUSIND BANK ATM Juhu. Ref: INDUS1234567890. Bal: INR 38,765.43`
- `Rs.2,000.00 debited from A/c XX1234 at ATM on 15/01/26 at ICICI ATM VILE PARLE. Ref: INDUS1234567890`

**Keywords:** `ATM withdrawal`, `ATM`, `debited`, `A/c`, `Bal`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: INDUS` + 10-12 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

---

## 6. Online Purchases

### HDFC Bank
**Examples:**
- `Rs.12,499.00 debited from a/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 at AMAZON.IN for online purchase. Ref: HD123456789012. Bal: Rs.45,678.90`
- `INR 3,299.00 debited from a/c XX1234 at FLIPKART.COM on 15/01/26 for online transaction. Ref: HD123456789`

**Keywords:** `online purchase`, `online transaction`, `debited`, `AMAZON.IN`, `FLIPKART.COM`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: HD` + 12-14 digits, `Ref: HD` + 9-11 digits
**Masking:** `a/c XXXXXXXXXX1234`, `a/c XX1234`

### ICICI Bank
**Examples:**
- `Your Account XX1234 has been debited by INR 8,999.00 on 15-01-26 14:30:25 IST at PAYTM for online purchase. Ref No: 000012345678. Avl Bal: INR 52,345.67`
- `Rs.1,599.00 debited from A/c XX1234 at NYKAA.COM on 15/JAN/26 for online transaction. Ref: 000012345678`

**Keywords:** `online purchase`, `online transaction`, `debited`, `Account`, `A/c`, `Avl Bal`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref No: ` + 12 digits, `Ref: ` + 12 digits
**Masking:** `XX1234`, `A/c XX1234`

### SBI
**Examples:**
- `Rs.15,999.00 debited from A/c XXXXXX1234 on 15-01-2026 at 14:30:25 at MAKEMYTRIP.COM for online purchase. Ref: SBI123456789. Balance: INR 62,345.78`
- `Rs 4,599.00 debited from A/c XX1234 at BOOKMYSHOW on 15/JAN/2026 for online transaction. Ref: SBI123456789`

**Keywords:** `online purchase`, `online transaction`, `debited`, `A/c`, `Balance`
**Amount Formats:** `Rs.X,XXX.XX`, `Rs X,XXX.XX`
**Reference IDs:** `Ref: SBI` + 9-12 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

### Axis Bank
**Examples:**
- `Dear Customer, INR 22,500.00 debited from A/c XXXXXXXXXX5678 on 15-Jan-2026 14:30:25 at AIRBNB for online purchase. Ref: AX123456789012. Bal: INR 87,654.32`
- `Rs.6,799.00 debited from A/c XX5678 at CROMA.COM on 15/01/26 for online transaction. Ref: AXIS12345678`

**Keywords:** `online purchase`, `online transaction`, `debited`, `A/c`, `Bal`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: AX` + 12 digits, `Ref: AXIS` + digits
**Masking:** `A/c XXXXXXXXXX5678`, `A/c XX5678`

### Kotak Mahindra Bank
**Examples:**
- `Rs.5,499.00 debited from Kotak A/c XXXXXX1234 on 15-01-2026 at 14:30:25 at URBAN LADDER for online purchase. Ref No: KTK12345678901. Bal: Rs.72,345.67`
- `INR 2,199.00 debited from A/c XX1234 at ZIVAME.COM on 15/01/26 for online transaction. Ref: KTK12345678901`

**Keywords:** `online purchase`, `online transaction`, `debited`, `Kotak A/c`, `A/c`, `Bal`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref No: KTK` + 11-14 digits, `Ref: KTK` + digits
**Masking:** `Kotak A/c XXXXXX1234`, `A/c XX1234`

### Yes Bank
**Examples:**
- `Rs.9,299.00 debited from A/c XXXXXX1234 on 15-01-2026 14:30:25 at TANIQ.COM for online purchase. Ref: YES12345678901. Bal: Rs.38,765.43`
- `INR 3,899.00 debited from A/c XX1234 at AJIO.COM on 15/01/26 for online transaction. Ref: YES12345678901`

**Keywords:** `online purchase`, `online transaction`, `debited`, `A/c`, `Bal`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: YES` + 11-13 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

### IDFC FIRST Bank
**Examples:**
- `Dear Customer, Rs.18,750.00 debited from A/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 at IRCTC for online purchase. Ref: IDFC123456789. Bal: Rs.95,432.10`
- `INR 7,999.00 debited from A/c XX1234 at RELIANCE DIGITAL on 15/01/26 for online transaction. Ref: IDFC123456789`

**Keywords:** `online purchase`, `online transaction`, `debited`, `A/c`, `Bal`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: IDFC` + 9-12 digits
**Masking:** `A/c XXXXXXXXXX1234`, `A/c XX1234`

### IndusInd Bank
**Examples:**
- `Dear Customer, INR 11,499.00 debited from A/c XXXXXX1234 on 15-01-2026 at 14:30:25 at PEPPERFRY for online purchase. Ref: INDUS1234567890. Bal: INR 54,321.98`
- `Rs.4,250.00 debited from A/c XX1234 at FABINDIA.COM on 15/01/26 for online transaction. Ref: INDUS1234567890`

**Keywords:** `online purchase`, `online transaction`, `debited`, `A/c`, `Bal`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: INDUS` + 10-12 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

---

## 7. Recurring / NACH / Autopay

### HDFC Bank
**Examples:**
- `Rs.2,500.00 debited from a/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 towards NACH-ECS for NETFLIX subscription. Ref: HD123456789012. Bal: Rs.22,345.67`
- `INR 999.00 debited from a/c XX1234 on 15/01/26 for Autopay - SPOITFY MUSIC. Ref: HD123456789`

**Keywords:** `NACH`, `ECS`, `Autopay`, `subscription`, `recurring`, `debited`
**Amount Formats:** `Rs.X,XXX.XX`, `INR XXX.XX`
**Reference IDs:** `Ref: HD` + 12-14 digits, `Ref: HD` + 9-11 digits
**Masking:** `a/c XXXXXXXXXX1234`, `a/c XX1234`

### ICICI Bank
**Examples:**
- `Your Account XX1234 has been debited by INR 1,200.00 on 15-01-26 14:30:25 IST for NACH - AMAZON PRIME subscription. Ref No: 000012345678. Avl Bal: INR 18,765.43`
- `Rs.500.00 debited from A/c XX1234 on 15/JAN/26 for Autopay - HOTSTAR. Ref: 000012345678`

**Keywords:** `NACH`, `ECS`, `Autopay`, `subscription`, `debited`, `Account`, `A/c`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref No: ` + 12 digits, `Ref: ` + 12 digits
**Masking:** `XX1234`, `A/c XX1234`

### SBI
**Examples:**
- `Rs.3,999.00 debited from A/c XXXXXX1234 on 15-01-2026 at 14:30:25 for NACH-ECS - LIC PREMIUM payment. Ref: SBI123456789. Balance: INR 56,789.01`
- `Rs 850.00 debited from A/c XX1234 on 15/JAN/2026 for Autopay - YOUTUBE PREMIUM. Ref: SBI123456789`

**Keywords:** `NACH`, `ECS`, `Autopay`, `premium`, `subscription`, `debited`, `A/c`
**Amount Formats:** `Rs.X,XXX.XX`, `Rs X,XXX.XX`
**Reference IDs:** `Ref: SBI` + 9-12 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

### Axis Bank
**Examples:**
- `Dear Customer, INR 1,799.00 debited from A/c XXXXXXXXXX5678 on 15-Jan-2026 14:30:25 for NACH - ADOBE CREATIVE CLOUD. Ref: AX123456789012. Bal: INR 32,456.78`
- `Rs.499.00 debited from A/c XX5678 on 15/01/26 for Autopay - DISNEY+ HOTSTAR. Ref: AXIS12345678`

**Keywords:** `NACH`, `Autopay`, `subscription`, `debited`, `A/c`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: AX` + 12 digits, `Ref: AXIS` + digits
**Masking:** `A/c XXXXXXXXXX5678`, `A/c XX5678`

### Kotak Mahindra Bank
**Examples:**
- `Rs.5,000.00 debited from Kotak A/c XXXXXX1234 on 15-01-2026 at 14:30:25 for NACH-ECS - MUTUAL FUND SIP. Ref No: KTK12345678901. Bal: Rs.65,432.10`
- `INR 299.00 debited from A/c XX1234 on 15/01/26 for Autopay - GAANA PLUS. Ref: KTK12345678901`

**Keywords:** `NACH`, `ECS`, `Autopay`, `SIP`, `debited`, `Kotak A/c`, `A/c`
**Amount Formats:** `Rs.X,XXX.XX`, `INR XXX.XX`
**Reference IDs:** `Ref No: KTK` + 11-14 digits, `Ref: KTK` + digits
**Masking:** `Kotak A/c XXXXXX1234`, `A/c XX1234`

### Yes Bank
**Examples:**
- `Rs.8,500.00 debited from A/c XXXXXX1234 on 15-01-2026 14:30:25 for NACH - HOME LOAN EMI. Ref: YES12345678901. Bal: Rs.1,25,678.90`
- `INR 1,499.00 debited from A/c XX1234 on 15/01/26 for Autopay - SONY LIV. Ref: YES12345678901`

**Keywords:** `NACH`, `Autopay`, `EMI`, `subscription`, `debited`, `A/c`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: YES` + 11-13 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

### IDFC FIRST Bank
**Examples:**
- `Dear Customer, Rs.12,000.00 debited from A/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 for NACH - CAR LOAN EMI. Ref: IDFC123456789. Bal: Rs.85,432.10`
- `INR 699.00 debited from A/c XX1234 on 15/01/26 for Autopay - APPLE MUSIC. Ref: IDFC123456789`

**Keywords:** `NACH`, `Autopay`, `EMI`, `subscription`, `debited`, `A/c`
**Amount Formats:** `Rs.X,XXX.XX`, `INR XXX.XX`
**Reference IDs:** `Ref: IDFC` + 9-12 digits
**Masking:** `A/c XXXXXXXXXX1234`, `A/c XX1234`

### IndusInd Bank
**Examples:**
- `Dear Customer, INR 15,000.00 debited from A/c XXXXXX1234 on 15-01-2026 at 14:30:25 for NACH - PERSONAL LOAN EMI. Ref: INDUS1234567890. Bal: INR 1,45,678.90`
- `Rs.999.00 debited from A/c XX1234 on 15/01/26 for Autopay - JIO CINEMA. Ref: INDUS1234567890`

**Keywords:** `NACH`, `Autopay`, `EMI`, `subscription`, `debited`, `A/c`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: INDUS` + 10-12 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

---

## 8. Refunds / Credits

### HDFC Bank
**Examples:**
- `Rs.1,299.00 credited to a/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 towards refund from FLIPKART for order OD123456789. Ref: HD123456789012. Bal: Rs.25,678.90`
- `INR 500.00 credited to a/c XX1234 on 15/01/26 for reversal of txn at MERCHANT. Ref: HD123456789`

**Keywords:** `credited`, `refund`, `reversal`, `towards`, `a/c`
**Amount Formats:** `Rs.X,XXX.XX`, `INR XXX.XX`
**Reference IDs:** `Ref: HD` + 12-14 digits, `Ref: HD` + 9-11 digits
**Masking:** `a/c XXXXXXXXXX1234`, `a/c XX1234`

### ICICI Bank
**Examples:**
- `Your A/c XX1234 is credited with INR 2,500.00 on 15-01-26 14:30:25 IST towards refund from AMAZON for order 402-1234567-1234567. Ref No: 000012345678. Avl Bal: INR 35,678.90`
- `Rs.800.00 credited to A/c XX1234 on 15/JAN/26 for reversal of failed txn. Ref: 000012345678`

**Keywords:** `credited`, `refund`, `reversal`, `failed txn`, `A/c`, `Avl Bal`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref No: ` + 12 digits, `Ref: ` + 12 digits
**Masking:** `XX1234`, `A/c XX1234`

### SBI
**Examples:**
- `Rs.4,999.00 credited to A/c XXXXXX1234 on 15-01-2026 at 14:30:25 towards refund from MAKEMYTRIP for booking PNR 1234567890. Ref: SBI123456789. Balance: INR 72,345.67`
- `Rs 1,200.00 credited to A/c XX1234 on 15/JAN/2026 for reversal of disputed txn. Ref: SBI123456789`

**Keywords:** `credited`, `refund`, `reversal`, `disputed`, `A/c`, `Balance`
**Amount Formats:** `Rs.X,XXX.XX`, `Rs X,XXX.XX`
**Reference IDs:** `Ref: SBI` + 9-12 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

### Axis Bank
**Examples:**
- `Dear Customer, INR 3,750.00 credited to A/c XXXXXXXXXX5678 on 15-Jan-2026 14:30:25 towards refund from URBAN LADDER for order ORD12345678. Ref: AX123456789012. Bal: INR 45,678.90`
- `Rs.1,500.00 credited to your Axis Bank A/c XX5678 on 15/01/26 for reversal of failed UPI. Ref: AXIS12345678`

**Keywords:** `credited`, `refund`, `reversal`, `failed`, `A/c`, `Bal`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: AX` + 12 digits, `Ref: AXIS` + digits
**Masking:** `A/c XXXXXXXXXX5678`, `A/c XX5678`

### Kotak Mahindra Bank
**Examples:**
- `Rs.2,199.00 credited to Kotak A/c XXXXXX1234 on 15-01-2026 at 14:30:25 towards refund from CROMA for invoice CR12345678. Ref No: KTK12345678901. Bal: Rs.58,765.43`
- `INR 650.00 credited to A/c XX1234 on 15/01/26 for reversal of duplicate txn. Ref: KTK12345678901`

**Keywords:** `credited`, `refund`, `reversal`, `invoice`, `Kotak A/c`, `A/c`, `Bal`
**Amount Formats:** `Rs.X,XXX.XX`, `INR XXX.XX`
**Reference IDs:** `Ref No: KTK` + 11-14 digits, `Ref: KTK` + digits
**Masking:** `Kotak A/c XXXXXX1234`, `A/c XX1234`

### Yes Bank
**Examples:**
- `Rs.5,500.00 credited to A/c XXXXXX1234 on 15-01-2026 14:30:25 towards refund from AIRINDIA for ticket 0981234567890. Ref: YES12345678901. Bal: Rs.82,345.67`
- `INR 1,800.00 credited to A/c XX1234 on 15/01/26 for reversal of declined txn. Ref: YES12345678901`

**Keywords:** `credited`, `refund`, `reversal`, `declined`, `A/c`, `Bal`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: YES` + 11-13 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

### IDFC FIRST Bank
**Examples:**
- `Dear Customer, Rs.8,999.00 credited to A/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 towards refund from RELIANCE DIGITAL for invoice RD12345678. Ref: IDFC123456789. Bal: Rs.95,432.10`
- `INR 2,500.00 credited to A/c XX1234 on 15/01/26 for reversal of timeout txn. Ref: IDFC123456789`

**Keywords:** `credited`, `refund`, `reversal`, `timeout`, `A/c`, `Bal`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: IDFC` + 9-12 digits
**Masking:** `A/c XXXXXXXXXX1234`, `A/c XX1234`

### IndusInd Bank
**Examples:**
- `Dear Customer, INR 6,299.00 credited to A/c XXXXXX1234 on 15-01-2026 at 14:30:25 towards refund from TATA CLiQ for order TC12345678. Ref: INDUS1234567890. Bal: INR 78,901.23`
- `Rs.3,000.00 credited to A/c XX1234 on 15/01/26 for reversal of error txn. Ref: INDUS1234567890`

**Keywords:** `credited`, `refund`, `reversal`, `error`, `A/c`, `Bal`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: INDUS` + 10-12 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

---

## 9. EMI Conversions

### HDFC Bank
**Examples:**
- `Rs.25,000.00 converted to EMI on Credit Card ending 1234 on 15-01-2026 at 14:30:25 for 6 months. EMI: Rs.4,379.00/month. Ref: HD123456789012`
- `INR 15,000.00 purchase on CC XX1234 converted to 3 month EMI. First EMI Rs.5,200.00 due on 15-Feb-2026. Ref: HD123456789`

**Keywords:** `converted to EMI`, `EMI`, `month`, `Credit Card`, `CC`, `due`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`, `Rs.X,XXX.XX/month`
**Reference IDs:** `Ref: HD` + 12-14 digits, `Ref: HD` + 9-11 digits
**Masking:** `Card ending 1234`, `CC XX1234`

### ICICI Bank
**Examples:**
- `Your ICICI Bank Credit Card XX1234 purchase of INR 35,000.00 on 15-01-26 has been converted to EMI for 12 months. Monthly EMI: INR 3,083.00. Ref No: 000012345678`
- `Rs.20,000.00 on CC ending 1234 converted to 6 EMI. EMI Rs.3,500.00 due on 15/FEB/26. Ref: 000012345678`

**Keywords:** `converted to EMI`, `Monthly EMI`, `EMI`, `CC`, `due`, `Credit Card`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref No: ` + 12 digits, `Ref: ` + 12 digits
**Masking:** `XX1234`, `CC ending 1234`

### SBI
**Examples:**
- `Rs.50,000.00 on SBI Card ending 1234 converted to EMI for 9 months on 15-01-2026 at 14:30:25. Monthly EMI: Rs.5,889.00. Ref: SBI123456789`
- `INR 18,000.00 purchase on CC XX1234 converted to 3 month EMI. EMI Rs.6,200.00 due on 15/FEB/2026. Ref: SBI123456789`

**Keywords:** `converted to EMI`, `Monthly EMI`, `EMI`, `SBI Card`, `CC`, `due`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: SBI` + 9-12 digits
**Masking:** `Card ending 1234`, `CC XX1234`

### Axis Bank
**Examples:**
- `Dear Customer, INR 40,000.00 on Axis Bank Credit Card XXXXXXXXXXXX5678 converted to EMI for 18 months on 15-Jan-2026 at 14:30:25. Monthly EMI: INR 2,456.00. Ref: AX123456789012`
- `Rs.28,000.00 on CC ending 5678 converted to 6 EMI. EMI Rs.4,889.00 due on 15/FEB/26. Ref: AXIS12345678`

**Keywords:** `converted to EMI`, `Monthly EMI`, `EMI`, `Credit Card`, `CC`, `due`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: AX` + 12 digits, `Ref: AXIS` + digits
**Masking:** `XXXXXXXXXXXX5678`, `CC ending 5678`

### Kotak Mahindra Bank
**Examples:**
- `Rs.22,000.00 on Kotak Credit Card XXXXXXXXXX1234 converted to EMI for 6 months on 15-01-2026 at 14:30:25. Monthly EMI: Rs.3,850.00. Ref No: KTK12345678901`
- `INR 12,000.00 on CC XX1234 converted to 3 EMI. EMI Rs.4,150.00 due on 15/FEB/26. Ref: KTK12345678901`

**Keywords:** `converted to EMI`, `Monthly EMI`, `EMI`, `Kotak Credit Card`, `CC`, `due`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref No: KTK` + 11-14 digits, `Ref: KTK` + digits
**Masking:** `XXXXXXXXXX1234`, `CC XX1234`

### Yes Bank
**Examples:**
- `Rs.32,000.00 on Yes Bank Credit Card XXXXXXXXXX1234 converted to EMI for 9 months on 15-01-2026 at 14:30:25. Monthly EMI: Rs.3,756.00. Ref: YES12345678901`
- `INR 16,000.00 on CC ending 1234 converted to 6 EMI. EMI Rs.2,834.00 due on 15/FEB/26. Ref: YES12345678901`

**Keywords:** `converted to EMI`, `Monthly EMI`, `EMI`, `Yes Bank Credit Card`, `CC`, `due`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: YES` + 11-13 digits
**Masking:** `XXXXXXXXXX1234`, `CC ending 1234`

### IDFC FIRST Bank
**Examples:**
- `Dear Customer, Rs.45,000.00 on IDFC FIRST Bank Credit Card XXXXXXXXXX1234 converted to EMI for 12 months on 15-01-2026 at 14:30:25. Monthly EMI: Rs.4,025.00. Ref: IDFC123456789`
- `INR 20,000.00 on CC XX1234 converted to 3 EMI. EMI Rs.6,890.00 due on 15/FEB/26. Ref: IDFC123456789`

**Keywords:** `converted to EMI`, `Monthly EMI`, `EMI`, `IDFC FIRST Bank Credit Card`, `CC`, `due`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: IDFC` + 9-12 digits
**Masking:** `XXXXXXXXXX1234`, `CC XX1234`

### IndusInd Bank
**Examples:**
- `Dear Customer, INR 60,000.00 on IndusInd Credit Card XXXXXXXXXX1234 converted to EMI for 24 months on 15-01-2026 at 14:30:25. Monthly EMI: INR 2,789.00. Ref: INDUS1234567890`
- `Rs.35,000.00 on CC ending 1234 converted to 12 EMI. EMI Rs.3,156.00 due on 15/FEB/26. Ref: INDUS1234567890`

**Keywords:** `converted to EMI`, `Monthly EMI`, `EMI`, `IndusInd Credit Card`, `CC`, `due`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: INDUS` + 10-12 digits
**Masking:** `XXXXXXXXXX1234`, `CC ending 1234`

---

## 10. Failed / Reversed Transactions

### HDFC Bank
**Examples:**
- `Your txn of Rs.5,000.00 on a/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 to MERCHANT via UPI has FAILED. No amount debited. Ref: HD123456789012. Reason: Insufficient funds`
- `INR 2,500.00 debited from a/c XX1234 on 15/01/26 has been REVERSED due to txn failure. Ref: HD123456789. Amount will be credited within 24 hrs`

**Keywords:** `FAILED`, `REVERSED`, `txn failure`, `Insufficient funds`, `No amount debited`, `credited within`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: HD` + 12-14 digits, `Ref: HD` + 9-11 digits
**Masking:** `a/c XXXXXXXXXX1234`, `a/c XX1234`

### ICICI Bank
**Examples:**
- `Your txn of INR 3,200.00 from Account XX1234 on 15-01-26 14:30:25 IST via UPI has FAILED. Ref No: 000012345678. Reason: Technical error. Amount not debited`
- `Rs.1,800.00 debited from A/c XX1234 on 15/JAN/26 has been REVERSED due to failed txn. Ref: 000012345678. Amount credited within 24-48 hrs`

**Keywords:** `FAILED`, `REVERSED`, `failed txn`, `Technical error`, `Amount not debited`, `credited within`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref No: ` + 12 digits, `Ref: ` + 12 digits
**Masking:** `XX1234`, `A/c XX1234`

### SBI
**Examples:**
- `Your txn of Rs.7,500.00 from A/c XXXXXX1234 on 15-01-2026 at 14:30:25 via NEFT has FAILED. Ref: SBI123456789. Reason: Invalid beneficiary. Amount not debited`
- `Rs 4,000.00 debited from A/c XX1234 on 15/JAN/2026 has been REVERSED due to timeout. Ref: SBI123456789. Amount will be credited within 48 hrs`

**Keywords:** `FAILED`, `REVERSED`, `timeout`, `Invalid beneficiary`, `Amount not debited`, `credited within`
**Amount Formats:** `Rs.X,XXX.XX`, `Rs X,XXX.XX`
**Reference IDs:** `Ref: SBI` + 9-12 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

### Axis Bank
**Examples:**
- `Dear Customer, your txn of INR 9,000.00 from A/c XXXXXXXXXX5678 on 15-Jan-2026 14:30:25 via UPI has FAILED. Ref: AX123456789012. Reason: Bank server down. Amount not debited`
- `Rs.5,500.00 debited from A/c XX5678 on 15/01/26 has been REVERSED due to declined txn. Ref: AXIS12345678. Amount credited within 24 hrs`

**Keywords:** `FAILED`, `REVERSED`, `declined`, `Bank server down`, `Amount not debited`, `credited within`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: AX` + 12 digits, `Ref: AXIS` + digits
**Masking:** `A/c XXXXXXXXXX5678`, `A/c XX5678`

### Kotak Mahindra Bank
**Examples:**
- `Your txn of Rs.2,800.00 from Kotak A/c XXXXXX1234 on 15-01-2026 at 14:30:25 via IMPS has FAILED. Ref No: KTK12345678901. Reason: Incorrect UPI PIN. Amount not debited`
- `INR 1,500.00 debited from A/c XX1234 on 15/01/26 has been REVERSED due to network error. Ref: KTK12345678901. Amount will be credited within 24-48 hrs`

**Keywords:** `FAILED`, `REVERSED`, `network error`, `Incorrect UPI PIN`, `Amount not debited`, `credited within`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref No: KTK` + 11-14 digits, `Ref: KTK` + digits
**Masking:** `Kotak A/c XXXXXX1234`, `A/c XX1234`

### Yes Bank
**Examples:**
- `Your txn of Rs.6,000.00 from A/c XXXXXX1234 on 15-01-2026 14:30:25 via UPI has FAILED. Ref: YES12345678901. Reason: Exceeds daily limit. Amount not debited`
- `INR 3,500.00 debited from A/c XX1234 on 15/01/26 has been REVERSED due to system error. Ref: YES12345678901. Amount credited within 48 hrs`

**Keywords:** `FAILED`, `REVERSED`, `Exceeds daily limit`, `system error`, `Amount not debited`, `credited within`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: YES` + 11-13 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

### IDFC FIRST Bank
**Examples:**
- `Dear Customer, your txn of Rs.10,000.00 from A/c XXXXXXXXXX1234 on 15-01-2026 at 14:30:25 via NEFT has FAILED. Ref: IDFC123456789. Reason: Account frozen. Amount not debited`
- `INR 4,200.00 debited from A/c XX1234 on 15/01/26 has been REVERSED due to processing error. Ref: IDFC123456789. Amount will be credited within 24-72 hrs`

**Keywords:** `FAILED`, `REVERSED`, `processing error`, `Account frozen`, `Amount not debited`, `credited within`
**Amount Formats:** `Rs.X,XXX.XX`, `INR X,XXX.XX`
**Reference IDs:** `Ref: IDFC` + 9-12 digits
**Masking:** `A/c XXXXXXXXXX1234`, `A/c XX1234`

### IndusInd Bank
**Examples:**
- `Dear Customer, your txn of INR 8,500.00 from A/c XXXXXX1234 on 15-01-2026 at 14:30:25 via UPI has FAILED. Ref: INDUS1234567890. Reason: Invalid VPA. Amount not debited`
- `Rs.2,900.00 debited from A/c XX1234 on 15/01/26 has been REVERSED due to gateway timeout. Ref: INDUS1234567890. Amount credited within 48-72 hrs`

**Keywords:** `FAILED`, `REVERSED`, `gateway timeout`, `Invalid VPA`, `Amount not debited`, `credited within`
**Amount Formats:** `INR X,XXX.XX`, `Rs.X,XXX.XX`
**Reference IDs:** `Ref: INDUS` + 10-12 digits
**Masking:** `A/c XXXXXX1234`, `A/c XX1234`

---

## Pattern Summary

### Amount Format Variations
| Format | Example | Notes |
|--------|---------|-------|
| `Rs.X,XXX.XX` | `Rs.1,500.00` | Most common across all banks |
| `INR X,XXX.XX` | `INR 2,500.00` | Common in ICICI, Axis, IndusInd |
| `Rs X,XXX.XX` | `Rs 500.00` | SBI variant (no period after Rs) |
| `Rs.XX,XXX.XX` | `Rs.1,25,678.90` | Indian lakh comma style (SBI, IDFC, IndusInd) |
| `₹X,XXX.XX` | `₹1,500.00` | Rare, mostly newer templates |

### Account/Card Masking Patterns
| Pattern | Example | Banks |
|---------|---------|-------|
| `a/c XXXXXXXXXX1234` | 10 X + last 4 | HDFC, IDFC |
| `A/c XXXXXX1234` | 6 X + last 4 | SBI, Kotak, Yes Bank, IndusInd |
| `A/c XX1234` | 2 X + last 4 | ICICI (short form) |
| `Card ending 1234` | Text + last 4 | All banks (credit cards) |
| `XXXXXXXXXXXX1234` | 12 X + last 4 | Credit cards across banks |
| `Kotak A/c XXXXXX1234` | Bank prefix + 6 X + last 4 | Kotak specific |

### Reference ID Patterns
| Bank | Format | Length |
|------|--------|--------|
| HDFC | `HD` + digits | 12-14 chars |
| ICICI | 12 digits (numeric) | 12 chars |
| SBI | `SBI` or `SB` + digits | 9-12 chars |
| Axis | `AX` or `AXIS` + digits | 10-14 chars |
| Kotak | `KTK` + digits | 11-14 chars |
| Yes Bank | `YES` + digits | 11-13 chars |
| IDFC | `IDFC` + digits | 9-12 chars |
| IndusInd | `INDUS` + digits | 10-12 chars |

### UTR Number Formats
| Format | Example | Used In |
|--------|---------|---------|
| `BANK` + 12-16 digits | `HDFC1234567890123456` | NEFT/RTGS |
| 12 digits | `123456789012` | IMPS |
| `SBIN` + digits | `SBIN1234567890123` | SBI NEFT |
| `ICIC` + digits | `ICIC1234567890123` | ICICI NEFT |

### Common Keywords by Transaction Type
| Type | Primary Keywords |
|------|------------------|
| UPI | `via UPI`, `UPI transfer`, `UPI-` |
| Credit Card | `spent`, `Credit Card`, `ending` |
| Debit/POS | `via POS`, `POS terminal` |
| Net Banking | `NEFT`, `IMPS`, `RTGS`, `UTR` |
| ATM | `ATM withdrawal`, `ATM` |
| Online | `online purchase`, `online transaction` |
| Recurring | `NACH`, `ECS`, `Autopay`, `SIP`, `subscription` |
| Refunds | `credited`, `refund`, `reversal` |
| EMI | `converted to EMI`, `Monthly EMI`, `EMI` |
| Failed | `FAILED`, `REVERSED`, `failed txn` |

### Date/Time Formats
| Format | Example | Banks |
|--------|---------|-------|
| `DD-MM-YYYY at HH:MM:SS` | `15-01-2026 at 14:30:25` | Most common |
| `DD/MM/YY` | `15/01/26` | Short form variants |
| `DD-MMM-YY` | `15-JAN-26` | ICICI, Axis |
| `DD-MMM-YYYY` | `15-Jan-2026` | Axis, some ICICI |
