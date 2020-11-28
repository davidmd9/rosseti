package com.rosseti

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.rosseti.base.BaseActivity
import com.rosseti.base.BaseFragment
import com.rosseti.fragments.MenuFragment
import com.rosseti.fragments.RegFragment

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var storage =  Storage(this);
        if(storage.token != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            pushFragment(RegFragment(), true)
        }
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

    override fun setToolBarTitle(text: String) {

    }

    override fun setBackButtonVisible(isVisible: Boolean) {

    }

}