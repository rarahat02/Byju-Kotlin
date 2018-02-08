package com.example.bs206.byju_test

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.facebook.accountkit.AccountKit
import com.facebook.accountkit.AccountKitError
import kotlinx.android.synthetic.main.activity_error.*

class ErrorActivity : Activity()
{

    public override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)

       log_out_button.setOnClickListener {
            AccountKit.logOut()
            finish()
        }

        val error = intent.getParcelableExtra<AccountKitError>(HELLO_TOKEN_ACTIVITY_ERROR_EXTRA)


        if (error_label != null) {
            if (error != null) {
                error_label.text = error.toString()
            } else {
                error_label.setText("no")
            }
        }
    }

    companion object {
        val HELLO_TOKEN_ACTIVITY_ERROR_EXTRA = "HELLO_TOKEN_ACTIVITY_ERROR_EXTRA"
    }
}