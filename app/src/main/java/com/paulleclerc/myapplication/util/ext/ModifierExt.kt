package com.paulleclerc.myapplication.util.ext

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.paulleclerc.myapplication.ViewTags

fun Modifier.testTag(tag: ViewTags) = this.testTag(tag.toString())
