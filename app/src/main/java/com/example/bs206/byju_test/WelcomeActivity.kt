package com.example.bs206.byju_test

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_welcome.*

/**
 * Created by bs206 on 2/1/18.
 */
class WelcomeActivity : AppCompatActivity()
{

    private lateinit var myViewPagerAdapter: MyViewPagerAdapter

    private var dots: Array<TextView?>? = null
    private var layouts: IntArray? = null

    private lateinit var prefManager: PrefManager

    //	viewpager change listener
    var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener
    {

        override fun onPageSelected(position: Int)
        {
            addBottomDots(position)

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts!!.size - 1)
            {
                // last page. make button text to GOT IT
                btnNext.text = getString(R.string.start)
                btnSkip.visibility = View.GONE
            }
            else
            {
                // still pages are left
                btnNext.text = getString(R.string.next)
                btnSkip.visibility = View.VISIBLE
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}

        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        // Checking for first time launch - before calling setContentView()
        prefManager = PrefManager(this)
        
        if (!prefManager.isFirstTimeLaunch)
        {
            launchHomeScreen()
            finish()
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) 
        {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        setContentView(R.layout.activity_welcome)


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = intArrayOf(R.layout.welcome_slide1, R.layout.welcome_slide2, R.layout.welcome_slide4)

        // adding bottom dots
        addBottomDots(0)

        // making notification bar transparent
        changeStatusBarColor()

        myViewPagerAdapter = MyViewPagerAdapter()
        viewPager.adapter = myViewPagerAdapter
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener)

        btnSkip.setOnClickListener { launchHomeScreen() }

        btnNext.setOnClickListener {
            // checking for last page
            // if last page home screen will be launched
            val current = getItem(+1)
            if (current < layouts!!.size) {
                // move to next screen
                viewPager.currentItem = current
            }
            else
            {
                launchHomeScreen()
            }
        }
    }

    private fun addBottomDots(currentPage: Int)
    {

        //dots = Array<TextView>(layouts.size)
        dots = arrayOfNulls(layouts!!.size)

        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)

        layoutDots.removeAllViews()
        for (i in dots!!.indices)
        {
            dots!![i] = TextView(this)
            dots!![i]!!.text = Html.fromHtml("&#8226;")
            dots!![i]!!.textSize = 35f
            dots!![i]!!.setTextColor(colorsInactive[currentPage])
            layoutDots.addView(dots!![i])
        }

        if (dots!!.isNotEmpty())
            dots!![currentPage]!!.setTextColor(colorsActive[currentPage])
    }

    private fun getItem(i: Int): Int = viewPager.currentItem + i


    private fun launchHomeScreen()
    {
        prefManager.isFirstTimeLaunch = false
        //prefManager.setFirstTimeLaunch(false)
        startActivity(Intent(this@WelcomeActivity, CourseListActivity::class.java))
        finish()
    }

    /**
     * Making notification bar transparent
     */
    private fun changeStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * View pager adapter
     */
    inner class MyViewPagerAdapter : PagerAdapter()
    {
        private lateinit var layoutInflater: LayoutInflater

        override fun instantiateItem(container: ViewGroup, position: Int): Any
        {
            layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = layoutInflater.inflate(layouts!![position], container, false)
            container.addView(view)

            return view
        }

        override fun getCount(): Int = layouts!!.size

        override fun isViewFromObject(view: View, obj: Any): Boolean
        {
            return view === obj
        }


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }
}
