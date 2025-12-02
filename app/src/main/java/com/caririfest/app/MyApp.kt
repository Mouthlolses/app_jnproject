package com.caririfest.app

import android.app.Application
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application(){
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51SUVUOQgaqHCARe3Ofe0TKDF7hoFOoQ8gTYshyT2TUkTjl2IdutZwHwkZAoL0Fn3hRJcgfqmdwhJhJTYCOrQbhxz00tjknUoqq"
        )
    }
}