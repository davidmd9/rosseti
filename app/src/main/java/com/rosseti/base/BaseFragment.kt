package com.rosseti.base

import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.rosseti.R

abstract class BaseFragment : Fragment() {

    var mainActivity: BaseActivity? = null
    
     fun showProgress() {
         mainActivity?.showProgress()
    }

     fun hideProgress() {
         mainActivity?.hideProgress()
    }

     fun showErrorMessage(error: String) {
         mainActivity?.showErrorMessage(error)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as BaseActivity?
    }

    protected fun setToolbar(toolbar: Toolbar, title: String?) {
        mainActivity!!.setSupportActionBar(toolbar)
        val tvTitle: TextView = (toolbar.parent as View).findViewById(R.id.toolbarTitle)
        tvTitle.text = title
        val btnBack: ImageButton = toolbar.findViewById(R.id.btnBack)
        btnBack?.setOnClickListener { v: View? -> mainActivity!!.onBackPressed() }
    }
}