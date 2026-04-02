package app.peptalkgenerator.model

import app.peptalkgenerator.data.FakePepTalkDao
import app.peptalkgenerator.data.FakePhraseDao
import app.peptalkgenerator.data.PepTalk
import app.peptalkgenerator.data.PepTalkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    private lateinit var fakePepTalkDao: FakePepTalkDao
    private lateinit var repository: PepTalkRepository
    private lateinit var viewModel: FavoritesViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakePepTalkDao = FakePepTalkDao()
        repository = PepTalkRepository(FakePhraseDao(), fakePepTalkDao)
        viewModel = FavoritesViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_hasEmptyFavoritesList() {
        val state = viewModel.favoritesUiState.value

        assertTrue(state.favoritesList.isEmpty())
    }

    @Test
    fun favoritesUiState_reflectsFavoritesFromRepository() = runTest {
        fakePepTalkDao.insertPepTalk(PepTalk(id = 1, pepTalk = "You rock!", favorite = true, block = false))
        fakePepTalkDao.insertPepTalk(PepTalk(id = 2, pepTalk = "Keep going!", favorite = true, block = false))

        val state = viewModel.favoritesUiState.value

        assertEquals(2, state.favoritesList.size)
    }

    @Test
    fun favoritesUiState_excludesNonFavorites() = runTest {
        fakePepTalkDao.insertPepTalk(PepTalk(id = 1, pepTalk = "Favorite", favorite = true, block = false))
        fakePepTalkDao.insertPepTalk(PepTalk(id = 2, pepTalk = "Not favorite", favorite = false, block = false))

        val state = viewModel.favoritesUiState.value

        assertEquals(1, state.favoritesList.size)
        assertEquals("Favorite", state.favoritesList[0].pepTalk)
    }
}
