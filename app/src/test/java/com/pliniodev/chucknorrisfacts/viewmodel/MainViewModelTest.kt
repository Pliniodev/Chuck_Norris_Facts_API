package com.pliniodev.chucknorrisfacts.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.pliniodev.chucknorrisfacts.R
import com.pliniodev.chucknorrisfacts.service.model.Fact
import com.pliniodev.chucknorrisfacts.service.repository.ChuckNorrisRepository
import com.pliniodev.chucknorrisfacts.service.utils.FactsResult
import com.pliniodev.chucknorrisfacts.viewmodel.test_utils.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()//para testes com coroutines

    @Mock
    private lateinit var searchResultLiveDataObserver: Observer<List<Fact>>

    @Mock
    private lateinit var viewFlipperLiveDataObserver: Observer<Pair<Int, Int?>>

    private lateinit var viewModel: MainViewModel

    private lateinit var facts: List<Fact>

    @Before
    fun setUp() {
        facts = listOf(
            Fact(
                categories = arrayListOf("food"),
                created_at = "2020-01-05 13:42:18.823766",
                icon_url = "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
                id = "MjtEesffSd6AH3Pxbw7_lg",
                updated_at = "2020-01-05 13:42:18.823766",
                url = "https://api.chucknorris.io/jokes/MjtEesffSd6AH3Pxbw7_lg",
                value = "When Chuck Norris played Chopped from Food Network, he finished " +
                        "his food in 1 millisecond, and instantly wins every dish. You " +
                        "didn't see him play because the episode is secret.",
            )
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should set searchResultLiveData, when viewModel getByFreeSearch, get Success`() {
        //Arrange

        val resultSuccess = MockRepository(FactsResult.Success(facts))
        viewModel = MainViewModel(resultSuccess)
        viewModel.searchResultLiveData.observeForever(searchResultLiveDataObserver)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        //act
        viewModel.getByFreeSearch(query = "food")

        //Assert
        verify(searchResultLiveDataObserver).onChanged(facts)
        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, null))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByFreeSearch get error 404`() {

        val mockRepository = MockRepository(FactsResult.ApiError(404))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByFreeSearch(query = "fo")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.facts_error_404))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByFreeSearch get error 400`() {

        val mockRepository = MockRepository(FactsResult.ApiError(400))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByFreeSearch(query = "fo")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.facts_error_400))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByFreeSearch get error 500`() {

        val mockRepository = MockRepository(FactsResult.ApiError(500))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByFreeSearch(query = "fo")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.facts_error_generic))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByFreeSearch get ServerError`() {

        val mockRepository = MockRepository(FactsResult.ServerError)
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByFreeSearch(query = "fo")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.facts_error_server_error))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should set searchResultLiveData, when viewModel getByRandom, get Success`() {
        //Arrange
        val resultSuccess = MockRepository(FactsResult.Success(facts))
        viewModel = MainViewModel(resultSuccess)
        viewModel.searchResultLiveData.observeForever(searchResultLiveDataObserver)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        //act
        viewModel.getByRandom()

        //Assert
        verify(searchResultLiveDataObserver).onChanged(facts)
        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, null))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByRandom get error 404`() {

        val mockRepository = MockRepository(FactsResult.ApiError(404))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByRandom()

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.facts_error_404))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByRandom get error 400`() {

        val mockRepository = MockRepository(FactsResult.ApiError(400))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByRandom()

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.facts_error_400))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByRandom get error 500`() {

        val mockRepository = MockRepository(FactsResult.ApiError(500))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByRandom()

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.facts_error_generic))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByRandom get ServerError`() {

        val mockRepository = MockRepository(FactsResult.ServerError)
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByRandom()

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.facts_error_server_error))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should set searchResultLiveData, when viewModel getByCategory, get Success`() {
        //Arrange
        val resultSuccess = MockRepository(FactsResult.Success(facts))
        viewModel = MainViewModel(resultSuccess)
        viewModel.searchResultLiveData.observeForever(searchResultLiveDataObserver)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        //act
        viewModel.getByCategory("food")

        //Assert
        verify(searchResultLiveDataObserver).onChanged(facts)
        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, null))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByCategory get error 404`() {

        val mockRepository = MockRepository(FactsResult.ApiError(404))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByCategory("food")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.facts_error_404))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByCategory get error 400`() {

        val mockRepository = MockRepository(FactsResult.ApiError(400))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByCategory("food")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.facts_error_400))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByCategory get error 500`() {

        val mockRepository = MockRepository(FactsResult.ApiError(500))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByCategory("food")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.facts_error_generic))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByCategory get ServerError`() {

        val mockRepository = MockRepository(FactsResult.ServerError)
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByCategory("food")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.facts_error_server_error))
    }
}

class MockRepository(
    private val result: FactsResult<List<Any>>
) : ChuckNorrisRepository {


    override suspend fun getByFreeQuery(
        query: String,
        factsResult: (result: FactsResult<List<Fact>>) -> Unit
    ) {
        factsResult(result as FactsResult<List<Fact>>)
    }

    override suspend fun getByCategory(
        category: String,
        factsResult: (result: FactsResult<List<Fact>>) -> Unit
    ) {
        factsResult(result as FactsResult<List<Fact>>)
    }

    override suspend fun getByRandom(factsResult: (result: FactsResult<List<Fact>>) -> Unit) {
        factsResult(result as FactsResult<List<Fact>>)
    }

    override suspend fun getCategoriesList(factsResult: (result: FactsResult<List<String>>) -> Unit) {
        factsResult(result as FactsResult<List<String>>)
    }


}

