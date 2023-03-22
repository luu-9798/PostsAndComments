package com.luu9798.postandcomments.network

import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModuleTest {

    @Test
    fun `provideOkHttpClient returns non-null OkHttpClient`() {
        val okHttpClient = NetworkModule.provideOkHttpClient()
        assertNotNull(okHttpClient)
        val loggingInterceptor = okHttpClient.interceptors.firstOrNull { it is HttpLoggingInterceptor }
        assertNotNull(loggingInterceptor)
    }

    @Test
    fun `provideRetrofit returns non-null retrofit with expected configuration`() {
        val okHttpClient = NetworkModule.provideOkHttpClient()
        val retrofit = NetworkModule.provideRetrofit(okHttpClient)
        assertNotNull(retrofit)
        assertEquals("https://jsonplaceholder.typicode.com/", retrofit.baseUrl().toString())
        assertNotNull(retrofit.converterFactories().firstOrNull { it is GsonConverterFactory })
        assertEquals(okHttpClient, retrofit.callFactory())
    }

    @Test
    fun `provideRepository returns non-null repository`() {
        val okHttpClient = NetworkModule.provideOkHttpClient()
        val retrofit = NetworkModule.provideRetrofit(okHttpClient)
        val repository = NetworkModule.provideRepository(retrofit)
        assertNotNull(repository)
    }
}
