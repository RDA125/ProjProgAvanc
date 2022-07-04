package com.example.projprogavanc

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.projprogavanc.databinding.FragmentDeleteGameBinding


class DeleteGameFragment: Fragment()  {

    private var _binding : FragmentDeleteGameBinding? = null

    private val binding get() = _binding!!

    private lateinit var game: Game

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentDeleteGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.currMenuId = R.menu.delete_menu
        activity.updateTitle(getString(R.string.fragment_delete_game))

        game = DeleteGameFragmentArgs.fromBundle(arguments!!).game

        binding.textViewGameName.text = game.name
        binding.textViewGameType.text = game.type.type
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processOptionMenu(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.action_delete ->{
                deleteGame()
                true

            }
            R.id.action_cancel ->{
                backtoGameList()
                true
            }
            else -> false
        }

    private fun deleteGame() {
        val alertdialog = AlertDialog.Builder(requireContext())

        alertdialog.apply {
            setMessage(R.string.delete_game_title)
            setMessage(getString(R.string.delete_game_question))
            setNegativeButton(
                R.string.menu_cancel,
                DialogInterface.OnClickListener { dialogInterface, i -> })
            setPositiveButton(
                R.string.delete_text,
                DialogInterface.OnClickListener { dialogInterface, i -> confirmDeleteGame()})
            show()
        }

    }

    private fun confirmDeleteGame() {
        val gameAddress = Uri.withAppendedPath(ContentProviderGameStore.GAMES_ADDRESS, "${game.id}")
        val DeletedEntries = requireActivity().contentResolver.delete(gameAddress, null,null)

        if(DeletedEntries == null){

            Toast.makeText(requireContext(), R.string.General_error, Toast.LENGTH_LONG).show()
            return

        }

        Toast.makeText(requireContext(), getString(R.string.delete_game_success), Toast.LENGTH_LONG).show()
        backtoGameList()
    }

    private fun backtoGameList() {
        (requireActivity() as MainActivity).updateTitle(getString(R.string.fragment_game_list))
        findNavController().navigate(R.id.action_deleteGameFragment_to_GameList)
    }
}