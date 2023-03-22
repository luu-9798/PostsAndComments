package com.luu9798.postandcomments.view.adapter

import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AdapterModuleTest {

    @Test
    fun `provideUserCardAdapter returns a non-null UserCardAdapter`() {
        val userCardAdapter = AdapterModule.provideUserCardAdapter()
        assertNotNull(userCardAdapter)
    }
}
