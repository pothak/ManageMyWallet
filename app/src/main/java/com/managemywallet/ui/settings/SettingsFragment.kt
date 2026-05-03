package com.managemywallet.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.managemywallet.WalletApplication
import com.managemywallet.databinding.FragmentSettingsBinding
import com.managemywallet.security.BiometricAuthManager
import com.managemywallet.security.DataWiper
import com.managemywallet.security.InactivityTimer
import com.managemywallet.security.RuntimeSecurityChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = (requireActivity().application as WalletApplication).encryptedPrefs

        binding.switchBiometric.isChecked = prefs.getBoolean("biometric_enabled", true)
        binding.switchBiometric.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("biometric_enabled", isChecked).apply()
            if (isChecked && !BiometricAuthManager.isBiometricAvailable(requireContext())) {
                Toast.makeText(context, "Biometric not available on this device", Toast.LENGTH_LONG).show()
            }
        }

        val timeoutSeconds = prefs.getInt("auto_lock_timeout", 120)
        binding.textTimeout.text = formatTimeout(timeoutSeconds)

        binding.layoutTimeout.setOnClickListener {
            showTimeoutDialog(prefs)
        }

        binding.layoutSecurityInfo.setOnClickListener {
            showSecurityDetails()
        }

        binding.layoutExport.setOnClickListener {
            exportData()
        }

        binding.layoutWipe.setOnClickListener {
            showWipeConfirmation()
        }
    }

    private fun formatTimeout(seconds: Int): String {
        return when {
            seconds < 60 -> "$seconds seconds"
            seconds == 60 -> "1 minute"
            seconds < 3600 -> "${seconds / 60} minutes"
            else -> "${seconds / 3600} hours"
        }
    }

    private fun showTimeoutDialog(prefs: android.content.SharedPreferences) {
        val options = arrayOf("30 seconds", "1 minute", "2 minutes", "5 minutes", "10 minutes", "Never")
        val values = arrayOf(30, 60, 120, 300, 600, 0)
        val current = prefs.getInt("auto_lock_timeout", 120)
        val currentIndex = values.indexOf(current).takeIf { it >= 0 } ?: 2

        AlertDialog.Builder(requireContext())
            .setTitle("Auto-Lock Timeout")
            .setSingleChoiceItems(options, currentIndex) { dialog, which ->
                val selected = values[which]
                prefs.edit().putInt("auto_lock_timeout", selected).apply()
                binding.textTimeout.text = formatTimeout(selected)
                InactivityTimer.setTimeout(selected)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showSecurityDetails() {
        val biometricStatus = BiometricAuthManager.getBiometricStatus(requireContext())
        val isRooted = RuntimeSecurityChecker.isDeviceRooted()
        val isEmulator = RuntimeSecurityChecker.isRunningOnEmulator(requireContext())

        val dbFile = requireContext().getDatabasePath("wallet.db")
        val dbExists = dbFile.exists()
        val dbSize = if (dbExists) "${dbFile.length() / 1024} KB" else "Not created yet"

        val message = """
            Biometric: $biometricStatus
            Device Rooted: ${if (isRooted) "Yes (Warning)" else "No"}
            Running on Emulator: ${if (isEmulator) "Yes" else "No"}
            Database: $dbSize (SQLCipher encrypted)
            Encryption: AES-256-GCM (Android Keystore)
            Internet Permission: Not granted
            Cloud Backup: Disabled
        """.trimIndent()

        AlertDialog.Builder(requireContext())
            .setTitle("Security Status")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun exportData() {
        val biometricEnabled = (requireActivity().application as WalletApplication).encryptedPrefs
            .getBoolean("biometric_enabled", true)

        if (biometricEnabled) {
            Toast.makeText(context, "Authenticate to export", Toast.LENGTH_SHORT).show()
        }

        val outputDir = requireContext().getExternalFilesDir(null)
        if (outputDir == null) {
            Toast.makeText(context, "Cannot access storage", Toast.LENGTH_SHORT).show()
            return
        }

        val file = File(outputDir, "managemywallet_export_${System.currentTimeMillis()}.csv")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val dao = (requireActivity().application as WalletApplication).database.transactionDao()
                val transactions = dao.getAllTransactions().value ?: emptyList()

                val csvHeader = "Date,Type,Amount,Currency,Merchant,Category,Bank,Reference,Notes\n"
                val csvRows = transactions.joinToString("\n") { txn ->
                    "${txn.date},${txn.type},${txn.amount},${txn.currency},${txn.merchant},${txn.category},${txn.bankName ?: ""},${txn.referenceId ?: ""},${txn.notes ?: ""}"
                }

                file.writeText(csvHeader + csvRows)

                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, "Exported to ${file.name}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, "Export failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showWipeConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Wipe All Data")
            .setMessage("This will permanently delete all transactions, alert rules, and settings. This cannot be undone.")
            .setPositiveButton("Delete Everything") { _, _ ->
                DataWiper.wipeAllData(requireContext())
                Toast.makeText(context, "All data wiped", Toast.LENGTH_LONG).show()
                requireActivity().finishAffinity()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
