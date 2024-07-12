package com.example.languagechange.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.languagechange.Listener.OnLanguageChangeListener
import com.example.languagechange.adapter.LanguageChangeAdapter
import com.example.languagechange.databinding.ActivityLanguageChangeBinding
import com.example.languagechange.fragment.LanguageChangeFragment
import com.example.languagechange.model.LanguageModel
import com.example.languagechange.util.LanguageChangeUtil
import com.example.languagechange.util.datastore
import com.example.languagechange.util.updateSavedLanguage
import kotlinx.coroutines.runBlocking

class LanguageChangeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLanguageChangeBinding

    var languageChangeAdapter: LanguageChangeAdapter? = null
    private val languages = mutableListOf<LanguageModel>()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageChangeUtil.updateBaseContextLocale(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageChangeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setActivityRecyclerView()
        addLanguage()
        languageIsChecked()

        initWidget()
    }

    private fun initWidget() {
        binding.btnLanguageChange.setOnClickListener {
            val languageChangeFragment = LanguageChangeFragment()
            languageChangeFragment.show(supportFragmentManager, languageChangeFragment.tag)
        }
    }

    private fun setActivityRecyclerView() {
        clearItem()
        addLanguage()
        if(languageChangeAdapter == null) {
            languageChangeAdapter = LanguageChangeAdapter(languages)
        }
        languageChangeAdapter?.let { adapter ->
            adapter.setItemClickListener(object : OnLanguageChangeListener {
                override fun onChangeClick(position: Int) {
                    val selectedLanguage = languages[position].languageType
                    setLocate(selectedLanguage)
                }
            })

            binding.rvLanguageChangeActivity.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(this@LanguageChangeActivity)
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun languageIsChecked() {
        if(LanguageChangeUtil.savedLanguage == LanguageChangeUtil.languageDefault) {
            languages[0].isLanguageChecked = LanguageChangeUtil.sysLanguage == LanguageChangeUtil.languageKo
            languages[1].isLanguageChecked = LanguageChangeUtil.sysLanguage == LanguageChangeUtil.languageEn
        } else {
            languages[0].isLanguageChecked = LanguageChangeUtil.savedLanguage == LanguageChangeUtil.languageKo
            languages[1].isLanguageChecked = LanguageChangeUtil.savedLanguage == LanguageChangeUtil.languageEn
        }
    }



    /* 언어 선택 뷰에 표시할 아이템(언어)추가 */
    private fun addLanguage() {
        languages.clear()
        languages.add(LanguageModel("한국어", LanguageChangeUtil.languageKo, false))
        languages.add(LanguageModel("English", LanguageChangeUtil.languageEn, false))
    }


    private fun clearItem() {
        languages.clear()
    }

    fun setLocate(changeLanguage: String) {
        /* 안드로이드 시스템 언어와 같은 언어를 선택하거나,
         현재 저장되어 적용된 언어와 같은 언어로 선택시 변경 못하게 막는 분기처리 */
        if ((LanguageChangeUtil.savedLanguage != "DEFAULT" && LanguageChangeUtil.savedLanguage == changeLanguage)
            ||(LanguageChangeUtil.savedLanguage == "DEFAULT" && LanguageChangeUtil.sysLanguage == changeLanguage)
        ) {
            Log.e("LanguageLog", "setLocate() Error!")
            return
        }
        runBlocking {
            datastore.updateSavedLanguage(changeLanguage)
        }
        setLanguageChangeRestartApp()
    }

    private fun setLanguageChangeRestartApp() {
        val intent: Intent =
            baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName) as Intent
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        overridePendingTransition(0, 0)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
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