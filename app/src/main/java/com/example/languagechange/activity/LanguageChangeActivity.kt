package com.example.languagechange.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.languagechange.Listener.OnLanguageChangeListener
import com.example.languagechange.R
import com.example.languagechange.adapter.LanguageChangeAdapter
import com.example.languagechange.databinding.ActivityLanguageChangeBinding
import com.example.languagechange.fragment.LanguageChangeFragment
import com.example.languagechange.model.LanguageModel

class LanguageChangeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLanguageChangeBinding

    var languageChangeAdapter: LanguageChangeAdapter? = null
    private val languages = mutableListOf<LanguageModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageChangeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setActivityRecyclerView()
        addLanguage()

        initWidget()
    }

    private fun initWidget() {
        binding.btnLanguageChange.setOnClickListener {
            val languageChangeFragment = LanguageChangeFragment()
            languageChangeFragment.show(supportFragmentManager, languageChangeFragment.tag)
        }
    }

    private fun setActivityRecyclerView() {
        addLanguage()
        if(languageChangeAdapter == null) {
            languageChangeAdapter = LanguageChangeAdapter(languages)
        }
        languageChangeAdapter?.let { adapter ->
            adapter.setItemClickListener(object : OnLanguageChangeListener {
                override fun onChangeClick(position: Int) {
                    when(languages[position].languageType) {
                        "ko" -> {
                            Log.d("LanguageLog", "Position 0 Click!!")
                        }
                        "en" -> {
                            Log.d("LanguageLog", "Position 1 Click!!")
                        }
                        else -> {
                            Log.e("LanguageLog", "setLocate Error!!")
                        }
                    }
                }
            })

            binding.rvLanguageChangeActivity.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(this@LanguageChangeActivity)
            }
            adapter.notifyDataSetChanged()
        }
    }

    /* 언어 선택 뷰에 표시할 아이템(언어)추가 */
    private fun addLanguage() {
        languages.clear()
        languages.add(LanguageModel("한국어", "ko", false))
        languages.add(LanguageModel("English", "en", false))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity :: class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        languageChangeAdapter = null
    }
}