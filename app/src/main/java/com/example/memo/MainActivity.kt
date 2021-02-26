package com.example.memo.view

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.memo.R
import com.example.memo.db.ArticleDB

class MainActivity : AppCompatActivity() {

    //初期化
    private val actionButton:FloatingActionButton by lazy{ findViewById(R.id.actionButton) }
    private val backButton:FloatingActionButton by lazy{ findViewById(R.id.backButton) }
    private val navController: NavController by lazy{ Navigation.findNavController(this, R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFirstFragmentDisplay()

        backButton.setOnClickListener {
            navController.currentDestination?.let {
                when (it.id) {
                    R.id.SecondFragment -> {
                        initFirstFragmentDisplay()
                        navController.navigate(R.id.action_SecondFragment_to_FirstFragment)
                    }
                }
            }
        }

        //アクションボタン
        actionButton.setOnClickListener {
            navController.currentDestination?.let{
                when(it.id){
                    R.id.FirstFragment->{
                        initSecondFragmentDisplay()
                        navController.navigate(R.id.action_FirstFragment_to_SecondFragment)
                    }
                    R.id.SecondFragment->{
                        initFirstFragmentDisplay()
                        saveText()
                        navController.navigate(R.id.action_SecondFragment_to_FirstFragment)
                    }
                }
            }
        }

    }

    //firstFragmentのボタン表示の初期化
    private fun initFirstFragmentDisplay(){
        backButtonVisibility(false)
        changeButtonImage(android.R.drawable.ic_menu_edit)
    }
    //SecondFragmentのボタン表示の初期化
    private fun initSecondFragmentDisplay(){
        backButtonVisibility(true)
        changeButtonImage(android.R.drawable.ic_menu_save)
    }
    //アクションボタンの画像変更
    private fun changeButtonImage(image:Int){
        actionButton.setImageResource(image)
    }

    //戻るボタンの表示と非表示
    private fun backButtonVisibility(visibility:Boolean){
        if(visibility){
            backButton.show()
        } else {
            backButton.hide()
        }
    }

    //SecondFragmentを取得
    private fun getSecondFragment():SecondFragment?{
        var fragment:SecondFragment? = null
        this.supportFragmentManager.fragments.first().childFragmentManager.fragments.forEach {
            if(it is SecondFragment){ fragment = it }
        }
        return fragment
    }

    //テキスト保存
    private fun saveText(){
        val secondFragment = getSecondFragment()

        val data = secondFragment?.getArticleData()
        if(data != null){
            ArticleDB(this).insertOrUpdate(data)
        }
        secondFragment?.closeKeyboard()
    }

    //選択時処理
    fun selectItem(id:Long, content:String) {
        initSecondFragmentDisplay()

        val args = Bundle().also {
            it.putLong(SELECT_ID, id)
            it.putString(SELECT_CONTENTS, content)
        }

        navController.navigate(R.id.action_FirstFragment_to_SecondFragment, args)
    }
        companion object {
            const val SELECT_ID = "select_id"
            const val SELECT_CONTENTS = "select_contents"
        }
}


