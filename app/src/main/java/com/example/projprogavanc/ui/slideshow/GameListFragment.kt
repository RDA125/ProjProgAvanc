package com.example.projprogavanc.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projprogavanc.databinding.FragmentGameListBinding

class GameListFragment : Fragment() {

    private var _binding: FragmentGameListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val slideshowViewModel = ViewModelProvider(this).get(GameListViewModel::class.java)

        _binding = FragmentGameListBinding.inflate(inflater, container, false)
        return binding.root

        /*val textView: TextView = binding.textslide
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}