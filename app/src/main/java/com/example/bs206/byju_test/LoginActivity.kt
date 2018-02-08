package com.example.bs206.byju_test

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat.startActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.facebook.accountkit.AccountKit
import com.facebook.accountkit.ui.AccountKitActivity
import com.facebook.accountkit.ui.AccountKitConfiguration
import com.facebook.accountkit.ui.LoginType
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.android.synthetic.main.activity_login.*
import java.util.HashMap

class LoginActivity : AppCompatActivity()
{

    private val FRAMEWORK_REQUEST_CODE = 1

    private var nextPermissionsRequestCode = 4000
    //private val permissionsListeners = HashMap<Int, OnCompleteListener>()

    private val permissionsListeners = mutableMapOf<Int, OnCompleteListener>()

    private interface OnCompleteListener
    {
        fun onComplete()
    }


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val list = arrayOf("India (+91)", "Bangladesh (+880)",  "Mexico (+52)")
        val adapter = ArrayAdapter<String>(this, R.layout.simple_spinner_item, list)
        spinner.setAdapter(adapter)

        nameInput.editText?.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
            {
                if (count < 1)
                {
                    nameInput.setErrorEnabled(true)
                    nameInput.editText!!.error = "Please enter your name"
                }
                else
                {
                    nameInput.setErrorEnabled(false)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })


        if (AccountKit.getCurrentAccessToken() != null && savedInstanceState == null)
        {
            startActivity(Intent(this, TokenActivity::class.java))
        }

        floating.setOnClickListener {

            Log.v("rahat", "fab clicked")
            phoneAuth()
        }
    }


    override fun onStart()
    {
        super.onStart()

        if (!checkReadSMSPermissions(this))
        {
            requestReadSMSPermissions(this)
        }
        if (!checkPhoneStatePermissions(this))
        {
            requestPhoneStatePermissions(this)
        }
    }
    private fun phoneAuth()
    {
        val intent = Intent(this, AccountKitActivity::class.java)

        val configurationBuilder = AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE, AccountKitActivity.ResponseType.TOKEN)

        val configuration = configurationBuilder.setReadPhoneStateEnabled(true).setReceiveSMS(true).build()

        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configuration)


        var completeListener: OnCompleteListener = object : OnCompleteListener
        {
            override fun onComplete()
            {
                startActivityForResult(intent, FRAMEWORK_REQUEST_CODE)
            }
        }

        Log.v("test", "test running")

        if (configuration.isReceiveSMSEnabled && !canReadSmsWithoutPermission()) {
            val receiveSMSCompleteListener = completeListener
            completeListener = object : OnCompleteListener {
                override fun onComplete() {
                    requestPermissions(
                            Manifest.permission.RECEIVE_SMS,
                            R.string.permissions_receive_sms_title,
                            R.string.permissions_receive_sms_message,
                            receiveSMSCompleteListener)
                }
            }
        }
        if (configuration.isReadPhoneStateEnabled && !isGooglePlayServicesAvailable()) {
            val readPhoneStateCompleteListener = completeListener
            completeListener = object : OnCompleteListener {
                override fun onComplete() {
                    requestPermissions(
                            Manifest.permission.READ_PHONE_STATE,
                            R.string.permissions_read_phone_state_title,
                            R.string.permissions_read_phone_state_message,
                            readPhoneStateCompleteListener)
                }
            }
        }
        completeListener.onComplete()
    }

    private fun isGooglePlayServicesAvailable(): Boolean
    {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val googlePlayServicesAvailable = apiAvailability.isGooglePlayServicesAvailable(this)
        return googlePlayServicesAvailable == ConnectionResult.SUCCESS
    }

    private fun canReadSmsWithoutPermission(): Boolean
    {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val googlePlayServicesAvailable = apiAvailability.isGooglePlayServicesAvailable(this)
        return  if (googlePlayServicesAvailable == ConnectionResult.SUCCESS)
            return true
        else return false
        //TODO we should also check for Android O here t18761104

    }

    private fun requestPermissions(permission: String, rationaleTitleResourceId: Int, rationaleMessageResourceId: Int, listener: OnCompleteListener?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            listener?.onComplete()
            return
        }

        checkRequestPermissions(
                permission,
                rationaleTitleResourceId,
                rationaleMessageResourceId,
                listener)
    }

    @TargetApi(23)
    private fun checkRequestPermissions(
            permission: String,
            rationaleTitleResourceId: Int,
            rationaleMessageResourceId: Int,
            listener: OnCompleteListener?) {
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            listener?.onComplete()
            return
        }

        var requestCode = nextPermissionsRequestCode++
        //permissionsListeners[requestCode] = listener
        permissionsListeners.put(requestCode, listener!!)

        if (shouldShowRequestPermissionRationale(permission))
        {
            AlertDialog.Builder(this)
                    .setTitle(rationaleTitleResourceId)
                    .setMessage(rationaleMessageResourceId)
                    .setPositiveButton(android.R.string.yes) { dialog, which -> requestPermissions(arrayOf(permission), requestCode) }
                    .setNegativeButton(android.R.string.no) { dialog, which ->
                        // ignore and clean up the listener
                        permissionsListeners.remove(requestCode)
                    }
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
        } else {
            requestPermissions(arrayOf(permission), requestCode)
        }
    }

    @TargetApi(23)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    {
        val permissionsListener = permissionsListeners.remove(requestCode)

        if (permissionsListener != null && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            permissionsListener.onComplete()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != FRAMEWORK_REQUEST_CODE)
            return


        val toastMessage: String
        val loginResult = AccountKit.loginResultWithIntent(data)

        if (loginResult == null || loginResult.wasCancelled())
        {
            toastMessage = "Login Cancelled"
        }
        else if (loginResult.error != null)
        {
            toastMessage = loginResult.error!!.errorType.message
            val intent = Intent(this, ErrorActivity::class.java)
            intent.putExtra(ErrorActivity.HELLO_TOKEN_ACTIVITY_ERROR_EXTRA, loginResult.error)

            startActivity(intent)
        }
        else
        {
            val accessToken = loginResult.accessToken
            val tokenRefreshIntervalInSeconds = loginResult.tokenRefreshIntervalInSeconds
            if (accessToken != null)
            {
                toastMessage = ("Success:" + accessToken.accountId
                        + tokenRefreshIntervalInSeconds)
                startActivity(Intent(this, TokenActivity::class.java))
            } else {
                toastMessage = "Unknown response type"
            }
        }

        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()
    }


}
