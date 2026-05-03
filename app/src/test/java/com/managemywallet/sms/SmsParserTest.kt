package com.managemywallet.sms

import com.managemywallet.data.entity.TransactionType
import org.junit.Assert.*
import org.junit.Test

class SmsParserTest {

    private val emptyMappings = emptyMap<String, String>()

    @Test
    fun `HDFC debit card POS - Shopping`() {
        val sms = "INR 2499.00 debited from A/c XX1234 on 15-Jan-25 15:30:45 IST at AMAZON RETAIL PVT LTD towards Purchase. Ref: HD123456789. Avail Bal: INR 45230.50."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(2499.00, result!!.amount, 0.01)
        assertEquals("HDFC Bank", result.bankName)
        assertEquals("XX1234", result.accountNumber)
        assertEquals(TransactionType.DEBIT, result.type)
        assertEquals("Apparel", result.category)
    }

    @Test
    fun `HDFC UPI food delivery`() {
        val sms = "Rs.450.00 debited from A/c XX5678 on 15-Jan-25 19:45:12 IST towards UPI-SWIGGY-INSTAMART-2847569120-9387650192-UPI. Ref: 503982671440. Avl Bal: INR 12340.25."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(450.0, result!!.amount, 0.01)
        assertEquals("HDFC Bank", result.bankName)
        assertEquals(TransactionType.DEBIT, result.type)
        assertEquals("Grocery", result.category)
    }

    @Test
    fun `HDFC credit card Netflix`() {
        val sms = "Credit card XX1234 spent INR 649.00 at NETFLIX on 14-Jan-25. Total outstanding: INR 15230.00. Due date: 05-Feb-25."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(649.0, result!!.amount, 0.01)
        assertEquals("HDFC Bank", result.bankName)
        assertEquals(TransactionType.DEBIT, result.type)
        assertEquals("Entertainment", result.category)
    }

    @Test
    fun `HDFC salary credit`() {
        val sms = "INR 45000.00 credited to A/c XX1234 on 15-Jan-25 10:00:00 IST towards SAL CREDIT. Ref: NEFT ABC123456. Avl Bal: INR 52340.50."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(45000.0, result!!.amount, 0.01)
        assertEquals(TransactionType.CREDIT, result!!.type)
        assertEquals("HDFC Bank", result.bankName)
    }

    @Test
    fun `ICICI UPI Zomato food delivery`() {
        val sms = "Rs 320.00 debited from your ICICI Bank A/c XX4567 on 15-Jan-25 20:15 via UPI-ZOMATO-ORDER-93847561. Ref: ICI9384756102. Bal: Rs 8,920.50."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(320.0, result!!.amount, 0.01)
        assertEquals("ICICI Bank", result.bankName)
        assertEquals(TransactionType.DEBIT, result.type)
        assertEquals("Food Delivery", result.category)
    }

    @Test
    fun `ICICI UPI Swiggy food delivery`() {
        val sms = "₹280.00 debited from A/c XX4567 on 15-Jan-25 via UPI-SWIGGY-FOOD-93847561. Ref: 8475639201. Bal: ₹8,640.50."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(280.0, result!!.amount, 0.01)
        assertEquals("Food Delivery", result.category)
    }

    @Test
    fun `SBI UPI Blinkit grocery`() {
        val sms = "Rs.180.00 debited from your SBI A/c XX9012 on 15/01/25 18:45 via UPI-BLINKIT-ORDER-8374651920. Ref: 8475639201. Bal: Rs.7,840.50."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(180.0, result!!.amount, 0.01)
        assertEquals("SBI", result.bankName)
        assertEquals("Grocery", result.category)
    }

    @Test
    fun `SBI ATM withdrawal`() {
        val sms = "INR 5,000.00 debited from A/c XX3456 on 15-01-2025 12:00 at ATM HDFC BANK MUMBAI. Ref: ATM8374651. Bal: INR 13,750.25."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(5000.0, result!!.amount, 0.01)
        assertEquals("SBI", result.bankName)
        assertEquals("ATM", result.category)
    }

    @Test
    fun `Axis Bank BigBasket grocery`() {
        val sms = "INR 850.00 debited from Axis Bank A/c XX5678 on 15-Jan-25 at BIG BASKET. Ref: AX9876543210. Available balance: INR 22,150.75."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(850.0, result!!.amount, 0.01)
        assertEquals("Axis Bank", result.bankName)
        assertEquals("Grocery", result.category)
    }

    @Test
    fun `Axis Bank UPI Zepto grocery`() {
        val sms = "₹299.00 debited from your Axis Bank A/c XX5678 on 15/01/25 21:30 via UPI-ZEPTO-ORDER-7364829105. Ref: 6475839201. Bal: ₹21,851.75."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(299.0, result!!.amount, 0.01)
        assertEquals("Axis Bank", result.bankName)
        assertEquals("Grocery", result.category)
    }

    @Test
    fun `Kotak Myntra apparel`() {
        val sms = "INR 1,499.00 debited from Kotak A/c XX2345 on 15.01.2025 at MYNTRA RETAIL. Ref: KKB1234567. Bal: INR 8,501.25."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(1499.0, result!!.amount, 0.01)
        assertEquals("Kotak Bank", result.bankName)
        assertEquals("Apparel", result.category)
    }

    @Test
    fun `Yes Bank BookMyShow entertainment`() {
        val sms = "Rs.649.00 debited from Yes Bank A/c XX6789 on 15-Jan-25 16:20 at BOOKMYSHOW. Ref: YB8374651. Bal: Rs 14,351.50."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(649.0, result!!.amount, 0.01)
        assertEquals("Yes Bank", result.bankName)
        assertEquals("Entertainment", result.category)
    }

    @Test
    fun `IDFC Zepto grocery`() {
        val sms = "INR 2,100.00 debited from IDFC A/c XX4321 on 15-01-2025 at ZEPTO GROCERY. Ref: IDFC8374651. Available Bal: INR 9,900.00."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(2100.0, result!!.amount, 0.01)
        assertEquals("IDFC Bank", result.bankName)
        assertEquals("Grocery", result.category)
    }

    @Test
    fun `IndusInd electricity bill`() {
        val sms = "INR 450.00 debited from IndusInd Bank A/c XX8765 on 15/01/25 at ELECTRICITY BILL PAYMENT. Ref: IIB9283746. Bal: INR 15,550.25."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(450.0, result!!.amount, 0.01)
        assertEquals("IndusInd Bank", result.bankName)
        assertEquals("Bills & Utilities", result.category)
    }

    @Test
    fun `UPI peer to peer transfer`() {
        val sms = "INR 500.00 debited from A/c XX1234 on 15-Jan-25 14:00 via UPI sent to ARJUN MEHRA. Ref: 8475639201. Avl Bal: INR 9,500.25."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(500.0, result!!.amount, 0.01)
        assertEquals(TransactionType.DEBIT, result!!.type)
        assertEquals("Transfer", result.category)
    }

    @Test
    fun `Mutual fund SIP investment`() {
        val sms = "INR 5,000.00 debited from A/c XX1234 on 15-Jan-25 towards SIP - HDFC MUTUAL FUND. Ref: SIP8374651. Bal: INR 20,500.25."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(5000.0, result!!.amount, 0.01)
        assertEquals("Investment", result.category)
    }

    @Test
    fun `Cashback credit`() {
        val sms = "INR 150.00 credited to your A/c XX1234 on 15-Jan-25 as CASHBACK for transaction at AMAZON. Ref: CB8374651."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(150.0, result!!.amount, 0.01)
        assertEquals(TransactionType.CREDIT, result!!.type)
    }

    @Test
    fun `Refund credit`() {
        val sms = "INR 899.00 credited to A/c XX1234 on 15-Jan-25 as REFUND for FLIPKART order. Ref: RF8374651. Bal: INR 12,399.25."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(899.0, result!!.amount, 0.01)
        assertEquals(TransactionType.CREDIT, result!!.type)
    }

    @Test
    fun `Netflix auto-debit subscription`() {
        val sms = "INR 1,299.00 debited from A/c XX1234 on 15-Jan-25 via NACH - NETFLIX SUBSCRIPTION. Ref: NACH8374651. Bal: INR 8,201.25."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(1299.0, result!!.amount, 0.01)
        assertEquals("Subscriptions", result.category)
    }

    @Test
    fun `Amount with rupee symbol`() {
        val sms = "₹3,500.00 debited from A/c XX1234 on 15-Jan-25 at ZARA. Ref: 8475639201."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(3500.0, result!!.amount, 0.01)
        assertEquals("Apparel", result.category)
    }

    @Test
    fun `Amount with comma separator`() {
        val sms = "INR 1,25,000.00 debited from A/c XX1234 on 15-Jan-25 towards property payment. Ref: 8475639201."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(125000.0, result!!.amount, 0.01)
    }

    @Test
    fun `Amount without decimal`() {
        val sms = "INR 500 debited from A/c XX1234 on 15-Jan-25 at ZOMATO. Ref: 8475639201."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(500.0, result!!.amount, 0.01)
        assertEquals("Food Delivery", result.category)
    }

    @Test
    fun `Croma electronics`() {
        val sms = "INR 24,999.00 debited from A/c XX1234 on 15-Jan-25 at CROMA. Ref: 8475639201."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(24999.0, result!!.amount, 0.01)
        assertEquals("Electronics", result.category)
    }

    @Test
    fun `Uber transport`() {
        val sms = "INR 350.00 debited from A/c XX1234 on 15-Jan-25 at UBER. Ref: 8475639201."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(350.0, result!!.amount, 0.01)
        assertEquals("Transport", result.category)
    }

    @Test
    fun `Apollo pharmacy health`() {
        val sms = "INR 850.00 debited from A/c XX1234 on 15-Jan-25 at APOLLO PHARMACY. Ref: 8475639201."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals(850.0, result!!.amount, 0.01)
        assertEquals("Health", result.category)
    }

    @Test
    fun `Not a transaction SMS - OTP`() {
        val result = SmsParser.isTransactionSms("Your OTP for transaction is 847563. Valid for 10 minutes. Do not share with anyone.")
        assertFalse(result)
    }

    @Test
    fun `Not a transaction SMS - marketing`() {
        val result = SmsParser.isTransactionSms("Get 50% off on your next purchase! Use code SAVE50. Visit www.example.com")
        assertFalse(result)
    }

    @Test
    fun `Not a transaction SMS - balance only`() {
        val result = SmsParser.isTransactionSms("Your account balance is INR 25,000.00 as on 15-Jan-25.")
        assertFalse(result)
    }

    @Test
    fun `Failed transaction rejected`() {
        val result = SmsParser.isTransactionSms("Your transaction of INR 5,000.00 at MERCHANT failed due to insufficient balance.")
        assertFalse(result)
    }

    @Test
    fun `Declined transaction rejected`() {
        val result = SmsParser.isTransactionSms("Transaction of INR 2,000.00 was declined. Please try again.")
        assertFalse(result)
    }

    @Test
    fun `User mapping overrides default pattern`() {
        val userMappings = mapOf("zara" to "Shopping - Personal")

        val sms = "₹3,500.00 debited from A/c XX1234 on 15-Jan-25 at ZARA. Ref: 8475639201."
        val result = SmsParser.parseSms(sms, userMappings = userMappings)

        assertNotNull(result)
        assertEquals("Shopping - Personal", result!!.category)
        assertTrue(result.isCategoryLearned)
    }

    @Test
    fun `User mapping for custom merchant`() {
        val userMappings = mapOf("chai point" to "Food & Beverages")

        val sms = "INR 180.00 debited from A/c XX1234 on 15-Jan-25 at CHAI POINT KORAMANGALA. Ref: 8475639201."
        val result = SmsParser.parseSms(sms, userMappings = userMappings)

        assertNotNull(result)
        assertEquals("Food & Beverages", result!!.category)
        assertTrue(result.isCategoryLearned)
    }

    @Test
    fun `Uncategorized when no pattern matches`() {
        val sms = "INR 750.00 debited from A/c XX1234 on 15-Jan-25 at RANDOM SHOP XYZ. Ref: 8475639201."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals("Uncategorized", result!!.category)
        assertTrue(result.isUncategorized)
        assertFalse(result.isCategoryLearned)
    }

    @Test
    fun `Travel category - flight booking`() {
        val sms = "INR 4,500.00 debited from A/c XX1234 on 15-Jan-25 at MAKEMYTRIP FLIGHT. Ref: 8475639201."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals("Travel", result.category)
    }

    @Test
    fun `Education category - Byjus`() {
        val sms = "INR 12,000.00 debited from A/c XX1234 on 15-Jan-25 at BYJUS LEARNING. Ref: 8475639201."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals("Education", result.category)
    }

    @Test
    fun `Health category - Practo`() {
        val sms = "INR 500.00 debited from A/c XX1234 on 15-Jan-25 at PRACTO CONSULTATION. Ref: 8475639201."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals("Health", result.category)
    }

    @Test
    fun `Home and furniture - Pepperfry`() {
        val sms = "INR 8,999.00 debited from A/c XX1234 on 15-Jan-25 at PEPPERFRY FURNITURE. Ref: 8475639201."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals("Home & Furniture", result.category)
    }

    @Test
    fun `Beauty - Nykaa`() {
        val sms = "INR 1,299.00 debited from A/c XX1234 on 15-Jan-25 at NYKAA COSMETICS. Ref: 8475639201."
        val result = SmsParser.parseSms(sms, userMappings = emptyMappings)

        assertNotNull(result)
        assertEquals("Beauty & Personal Care", result.category)
    }

    @Test
    fun `getAllDefaultCategories returns all categories`() {
        val categories = SmsParser.getAllDefaultCategories()
        assertTrue(categories.contains("Food Delivery"))
        assertTrue(categories.contains("Dine Out"))
        assertTrue(categories.contains("Grocery"))
        assertTrue(categories.contains("Apparel"))
        assertTrue(categories.contains("Electronics"))
        assertTrue(categories.contains("Transport"))
        assertTrue(categories.contains("Bills & Utilities"))
        assertTrue(categories.contains("Entertainment"))
        assertTrue(categories.contains("Health"))
        assertTrue(categories.contains("Investment"))
        assertTrue(categories.contains("ATM"))
        assertTrue(categories.contains("Transfer"))
        assertTrue(categories.contains("Education"))
        assertTrue(categories.contains("Travel"))
        assertTrue(categories.contains("Subscriptions"))
        assertTrue(categories.contains("Home & Furniture"))
        assertTrue(categories.contains("Beauty & Personal Care"))
        assertEquals(17, categories.size)
    }
}
