package com.caririfest.app.payment.stripe.data.remote

import com.caririfest.app.payment.stripe.domain.model.PaymentIntentRemote

class StripeRepository(
    private val api: StripeApi
) {
    suspend fun getPaymentIntent(): Result<PaymentIntentRemote> {
        return try {
            val response = api.createPaymentIntent(
                request = mapOf(
                    "items" to listOf(
                        mapOf<String, Any>("id" to "xl-tshirt", "amount" to 5000)  // valor pode vir do carrinho
                    )
                )
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}