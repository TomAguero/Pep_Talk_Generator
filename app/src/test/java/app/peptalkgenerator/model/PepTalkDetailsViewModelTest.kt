package app.peptalkgenerator.model

import androidx.lifecycle.SavedStateHandle
import app.peptalkgenerator.data.FakePepTalkDao
import app.peptalkgenerator.data.FakePhraseDao
import app.peptalkgenerator.data.PepTalk
import app.peptalkgenerator.data.PepTalkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
class PepTalkDetailsViewModelTest {

    private lateinit var fakePepTalkDao: FakePepTalkDao
    private lateinit var repository: PepTalkRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakePepTalkDao = FakePepTalkDao()
        repository = PepTalkRepository(FakePhraseDao(), fakePepTalkDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(pepTalkId: Int): PepTalkDetailsViewModel {
        val savedStateHandle = SavedStateHandle(mapOf("pepTalkId" to pepTalkId))
        return PepTalkDetailsViewModel(savedStateHandle, repository)
    }

    @Test
    fun pepTalkDetailsUiState_loadsPepTalkById() = runTest {
        fakePepTalkDao.insertPepTalk(PepTalk(id = 1, pepTalk = "You rock!", favorite = true, block = false))

        val viewModel = createViewModel(1)
        val state = viewModel.pepTalkDetailsUiState.value

        assertEquals("You rock!", state.pepTalkDetails.pepTalk)
        assertEquals(1, state.pepTalkDetails.id)
    }

    @Test
    fun initialState_hasDefaultValues() {
        // Don't insert any pep talks - the initial value should be the default
        val viewModel = createViewModel(999)
        val state = viewModel.pepTalkDetailsUiState.value

        assertEquals(PepTalkDetailsUIState(), state)
    }

    @Test
    fun removeFromFavorites_deletesPepTalkFromRepository() = runTest {
        fakePepTalkDao.insertPepTalk(PepTalk(id = 1, pepTalk = "Delete me", favorite = true, block = false))

        val viewModel = createViewModel(1)
        // Wait for state to load
        viewModel.pepTalkDetailsUiState.first { it.pepTalkDetails.id != 0 }

        viewModel.removeFromFavorites()

        val favorites = repository.getFavorites().first()
        assertTrue(favorites.isEmpty())
    }
}
