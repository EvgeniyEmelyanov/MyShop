package com.example.myshop.core.ui

enum class ContentState {
    LOADING,
    CONTENT,
    EMPTY,
    ERROR;

    companion object {
        fun fromHasContent(hasContent: Boolean): ContentState {
            return if (hasContent) CONTENT else EMPTY
        }
    }
}
