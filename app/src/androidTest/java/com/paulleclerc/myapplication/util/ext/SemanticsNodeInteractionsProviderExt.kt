package com.paulleclerc.myapplication.util.ext

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import com.paulleclerc.myapplication.ViewTags

fun SemanticsNodeInteractionsProvider.onNodeWithTag(
    tag: ViewTags,
    useUnmergedTree: Boolean = false
) = this.onNodeWithTag(tag.toString(), useUnmergedTree)

fun SemanticsNodeInteractionsProvider.onAllNodesWithTag(
    tag: ViewTags,
    useUnmergedTree: Boolean = false
) = this.onAllNodesWithTag(tag.toString(), useUnmergedTree)
