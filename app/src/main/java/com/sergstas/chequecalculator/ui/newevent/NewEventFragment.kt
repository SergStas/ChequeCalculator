package com.sergstas.chequecalculator.ui.newevent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.databinding.FragmentNewEventBinding

class NewEventFragment: Fragment(R.layout.fragment_new_event) {
    private val binding by viewBinding(FragmentNewEventBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        val navHost = childFragmentManager.findFragmentById(R.id.ne_fragment_host) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.neBnvMenu, navController)
    }
}