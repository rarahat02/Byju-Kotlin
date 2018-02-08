package com.example.bs206.byju_test

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.facebook.accountkit.Account
import com.facebook.accountkit.AccountKit
import com.facebook.accountkit.AccountKitCallback
import com.facebook.accountkit.AccountKitError
import kotlinx.android.synthetic.main.activity_hello_token.*

class TokenActivity : Activity()
{

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_token)

        log_out_button.setOnClickListener {
            AccountKit.logOut()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        AccountKit.getCurrentAccount(object : AccountKitCallback<Account> {
            override fun onSuccess(account: Account) {
                val userId = findViewById<View>(R.id.user_id) as TextView
                user_id.text = account.id

                val number = account.phoneNumber
                user_phone.text = number?.toString()
            }

            override fun onError(error: AccountKitError) {}
        })
    }
}
