package com.rosseti.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.rosseti.base.BaseActivity

abstract class BaseFragment : Fragment() {

    private var mainActivity: BaseActivity? = null
    
     fun showProgress() {

    }

     fun hideProgress() {

    }

     fun showErrorMessage(error: String) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as BaseActivity?
    }
}