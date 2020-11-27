package com.rosseti.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

     fun showProgress() {

    }

     fun hideProgress() {

    }

     fun showErrorMessage(error: String) {

    }
}