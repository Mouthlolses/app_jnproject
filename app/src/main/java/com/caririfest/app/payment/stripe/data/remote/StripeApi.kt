package com.caririfest.app.payment.stripe.data.remote

import com.caririfest.app.payment.stripe.domain.model.PaymentIntentRemote
import retrofit2.http.Body
import retrofit2.http.POST

interface StripeApi {

    @POST("create-payment-intent")
    suspend fun createPaymentIntent(
        @Body  request: Map<String, @JvmSuppressWildcards Any>
    ) : PaymentIntentRemote
}