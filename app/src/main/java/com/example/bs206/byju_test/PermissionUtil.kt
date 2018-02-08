package com.example.bs206.byju_test

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.checkSelfPermission
import android.util.Log



fun checkPhoneStatePermissions(context : Context): Boolean
{
    val permissionState = checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
    return permissionState == PackageManager.PERMISSION_GRANTED
}
fun checkReadSMSPermissions(context : Context): Boolean
{
    val permissionState = checkSelfPermission(context, Manifest.permission.READ_SMS)
    return permissionState == PackageManager.PERMISSION_GRANTED
}

fun requestPhoneStatePermissions(context: Context)
{
    if(context is Activity)
    {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(context,
                Manifest.permission.READ_PHONE_STATE)

        if (shouldProvideRationale)
        {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")

/*            showSnackbar(context, R.string.permission_rationale, android.R.string.ok,
                    View.OnClickListener {
                        // Request permission
                        startPhoneStatePermissionRequest(context)
                    })*/

            startPhoneStatePermissionRequest(context)

        }
        else
        {
            Log.i(TAG, "Requesting permission")
            startPhoneStatePermissionRequest(context)
        }
    }

}
fun requestReadSMSPermissions(context: Context)
{
    if(context is Activity)
    {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(context,
                Manifest.permission.READ_SMS)

        if (shouldProvideRationale)
        {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")

/*            showSnackbar(context, R.string.permission_rationale, android.R.string.ok,
                    View.OnClickListener {
                        // Request permission
                        startPhoneStatePermissionRequest(context)
                    })*/

            startReadSMSPermissionRequest(context)

        }
        else
        {
            Log.i(TAG, "Requesting permission")
            startReadSMSPermissionRequest(context)
        }
    }

}
/*fun showSnackbar(context: Context,mainTextStringId: Int, actionStringId: Int, listener: View.OnClickListener)
{
    Snackbar.make(android.R.id.content,
            mainTextStringId,
            Snackbar.LENGTH_INDEFINITE)
            .setAction(actionStringId, listener).show()
}*/

fun startPhoneStatePermissionRequest(context: Context)
{

    if (context is Activity)
    {
        ActivityCompat.requestPermissions(context,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                Constants.REQUEST_PHONE_STATE_PERMISSIONS_REQUEST_CODE)
    }

}

fun startReadSMSPermissionRequest(context: Context)
{

    if (context is Activity)
    {
        ActivityCompat.requestPermissions(context,
                arrayOf(Manifest.permission.READ_SMS),
                Constants.REQUEST_REASD_SMS_PERMISSIONS_REQUEST_CODE)
    }

}