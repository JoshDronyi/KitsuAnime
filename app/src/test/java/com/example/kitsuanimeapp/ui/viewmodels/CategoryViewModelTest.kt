package com.example.kitsuanimeapp.ui.viewmodels

import com.example.kitsuanimeapp.data.model.KitsuRepo
import com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO.CategoryDataDTO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CategoryViewModelTest {
    private val TAG = "CategoryViewModelTest"

    private val repo = mockk<KitsuRepo>()
    private val dispatcher = UnconfinedTestDispatcher()

    @Test
    fun checkInitialState_hasDefaultValues_ReturnsTrue() {
        // when
        val viewModel = CategoryViewModel(repo)
        // then
        Assert.assertTrue(!viewModel.categoryState.value.isLoading)
        Assert.assertTrue(viewModel.categoryState.value.categoryList.isEmpty())
        Assert.assertTrue(viewModel.categoryState.value.error == null)
    }

    @Test
    fun checkStateUpdates_SuccessfulUpdate_ReturnsTrue() = runTest(dispatcher.scheduler) {
        // Given
        val testObject = CategoryDataDTO.createBlankObject()
        coEvery { repo.getCategories() } coAnswers { listOf(testObject) }
        val viewModel = CategoryViewModel(repo)
        // When
        viewModel.getAnimeCategories(dispatcher).join()
        val (result1, result2, result3) = viewModel.categoryState.take(3).toList()
        println("$result1")
        // Then
        with(result1) {
            Assert.assertTrue(!isLoading)
            Assert.assertTrue(categoryList.isEmpty())
            Assert.assertTrue(error == null)
        }
        println("$result2")
        // Then
        with(result2) {
            Assert.assertTrue(isLoading)
            Assert.assertTrue(categoryList.isEmpty())
            Assert.assertTrue(error == null)
        }
        println("$result3")
        with(result3) {
            Assert.assertTrue(!isLoading)
            Assert.assertTrue(categoryList.isNotEmpty())
            Assert.assertTrue(categoryList.size == 1)
            Assert.assertTrue(categoryList.first().type == testObject.type) // Both are toaster strudel.
            Assert.assertTrue(error == null)
        }
    }
}
