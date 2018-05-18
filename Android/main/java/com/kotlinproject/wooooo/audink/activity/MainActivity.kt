package com.kotlinproject.wooooo.audink.activity

import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.adapter.PlayBarAdapter
import com.kotlinproject.wooooo.audink.fragment.BookStoreFragment
import com.kotlinproject.wooooo.audink.fragment.MyShelfFragment
import com.kotlinproject.wooooo.audink.model.ArticleInfo
import com.kotlinproject.wooooo.audink.service.BackgroundAudioService
import com.kotlinproject.wooooo.audink.utils.AppCache
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_my_shelf.*
import kotlinx.android.synthetic.main.layout_navigation_header.*
import kotlinx.android.synthetic.main.layout_tab_bar.*

class MainActivity :
    BaseActivity(),
    BookStoreFragment.OnBookStoreFragmentInteractionListener,
    MyShelfFragment.OnMyShelfFragmentInteractionListener {

    private lateinit var audioBinder: BackgroundAudioService.BackgroundAudioBinder
    private lateinit var playBarAdapter: PlayBarAdapter

    /** 新的文章被选择 */
    override fun onNewArticleSelected(article: ArticleInfo) {
    }

    /** 抽屉的头部 */
    private val viewNavigationHeader by lazy {
        LayoutInflater.from(this).inflate(R.layout.layout_navigation_header, view_navigation, false)
    }

    override fun onBgmServiceBind(service: IBinder?) {
        super.onBgmServiceBind(service)
        audioBinder = service as BackgroundAudioService.BackgroundAudioBinder
        playBarAdapter = PlayBarAdapter(this, audioBinder)
        playBarAdapter.refreshBarState()
        initView()
    }

    override fun onResume() {
        super.onResume()
        try {
            playBarAdapter.refreshBarState()
            recycler_bookshelf.adapter.notifyDataSetChanged()
        } catch (e: UninitializedPropertyAccessException) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun initView() {
        // 添加抽屉表头
        view_navigation.addHeaderView(viewNavigationHeader)

        // 抽屉表头显示
        if (AppCache.isUserLogin) {
            txt_navigation_user_name.text = AppCache.username
            txt_navigation_click_to_login.visibility = View.GONE
            relative_navigation_user_info.visibility = View.VISIBLE
        } else {
            relative_navigation_user_info.visibility = View.GONE
            txt_navigation_click_to_login.visibility = View.VISIBLE
        }

        txt_navigation_click_to_login.setOnClickListener {
            startActivity(Intent(this, LoginRegisterActivity::class.java))
        }

        // 设置翻页
        val adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            private val fragments = listOf<Fragment>(MyShelfFragment(), BookStoreFragment())
            override fun getItem(position: Int): Fragment = fragments[position]
            override fun getCount(): Int = fragments.count()
        }
        viewpager_main.adapter = adapter
        txt_bookshelf.isSelected = true

        // 监听器
        image_menu.setOnClickListener { drawer.openDrawer(Gravity.START) }
        image_search.setOnClickListener { startActivity(Intent(this, SearchActivity::class.java)) }
        txt_bookshelf.setOnClickListener { viewpager_main.currentItem = 0 }
        txt_bookstore.setOnClickListener { viewpager_main.currentItem = 1 }
        viewpager_main.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
            override fun onPageSelected(position: Int) {
                val b = position == 0
                txt_bookshelf.isSelected = b
                txt_bookstore.isSelected = !b
            }
        })
        view_navigation.setNavigationItemSelectedListener {
            /* todo 导航菜单事件 */
            when (it.itemId) {
            /** 切换用户 */
                R.id.menu_item_swap_user -> startActivity(Intent(this, LoginRegisterActivity::class.java))
            }
            drawer.closeDrawers()
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioBinder.setOnCompletionListener(null)
    }
}