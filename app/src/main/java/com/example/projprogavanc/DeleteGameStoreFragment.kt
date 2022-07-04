package com.example.projprogavanc

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.projprogavanc.databinding.FragmentDeleteGameStoreBinding


class DeleteGameStoreFragment : Fragment() {
    private var _binding : FragmentDeleteGameStoreBinding? = null

    private val binding get() = _binding!!

    private lateinit var gameStore: GameStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDeleteGameStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.currMenuId = R.menu.delete_menu
        activity.updateTitle(getString(R.string.delete_gameStore))

        gameStore = DeleteGameStoreFragmentArgs.fromBundle(arguments!!).gamestore

        binding.textViewGSGameName.text = gameStore.game.name
        binding.textViewGSStoreName.text = gameStore.store.name
        binding.textViewGSPrice.text = gameStore.preco.toString()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processOptionMenu(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.action_delete ->{
                deleteGameStore()
                true

            }
            R.id.action_cancel ->{
                backtoGameStoreList()
                true
            }
            else -> false
        }

    private fun deleteGameStore() {
        val alertdialog = AlertDialog.Builder(requireContext())

        alertdialog.apply {
            setMessage(getString(R.string.delete_gameStore))
            setMessage(getString(R.string.delete_gamestore_question))
            setNegativeButton(
                R.string.menu_cancel,
                DialogInterface.OnClickListener { dialogInterface, i -> })
            setPositiveButton(
                R.string.delete_text,
                DialogInterface.OnClickListener { dialogInterface, i -> confirmDeleteGameStore()})
            show()
        }

    }

    private fun confirmDeleteGameStore() {
        val gameStoreAddress = Uri.withAppendedPath(ContentProviderGameStore.GAME_STORES_ADDRESS, "${gameStore.id}")
        val DeletedEntries = requireActivity().contentResolver.delete(gameStoreAddress, null,null)

        if(DeletedEntries == null){

            Toast.makeText(requireContext(), R.string.General_error, Toast.LENGTH_LONG).show()
            return

        }

        Toast.makeText(requireContext(), getString(R.string.delete_gameStore_success), Toast.LENGTH_LONG).show()
        backtoGameStoreList()
    }


    private fun backtoGameStoreList() {
        (requireActivity() as MainActivity).updateTitle(getString(R.string.Wishlist_title))
        findNavController().navigate(R.id.action_deleteGameStoreFragment_to_wishlist)
    }
}