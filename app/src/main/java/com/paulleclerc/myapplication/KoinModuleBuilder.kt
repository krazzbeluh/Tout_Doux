package com.paulleclerc.myapplication

import com.paulleclerc.myapplication.database.TodoDatabase
import com.paulleclerc.myapplication.repository.TodoRepository
import com.paulleclerc.myapplication.ui.add.AddTodoViewModel
import com.paulleclerc.myapplication.ui.list.ListTodosViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModuleBuilder {
    val module = module {
        single { TodoDatabase(androidContext()) }
        single { TodoRepository(get()) }
        viewModel { ListTodosViewModel(get()) }
        viewModel { AddTodoViewModel(get()) }
    }
}
