package com.managemywallet.sms

import com.managemywallet.data.entity.TransactionType
import java.util.*
import java.util.regex.Pattern

data class ParsedTransaction(
    val amount: Double,
    val merchant: String,
    val category: String,
    val date: Date,
    val type: TransactionType,
    val bankName: String?,
    val accountNumber: String?,
    val referenceId: String?,
    val rawSms: String,
    val isCategoryLearned: Boolean = false,
    val isUncategorized: Boolean = false
)

object SmsParser {

    private val amountPattern = Pattern.compile(
        "(?:INR|Rs\\.?|₹)\\s*([\\d,]+\\.?\\d*)",
        Pattern.CASE_INSENSITIVE
    )

    private val accountPattern = Pattern.compile(
        "(?:A\\/c|Account|Acct)\\.?\\s*(?:no\\.?|number)?\\s*:?\\s*([\\dXx]+)",
        Pattern.CASE_INSENSITIVE
    )

    private val refPattern = Pattern.compile(
        "(?:Ref|Txn|Transaction)\\s*(?:Id|No|#|ID|Number)?\\.?\\s*:?\\s*([\\w-]+)",
        Pattern.CASE_INSENSITIVE
    )

    private val debitKeywords = listOf(
        "debited", "debit", "spent", "paid", "purchase", "payment",
        "withdrawn", "sent", "transfer", "upi", "swiped"
    )

    private val creditKeywords = listOf(
        "credited", "credit", "received", "deposited", "refunded",
        "cashback", "reversal"
    )

    private val failedTransactionKeywords = listOf(
        "failed", "failure", "declined", "rejected", "insufficient",
        "not successful", "could not be processed"
    )

    private val bankPatterns = mapOf(
        "HDFC Bank" to Pattern.compile("HDFC", Pattern.CASE_INSENSITIVE),
        "ICICI Bank" to Pattern.compile("ICICI", Pattern.CASE_INSENSITIVE),
        "SBI" to Pattern.compile("SBI|State Bank", Pattern.CASE_INSENSITIVE),
        "Axis Bank" to Pattern.compile("Axis", Pattern.CASE_INSENSITIVE),
        "Kotak Bank" to Pattern.compile("Kotak", Pattern.CASE_INSENSITIVE),
        "Yes Bank" to Pattern.compile("Yes Bank", Pattern.CASE_INSENSITIVE),
        "IDFC Bank" to Pattern.compile("IDFC", Pattern.CASE_INSENSITIVE),
        "IndusInd Bank" to Pattern.compile("IndusInd", Pattern.CASE_INSENSITIVE)
    )

    private val defaultCategoryPatterns = mapOf(
        "Food Delivery" to listOf("swiggy", "zomato", "uber eats", "dunzo food", "foodpanda"),
        "Dine Out" to listOf("restaurant", "cafe", "dining", "dine", "domino", "pizza", "mcdonald", "kfc", "burger king", "starbucks", "haldiram", "barbeque nation", "food court"),
        "Grocery" to listOf("bigbasket", "blinkit", "instamart", "zepto", "dmart", "grofers", "jio mart", "supermarket", "sahakari bhandar", "ratnadeep", "more", "spencer"),
        "Apparel" to listOf("myntra", "ajio", "zara", "h&m", "westside", "fabindia", "pantaloons", "shoppers stop", "lifestyle", "reliance trends", "max fashion", "nike", "adidas", "puma", "levi", "us polo"),
        "Electronics" to listOf("croma", "reliance digital", "vijay sales", "poorvika", "sangeetha", "apple", "samsung", "mi store", "realme", "oneplus"),
        "Transport" to listOf("uber", "ola", "rapido", "metro", "bus", "fuel", "petrol", "diesel", "parking", "redbus", "makemytrip transport", "irctc"),
        "Bills & Utilities" to listOf("electricity", "water", "gas", "broadband", "internet", "mobile", "recharge", "bill", "tata power", "bescom", "mahanagar gas", "indane", "hp gas", "jio recharge", "airtel recharge", "vodafone"),
        "Entertainment" to listOf("netflix", "amazon prime", "hotstar", "youtube", "bookmyshow", "pvr", "inox", "movie", "music", "spotify", "disney", "sony liv", "jio cinema", "gaana", "jio saavn"),
        "Health" to listOf("pharmacy", "medical", "hospital", "clinic", "doctor", "apollo", "medplus", "1mg", "netmeds", "pharmeasy", "practo", "diagnostic", "lab test"),
        "Investment" to listOf("mutual fund", "sip", "stock", "zerodha", "groww", "upstox", "invest", "insurance", "lic", "hdfc life", "sbi life", "icici prudential"),
        "ATM" to listOf("atm", "cash withdrawal"),
        "Transfer" to listOf("neft", "imps", "rtgs", "sent to", "transfer"),
        "Education" to listOf("school", "college", "university", "tuition", "coaching", "byjus", "vedantu", "unacademy", "coursera", "udemy", "fees"),
        "Travel" to listOf("flight", "hotel", "booking.com", "goibibo", "makemytrip", "oyo", "airbnb", "cleartrip", "yatra", "travel"),
        "Subscriptions" to listOf("subscription", "renewal", "membership", "nach", "auto debit", "standing instruction"),
        "Home & Furniture" to listOf("ikea", "godrej", "durian", "nilai", "urban ladder", "pepperfry", "homemaker", "furniture"),
        "Beauty & Personal Care" to listOf("nykaa", "lifestyle", "forest essentials", "bombay", "mamaearth", "laks", "salon", "spa", "grooming")
    )

    fun parseSms(
        smsBody: String,
        smsDate: Date? = null,
        userMappings: Map<String, String> = emptyMap()
    ): ParsedTransaction? {
        if (!isTransactionSms(smsBody)) return null

        val amount = extractAmount(smsBody) ?: return null
        val type = determineType(smsBody)
        val bankName = detectBank(smsBody)
        val accountNumber = extractAccountNumber(smsBody)
        val referenceId = extractReferenceId(smsBody)
        val merchant = extractMerchant(smsBody, bankName)

        val (category, isLearned) = categorize(merchant, smsBody, userMappings)
        val isUncategorized = category == "Uncategorized"

        return ParsedTransaction(
            amount = amount,
            merchant = merchant,
            category = category,
            date = smsDate ?: Date(),
            type = type,
            bankName = bankName,
            accountNumber = accountNumber,
            referenceId = referenceId,
            rawSms = smsBody,
            isCategoryLearned = isLearned,
            isUncategorized = isUncategorized
        )
    }

    private fun extractAmount(sms: String): Double? {
        val matcher = amountPattern.matcher(sms)
        if (matcher.find()) {
            val amountStr = matcher.group(1)?.replace(",", "") ?: return null
            return amountStr.toDoubleOrNull()
        }
        return null
    }

    private fun determineType(sms: String): TransactionType {
        val lowerSms = sms.lowercase()
        when {
            creditKeywords.any { lowerSms.contains(it) } -> return TransactionType.CREDIT
            debitKeywords.any { lowerSms.contains(it) } -> return TransactionType.DEBIT
            lowerSms.contains("balance") && lowerSms.contains("inr") -> return TransactionType.DEBIT
        }
        return TransactionType.DEBIT
    }

    private fun detectBank(sms: String): String? {
        for ((bankName, pattern) in bankPatterns) {
            if (pattern.matcher(sms).find()) {
                return bankName
            }
        }
        return null
    }

    private fun extractAccountNumber(sms: String): String? {
        val matcher = accountPattern.matcher(sms)
        return if (matcher.find()) matcher.group(1) else null
    }

    private fun extractReferenceId(sms: String): String? {
        val matcher = refPattern.matcher(sms)
        return if (matcher.find()) matcher.group(1) else null
    }

    private fun extractMerchant(sms: String, bankName: String?): String {
        var cleaned = sms
            .replace("debited", "", true)
            .replace("credited", "", true)
            .replace("INR", "", true)
            .replace("Rs.", "", true)
            .replace("₹", "", true)
            .replace("[\\d,]+\\.?\\d*".toRegex(), "")
            .replace("on.*card.*".toRegex(), "")
            .replace(bankName ?: "", "", true)

        val merchantExtractPatterns = listOf(
            "at\\s+(.+?)(?:\\s+on\\s|\\s+for\\s|\\s+to\\s|\\s+via\\s|\\s+Ref\\s|\\.|\$)".toRegex(RegexOption.IGNORE_CASE),
            "towards\\s+(.+?)(?:\\s+on\\s|\\s+for\\s|\\.|\$)".toRegex(RegexOption.IGNORE_CASE),
            "UPI-(.+?)(?:-\\d|\\s|\$)".toRegex(RegexOption.IGNORE_CASE)
        )

        for (pattern in merchantExtractPatterns) {
            val match = pattern.find(cleaned)
            if (match != null) {
                val rawMerchant = match.groupValues[1].trim()
                if (rawMerchant.length > 1 && rawMerchant.length < 50) {
                    return rawMerchant.replace("-", " ").replace("_", " ").trim()
                }
            }
        }

        cleaned = cleaned
            .replace("on\\s+\\d".toRegex(), "")
            .replace("towards".toRegex(RegexOption.IGNORE_CASE), "")
            .replace("via\\s+upi".toRegex(), "")
            .trim()

        val parts = cleaned.split(".", ",", ";")
        for (part in parts) {
            val trimmed = part.trim()
            if (trimmed.isNotBlank() && trimmed.length > 1 && trimmed.length < 50) {
                return trimmed.replace("-", " ").replace("_", " ").trim()
            }
        }

        return "Unknown Merchant"
    }

    private fun categorize(
        merchantName: String,
        smsBody: String,
        userMappings: Map<String, String>
    ): Pair<String, Boolean> {
        val lowerMerchant = merchantName.lowercase()
        val lowerSms = smsBody.lowercase()

        for ((pattern, category) in userMappings) {
            if (lowerMerchant.contains(pattern.lowercase())) {
                return Pair(category, true)
            }
        }

        for ((category, keywords) in defaultCategoryPatterns) {
            if (keywords.any { lowerMerchant.contains(it) || lowerSms.contains(it) }) {
                return Pair(category, false)
            }
        }

        return Pair("Uncategorized", false)
    }

    fun isTransactionSms(smsBody: String): Boolean {
        val lowerSms = smsBody.lowercase()

        if (failedTransactionKeywords.any { lowerSms.contains(it) }) {
            return false
        }

        val hasAmount = amountPattern.matcher(smsBody).find()
        val hasTransactionKeyword = debitKeywords.any { lowerSms.contains(it) } ||
                creditKeywords.any { lowerSms.contains(it) }
        val hasBankKeyword = bankPatterns.values.any { it.matcher(smsBody).find() }

        val isPromo = listOf("offer", "discount", "coupon", "cashback available", "use code").all { lowerSms.contains(it) }
        val isBalanceOnly = lowerSms.contains("balance") && !hasTransactionKeyword && !lowerSms.contains("debit") && !lowerSms.contains("credit")

        return hasAmount && (hasTransactionKeyword || hasBankKeyword) && !isBalanceOnly
    }

    fun getAllDefaultCategories(): List<String> {
        return defaultCategoryPatterns.keys.sorted()
    }
}
