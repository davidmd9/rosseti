package com.rosseti.base

import android.content.Context
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    private var mainActivity: BaseActivity? = null
    
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
}