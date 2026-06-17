package com.example.myshop.core.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class ContentStateTest {

    @Test
    fun `fromHasContent returns content when items exist`() {
        assertEquals(
            ContentState.CONTENT,
            ContentState.fromHasContent(hasContent = true)
        )
    }

    @Test
    fun `fromHasContent returns empty when items do not exist`() {
        assertEquals(
            ContentState.EMPTY,
            ContentState.fromHasContent(hasContent = false)
        )
    }
}
