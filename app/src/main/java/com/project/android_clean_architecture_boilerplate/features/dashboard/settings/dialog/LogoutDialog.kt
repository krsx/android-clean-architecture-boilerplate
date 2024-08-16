package com.project.android_clean_architecture_boilerplate.features.dashboard.settings.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.project.android_clean_architecture_boilerplate.R
import com.project.android_clean_architecture_boilerplate.features.dashboard.settings.SettingsViewModel
import com.project.android_clean_architecture_boilerplate.features.splash.SplashActivity
import com.project.core.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LogoutDialog : DialogFragment() {
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it, R.style.TransparentDialogTheme)
            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.dialog_logout, null)
            builder.setView(view)
            builder.setCancelable(false)

            handleButton(view)

            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }

    private fun handleButton(view: View) {
        val btnYes = view.findViewById<Button>(R.id.btn_logout_yes)
        val btnNo = view.findViewById<Button>(R.id.btn_logout_no)

        btnNo.setOnClickListener {
            dismiss()
        }

        btnYes.setOnClickListener {
            settingsViewModel.deleteToken().observe(this){result ->
                when(result){
                    true -> {
                        val intentToSplash = Intent(activity, SplashActivity::class.java)
                        intentToSplash.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intentToSplash)
                    }
                    false -> {
                        dismiss()

                        val message = "Failed to delete token"
                        Timber.tag("LogoutDialog").e("deleteToken: $message")
                        activity?.showToast(message)
                    }
                }
            }
        }
    }
}