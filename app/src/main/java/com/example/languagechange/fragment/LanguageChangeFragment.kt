package com.example.languagechange.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.languagechange.Listener.OnLanguageChangeListener
import com.example.languagechange.R
import com.example.languagechange.activity.LanguageChangeActivity
import com.example.languagechange.adapter.LanguageChangeAdapter
import com.example.languagechange.databinding.FragmentLanguageChangeBinding

import com.example.languagechange.model.LanguageModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LanguageChangeFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentLanguageChangeBinding? = null
    private val binding get() = _binding!!

    private var languageChangeAdapter: LanguageChangeAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLanguageChangeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun getTheme(): Int = R.style.LanguageBottomSheetDialogThem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetRecyclerView()
    }

    private fun setBottomSheetRecyclerView() {
        setActivityAdapter()
        languageChangeAdapter?.let { adapter ->
            binding.rvLanguageChange.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(this.context)
            }
        }
    }

    private fun setActivityAdapter() {
        if(activity is LanguageChangeActivity) {
            languageChangeAdapter = (activity as LanguageChangeActivity).languageChangeAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        languageChangeAdapter = null
    }
}