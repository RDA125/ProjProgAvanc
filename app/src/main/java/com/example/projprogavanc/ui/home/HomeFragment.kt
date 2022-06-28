package com.example.projprogavanc.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projprogavanc.MainActivity
import com.example.projprogavanc.R
import com.example.projprogavanc.WishlistAdapter
import com.example.projprogavanc.databinding.FragmentHomeBinding
import com.example.projprogavanc.ui.gallery.WishlistFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.currMenuId = R.menu.main

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processOptionMenu(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.action_settings -> true
            else -> false
        }

}