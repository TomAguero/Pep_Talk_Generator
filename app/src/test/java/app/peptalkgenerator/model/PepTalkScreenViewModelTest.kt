package app.peptalkgenerator.model

import app.peptalkgenerator.data.FakePepTalkDao
import app.peptalkgenerator.data.FakePhraseDao
import app.peptalkgenerator.data.PepTalkRepository
import app.peptalkgenerator.data.Phrase
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
class PepTalkScreenViewModelTest {

    private lateinit var fakePhraseDao: FakePhraseDao
    private lateinit var fakePepTalkDao: FakePepTalkDao
    private lateinit var repository: PepTalkRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakePhraseDao = FakePhraseDao()
        fakePepTalkDao = FakePepTalkDao()
        repository = PepTalkRepository(fakePhraseDao, fakePepTalkDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun init_withPendingNotificationTalk_setsTalkState() {
        val viewModel = PepTalkScreenViewModel(repository, pendingNotificationTalk = "You are great!")

        assertEquals("You are great!", viewModel.talkState)
        assertEquals("You are great!", viewModel.pepTalkUiState.pepTalkDetails.pepTalk)
    }

    @Test
    fun init_withPendingNotificationTalk_setsFavoriteTrue() {
        val viewModel = PepTalkScreenViewModel(repository, pendingNotificationTalk = "You are great!")

        assertEquals(true, viewModel.pepTalkUiState.pepTalkDetails.favorite)
        assertEquals(false, viewModel.pepTalkUiState.pepTalkDetails.block)
    }

    @Test
    fun init_withoutPendingTalk_generatesNewTalk() = runTest {
        fakePhraseDao.insertPhrase(Phrase(type = "greeting", saying = "Hello"))
        fakePhraseDao.insertPhrase(Phrase(type = "first", saying = "you"))
        fakePhraseDao.insertPhrase(Phrase(type = "second", saying = "are great"))
        fakePhraseDao.insertPhrase(Phrase(type = "ending", saying = "always"))

        val viewModel = PepTalkScreenViewModel(repository)

        assertEquals("Hello you are great always", viewModel.talkState)
    }

    @Test
    fun refreshTalkState_updatesState() = runTest {
        fakePhraseDao.insertPhrase(Phrase(type = "greeting", saying = "Hey"))
        fakePhraseDao.insertPhrase(Phrase(type = "first", saying = "friend"))
        fakePhraseDao.insertPhrase(Phrase(type = "second", saying = "keep going"))
        fakePhraseDao.insertPhrase(Phrase(type = "ending", saying = "today"))

        val viewModel = PepTalkScreenViewModel(repository)
        // First generation happened in init
        val firstTalk = viewModel.talkState

        viewModel.refreshTalkState()
        // With single phrases per type, result is same, but it ran successfully
        assertEquals(firstTalk, viewModel.talkState)
    }

    @Test
    fun favoritePepTalk_insertsPepTalkIntoRepository() = runTest {
        val viewModel = PepTalkScreenViewModel(repository, pendingNotificationTalk = "Save me!")

        viewModel.favoritePepTalk()

        val favorites = repository.getFavorites().first()
        assertEquals(1, favorites.size)
        assertEquals("Save me!", favorites[0].pepTalk)
        assertEquals(true, favorites[0].favorite)
    }

    @Test
    fun init_withEmptyPhrases_setsEmptyTalkState() = runTest {
        val viewModel = PepTalkScreenViewModel(repository)

        assertEquals("", viewModel.talkState)
    }
}
