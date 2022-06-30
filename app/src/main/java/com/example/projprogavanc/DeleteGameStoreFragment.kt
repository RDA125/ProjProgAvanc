package com.example.projprogavanc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
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

    }

    companion object {

    }
}