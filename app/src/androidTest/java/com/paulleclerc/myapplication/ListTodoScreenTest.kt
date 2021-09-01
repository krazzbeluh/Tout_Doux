package com.paulleclerc.myapplication

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import com.paulleclerc.myapplication.ViewTags.AddFAB
import com.paulleclerc.myapplication.ViewTags.TodoCheckBox
import com.paulleclerc.myapplication.ui.list.ListTodosScreen
import com.paulleclerc.myapplication.ui.theme.MyApplicationTheme
import com.paulleclerc.myapplication.util.ext.onAllNodesWithTag
import com.paulleclerc.myapplication.util.ext.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class ListTodoScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun clickingPlusFABLaunchesAddTotoScreen() {
        var isFinished = false
        val onConfirm: () -> Unit = {
            isFinished = true
        }
        launch(onConfirm = onConfirm)

        rule.onNodeWithTag(AddFAB).performClick()

        Thread.sleep(100)
        assert(isFinished)
    }

    @Test
    fun clickingCheckBoxInvertsItsValue() {
        launch { }

        val firstCheckBox = rule.onAllNodesWithTag(TodoCheckBox).onFirst()
        val initialValue = try {
            firstCheckBox.assertIsOn()
            true
        } catch (e: AssertionError) {
            false
        }
        firstCheckBox.performClick()

        Thread.sleep(100)
        if (initialValue) firstCheckBox.assertIsOff()
        else firstCheckBox.assertIsOn()
    }

    private fun launch(onConfirm: () -> Unit) {
        rule.setContent {
            MyApplicationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ListTodosScreen(onConfirm = onConfirm)
                }
            }
        }
    }
}
