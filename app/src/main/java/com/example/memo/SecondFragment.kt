package com.example.memo.view

import android.content.Context
import android.os.Bundle
import android.view.InputDevice
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.memo.R
import com.example.memo.db.ArticleData

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var inputText: EditText
    private var currentId:Long? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputText = view.findViewById(R.id.inputText)

        currentId = this.arguments?.getLong(MainActivity.SELECT_ID)
        if (currentId != null) {
            inputText.setText(this.arguments?.getString(MainActivity.SELECT_CONTENTS))
        } else {
            inputText.setText("")
        }
    }

    fun getArticleData():ArticleData? {
        val text = inputText.text.toString()

        return if(text.isNotEmpty()) { ArticleData(currentId, text) } else { null }
    }

    fun closeKeyboard() {
        val view = activity?.currentFocus
        if(view != null) {
            val manager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        manager?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}