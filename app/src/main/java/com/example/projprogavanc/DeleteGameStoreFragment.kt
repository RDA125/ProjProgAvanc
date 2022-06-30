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

    private lateinit var gameStore: Game_Store

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

        gameStore = DeleteGameStoreFragmentArgs.fromBundle(arguments!!).gameStore

        binding.textViewGSGameName.text = gameStore.game.name
        binding.textViewGSStoreName.text = gameStore.store.name
        binding.textViewGSPrice.text = gameStore.preco.toString()

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
            setMessage(R.string.delete_game_title)
            setMessage("Are you sure you want to delete this game?")
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

        Toast.makeText(requireContext(), "Game Store connection successfully deleted", Toast.LENGTH_LONG).show()
        backtoGameStoreList()
        //TODO("Prepare insert/ Delete Game and store interfaces and change gamestore interface.")
    }


    private fun backtoGameStoreList() {
        findNavController().navigate(R.id.action_InsertGameStoreFragment_to_nav_game_store)
    }
}