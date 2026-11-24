package com.example.coroutinedemo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinedemo.data.TaskRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TasksViewModel(
    private val repository: TaskRepository = TaskRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()
    private var loadJob: Job? = null
    fun loadData(shouldFail: Boolean = false) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                coroutineScope {
                    val tasks = async { repository.getTasks(shouldFail) }
                    val announcement = async { repository.getAnnouncement() }
                    val courseCount = async { repository.getCourseCount() }

                    val loadedTasks = tasks.await()
                    val loadedAnnouncement = announcement.await()
                    val loadedCourseCount = courseCount.await()

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            tasks = loadedTasks,
                            announcement = loadedAnnouncement,
                            courseCount = loadedCourseCount
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to load: ${e.message}"
                    )
                }
            }
        }
    }

    fun sortTasks() {
        viewModelScope.launch {
            val current = _uiState.value
            val ascending = current.sortAscending
            val sorted = withContext(Dispatchers.Default) {
                if (ascending) current.tasks.sortedBy { it.title }
                else current.tasks.sortedByDescending { it.title }
            }
            _uiState.update { it.copy(tasks = sorted, sortAscending = !ascending) }
        }
    }

    fun clearData() {
        loadJob?.cancel()
        _uiState.update {
            it.copy(
                isLoading = false,
                tasks = emptyList(),
                announcement = "",
                errorMessage = null,
                sortAscending = true
            )
        }
    }
    fun cancelLoading() {
        loadJob?.cancel()

        _uiState.update {
            it.copy(isLoading = false, errorMessage = "Loading cancelled")
        }
    }
    fun onTap() {
        _uiState.update { it.copy(tapCount = it.tapCount + 1) }
    }
    fun blockMainThread() {
        Thread.sleep(3000L)
    }
}