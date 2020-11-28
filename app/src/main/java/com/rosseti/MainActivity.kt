package com.rosseti

import android.R.attr
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.rosseti.base.BaseFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun pushFragment(fragment: BaseFragment, isAdd: Boolean) {
        var bundle: Bundle? = fragment.arguments
        if (bundle == null) {
            bundle = Bundle()
        }

        bundle.putBoolean("is_add", isAdd)
        fragment?.arguments = bundle

        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction();
        if (isAdd) {
            transaction.replace(
                R.id.flContainer,
                fragment,
                fragment.javaClass.simpleName
            )
            transaction.addToBackStack(fragment.javaClass.simpleName)
        } else {
            transaction.replace(
                R.id.flContainer,
                fragment,
                attr.fragment.javaClass.simpleName
            )
        }
        transaction.commitAllowingStateLoss()
    }
}