package com.sergstas.chequecalculator.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.databinding.FragmentMenuBinding

class MenuFragment: Fragment(R.layout.fragment_menu) {
    private val binding by viewBinding(FragmentMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHost = childFragmentManager.findFragmentById(R.id.menu_fragment_host) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.menuBnvNav, navController)
    }
}