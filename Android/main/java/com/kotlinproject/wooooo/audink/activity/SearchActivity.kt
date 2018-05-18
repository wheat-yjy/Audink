package com.kotlinproject.wooooo.audink.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.support.v7.widget.SearchView
import android.widget.ImageView
import android.widget.Toast
import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.adapter.ShelfBookAdapter
import com.kotlinproject.wooooo.audink.utils.AppCache
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity :
    BaseActivity(),
    SearchView.OnQueryTextListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        recycler_search_result.layoutManager = LinearLayoutManager(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        AppCache.asyncSearch(this, query) { list, isSuccess ->
            if (isSuccess) {
                recycler_search_result.adapter = ShelfBookAdapter(this,list)
            } else {
                Toast.makeText(this,"搜索失败",Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    override fun onQueryTextChange(newText: String?) = false

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.menu_item_search)?.actionView as SearchView?
        searchView?.apply {
            maxWidth = Integer.MAX_VALUE
            onActionViewExpanded()
            queryHint = "搜索书籍"
            setOnQueryTextListener(this@SearchActivity)
            isSubmitButtonEnabled = true

            // 改图标
            val field = javaClass.getDeclaredField("mGoButton")
            field.isAccessible = true
            val mGoButton = field[this] as ImageView
            mGoButton.setImageResource(R.drawable.ic_search_white_24dp)
        }
        return super.onCreateOptionsMenu(menu)
    }
}
