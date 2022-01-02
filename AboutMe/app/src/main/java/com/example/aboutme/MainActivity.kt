package com.example.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.aboutme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var mBinding:ActivityMainBinding
    private var myName:MyName = MyName("Tanmay")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.myName = myName


        mBinding.doneButton.setOnClickListener {
            addNickName(it)
        }


    }

    private fun addNickName(view: View) {

        mBinding.apply {
            myName?.nickname = nickNameEditText.text.toString()
            invalidateAll()
            nickNameEditText.visibility = View.GONE
            view.visibility = View.GONE
            nickNameTextView.visibility = View.VISIBLE
        }
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}