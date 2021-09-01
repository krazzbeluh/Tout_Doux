package com.paulleclerc.myapplication

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.paulleclerc.myapplication.ViewTags.AddConfirmed
import com.paulleclerc.myapplication.ViewTags.AddTitleTextField
import com.paulleclerc.myapplication.ui.add.AddTodoScreen
import com.paulleclerc.myapplication.ui.theme.MyApplicationTheme
import com.paulleclerc.myapplication.util.ext.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class AddTodoScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun clickingAddWithNoTitleDoesNotGoBackToListScreen() {
        val onConfirm: () -> Unit = {
            assert(false)
        }

        launch(onConfirm = onConfirm)

        rule.onNodeWithTag(AddConfirmed).performClick()

        rule.onNodeWithTag(AddTitleTextField).assertIsFocused()
    }

    @Test
    fun clickingAddWithTitleGoesBackToListScreen() {
        var isFinished = false
        val onConfirm : () -> Unit = {
            isFinished = true
        }

        launch(onConfirm)

        rule.onNodeWithTag(AddTitleTextField).performTextInput("Test")
        rule.onNodeWithTag(AddConfirmed).performClick()
        Thread.sleep(200)
        assert(isFinished)
    }

    private fun launch(onConfirm: () -> Unit) {
        rule.setContent {
            MyApplicationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AddTodoScreen(onConfirm = onConfirm)
                }
            }
        }
    }
}
