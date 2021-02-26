package com.example.memo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memo.R
import com.example.memo.db.ArticleDB
import com.example.memo.db.ArticleData
import com.example.memo.view.ArticleAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), ArticleAdapter.OnItemSelectedListener {

    private var articleList = mutableListOf<ArticleData>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.List).let{
            //RecyclerViewのサイズを固定し、パフォーマンスが向上する
            it.setHasFixedSize(true)

            activity?.let{ act->
                it.layoutManager = LinearLayoutManager(act)
                articleList = ArticleDB(act).selectList()

                val adapter = ArticleAdapter(act, articleList)
                adapter.onItemSelectedListener = this
                it.adapter = adapter
            }
        }
    }

    override fun onSelect(id: Long?, content:String) {
        (activity as? MainActivity)?.let{
            if(id != null){ it.selectItem(id, content) }
        }
    }
}