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
import androidx.loader.app.LoaderManager
import androidx.navigation.fragment.findNavController
import com.example.projprogavanc.databinding.FragmentDeleteStoreBinding

class DeleteStoreFragment : Fragment() {
    private var _binding: FragmentDeleteStoreBinding? = null

    private val binding get() = _binding!!

    private lateinit var store: Store

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDeleteStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.currMenuId = R.menu.delete_menu
        activity.updateTitle(getString(R.string.fragment_delete_store))

        store = DeleteStoreFragmentArgs.fromBundle(arguments!!).store

        binding.textViewStoreName.text = store.name
        binding.textViewStoreAddress.text = store.address
        binding.textViewStoreType.text = store.type.type
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processOptionMenu(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.action_delete ->{
                deleteStore()
                true

            }
            R.id.action_cancel ->{
                backtoStoreList()
                true
            }
            else -> false
        }

    private fun deleteStore() {
        val alertdialog = AlertDialog.Builder(requireContext())

        alertdialog.apply {
            setMessage(getString(R.string.delete_store_title))
            setMessage(getString(R.string.delete_store_question))
            setNegativeButton(
                R.string.menu_cancel,
                DialogInterface.OnClickListener { dialogInterface, i -> })
            setPositiveButton(
                R.string.delete_text,
                DialogInterface.OnClickListener { dialogInterface, i -> confirmDeleteStore()})
            show()
        }
    }

    private fun confirmDeleteStore() {
        val storeAddress = Uri.withAppendedPath(ContentProviderGameStore.STORES_ADDRESS, "${store.id}")
        val DeletedEntries = requireActivity().contentResolver.delete(storeAddress, null,null)

        if(DeletedEntries == null){

            Toast.makeText(requireContext(), R.string.General_error, Toast.LENGTH_LONG).show()
            return

        }

        Toast.makeText(requireContext(), getString(R.string.delete_store_success), Toast.LENGTH_LONG).show()
        backtoStoreList()
    }


    private fun backtoStoreList() {
        (requireActivity() as MainActivity).updateTitle(getString(R.string.fragment_store_list))
        findNavController().navigate(R.id.action_deleteStoreFragment_to_StoreList)
    }
}