package com.paulleclerc.myapplication.util

import androidx.compose.ui.test.hasTestTag
import com.paulleclerc.myapplication.ViewTags

fun hasTestTag(tags: ViewTags) = hasTestTag(tags.toString())
