package com.example.bs206.byju_test

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import kotlinx.android.synthetic.main.activity_course_list.*

class CourseListActivity : AppCompatActivity()
{

    private lateinit var sixGrade : CheckBox
    private lateinit var sevenGrade : CheckBox
    private lateinit var eightGrade : CheckBox
    private lateinit var nineGrade : CheckBox
/*    private lateinit var tenGrade : CheckBox
    private lateinit var elevenGrade : CheckBox
    private lateinit var twelveGrade : CheckBox
    private lateinit var campusRecruit : CheckBox*/

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_list)

        sixGrade = findViewById(R.id.six)
        sevenGrade = findViewById(R.id.seven)
        eightGrade = findViewById(R.id.eight)
        nineGrade = findViewById(R.id.nine)


        sixGrade.setOnClickListener { showLoginScreen() }
        sevenGrade.setOnClickListener{showLoginScreen()}
        eightGrade.setOnClickListener { showLoginScreen() }
        nineGrade.setOnClickListener { showLoginScreen() }

    }
    /*private fun onCheckBoxClicked(view : View)
    {
        val checkBox =  view as CheckBox

        val checked = view.isChecked

        Log.v("rahat", checkBox.text.toString())

        when(checkBox.text)
        {

            R.string.sixGrade.toString() -> { if(checked)showLoginScreen()}
            R.string.sevenGrade.toString() -> {showLoginScreen()}
            R.string.eightGrade.toString() -> {showLoginScreen()}
            R.string.nineGrade.toString() -> {showLoginScreen()}
            R.string.tenGrade.toString() -> {showLoginScreen()}
            R.string.elevenGrade.toString() -> {showLoginScreen()}
            R.string.twelveGrade.toString() -> {showLoginScreen()}
            R.string.campusRecruit.toString() -> {showLoginScreen()}
            R.string.bankPo.toString() -> {showLoginScreen()}
            R.string.cat.toString() -> {showLoginScreen()}
            R.string.gre.toString() -> {showLoginScreen()}
            R.string.gmat.toString() -> {showLoginScreen()}
        }
    }*/
    private fun showLoginScreen()
    {
        val intent =  Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
