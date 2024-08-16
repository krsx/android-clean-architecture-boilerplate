package com.project.android_clean_architecture_boilerplate.features.dashboard.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.project.android_clean_architecture_boilerplate.R
import com.project.android_clean_architecture_boilerplate.databinding.FragmentSettingsBinding
import com.project.android_clean_architecture_boilerplate.features.dashboard.settings.dialog.LogoutDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val settingsViewModel: SettingsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleLogout()
    }

    private fun handleLogout() {
        binding.btnLogout.setOnClickListener {
            Timber.tag("TEST").e("Token: Halo")

            LogoutDialog().show(parentFragmentManager, "Logout Dialog")
        }
    }
}