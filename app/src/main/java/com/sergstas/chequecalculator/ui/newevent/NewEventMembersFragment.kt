package com.sergstas.chequecalculator.ui.newevent

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.databinding.FragmentNewEventMembersBinding
import com.sergstas.chequecalculator.databinding.ItemSingleButtonTitleBinding
import com.sergstas.chequecalculator.ui.newevent.models.SingleButtonItem
import com.sergstas.chequecalculator.ui.newevent.viewholders.SingleButtonItemViewHolder
import com.sergstas.chequecalculator.util.extensions.findAppComponent
import com.sergstas.chequecalculator.util.rv.AbstractAdapter
import com.sergstas.chequecalculator.util.spinner.DefaultSpinnerAdapter
import com.sergstas.chequecalculator.vm.newevent.NewEventViewModel
import com.sergstas.domain.models.UserData

class NewEventMembersFragment: Fragment(R.layout.fragment_new_event_members) {
    private val binding by viewBinding(FragmentNewEventMembersBinding::bind)
    private val viewModel by activityViewModels<NewEventViewModel> {
        requireContext().findAppComponent().viewModelFactory()
    }
    private lateinit var adapter: AbstractAdapter<SingleButtonItem, SingleButtonItemViewHolder>

    private val availableUsers get() =
        viewModel.allUsers.value?.toMutableList()?.apply {
            removeAll(viewModel.members.value ?: emptyList())
        } ?: emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setView()
        subscribe()
    }

    private fun setAdapter() {
        adapter = AbstractAdapter.create { SingleButtonItemViewHolder(
            ItemSingleButtonTitleBinding.inflate(requireActivity().layoutInflater, it, false)
        ) }.apply { bindToRecycler(binding.neMembersRvUsers) }
    }

    private fun subscribe() = with(viewModel) {
        members.observe(viewLifecycleOwner) {
            val mapped = it?.map(this@NewEventMembersFragment::toListItem) ?: return@observe
            if (adapter.currentList != mapped) {
                adapter.submitList(it.map(this@NewEventMembersFragment::toListItem))
            }
            updateSpinner()
            updateNextButton()
        }

        allUsers.observe(viewLifecycleOwner) {
            updateSpinner()
        }
    }

    private fun setView() = with(binding) {
        neMembersBNext.setOnClickListener { nextPage() }
        neMembersBAdd.setOnClickListener {
            val user = neMembersSpinnerUser.selectedItem as? String ?: return@setOnClickListener
            viewModel.addMember(UserData(user))
        }
        updateNextButton()
        updateSpinner()
    }

    private fun updateSpinner() {
        val list = availableUsers.map { it.name }
        binding.neMembersSpinnerUser.isVisible = list.isNotEmpty()
        binding.neMembersTvNoUsers.isVisible = list.isEmpty()
        binding.neMembersSpinnerUser.adapter = DefaultSpinnerAdapter(list)
        updateAddButton()
    }

    private fun updateAddButton() {
        val value = availableUsers.isNotEmpty()
        binding.neMembersBAdd.isEnabled = value
    }

    private fun updateNextButton() {
        val value = viewModel.members.value?.isNotEmpty() ?: false
        binding.neMembersBNext.isEnabled = value
    }

    private fun toListItem(data: UserData) =
        SingleButtonItem(
            title = data.name,
            buttonText = getString(R.string.button_remove),
            onButtonPressed = { viewModel.deleteMember(data) },
        )

    private fun nextPage() =
        findNavController().navigate(R.id.action_newEventMembersFragment_to_newEventReceiptsFragment)
}