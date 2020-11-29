package com.rosseti.base

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import cn.pedant.SweetAlert.SweetAlertDialog

abstract class BaseActivity : AppCompatActivity() {

    private var progressDialog:ACProgressFlower? = null
    private var alertDialog:SweetAlertDialog? = null

    fun showProgress() {
     hideProgress();
     progressDialog = ACProgressFlower.Builder(this)
         .direction(ACProgressConstant.DIRECT_ANTI_CLOCKWISE)
         .themeColor(Color.WHITE)
         .fadeColor(Color.DKGRAY).build()
     progressDialog?.show()

    }

    fun hideProgress() {
    progressDialog?.dismiss()
    }

    fun showErrorMessage(error: String) {
         alertDialog?.dismiss()
         alertDialog = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
         alertDialog?.contentText = error
         alertDialog?.show()

    }

    abstract fun setToolBarTitle(text: String)
    abstract fun setBackButtonVisible(isVisible: Boolean)

    override fun onDestroy() {
        progressDialog?.dismiss()
        super.onDestroy()
    }
}