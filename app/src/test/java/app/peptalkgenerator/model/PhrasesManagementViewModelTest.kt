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
class PhrasesManagementViewModelTest {

    private lateinit var fakePhraseDao: FakePhraseDao
    private lateinit var repository: PepTalkRepository
    private lateinit var viewModel: PhrasesManagementViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakePhraseDao = FakePhraseDao()
        repository = PepTalkRepository(fakePhraseDao, FakePepTalkDao())
        viewModel = PhrasesManagementViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_hasEmptyPhrasesList() {
        assertTrue(viewModel.uiState.value.phrasesList.isEmpty())
    }

    @Test
    fun initialState_defaultPhraseTypeIsGreeting() {
        assertEquals("greeting", viewModel.newPhraseType)
    }

    @Test
    fun initialState_newPhraseSayingIsEmpty() {
        assertEquals("", viewModel.newPhraseSaying)
    }

    @Test
    fun updateNewPhraseType_updatesType() {
        viewModel.updateNewPhraseType("ending")

        assertEquals("ending", viewModel.newPhraseType)
    }

    @Test
    fun updateNewPhraseSaying_updatesSaying() {
        viewModel.updateNewPhraseSaying("You're amazing")

        assertEquals("You're amazing", viewModel.newPhraseSaying)
    }

    @Test
    fun addPhrase_withValidSaying_insertsPhrase() = runTest {
        viewModel.updateNewPhraseType("first")
        viewModel.updateNewPhraseSaying("you are wonderful")

        viewModel.addPhrase()

        val phrases = repository.getAllPhrases().first()
        assertEquals(1, phrases.size)
        assertEquals("first", phrases[0].type)
        assertEquals("you are wonderful", phrases[0].saying)
    }

    @Test
    fun addPhrase_clearsSayingAfterInsert() = runTest {
        viewModel.updateNewPhraseSaying("test saying")

        viewModel.addPhrase()

        assertEquals("", viewModel.newPhraseSaying)
    }

    @Test
    fun addPhrase_withBlankSaying_doesNotInsert() = runTest {
        viewModel.updateNewPhraseSaying("   ")

        viewModel.addPhrase()

        val phrases = repository.getAllPhrases().first()
        assertTrue(phrases.isEmpty())
    }

    @Test
    fun addPhrase_withEmptySaying_doesNotInsert() = runTest {
        // newPhraseSaying is already ""
        viewModel.addPhrase()

        val phrases = repository.getAllPhrases().first()
        assertTrue(phrases.isEmpty())
    }

    @Test
    fun deletePhrase_removesFromRepository() = runTest {
        val phrase = Phrase(id = 1, type = "greeting", saying = "Hello")
        fakePhraseDao.insertPhrase(phrase)

        viewModel.deletePhrase(phrase)

        val phrases = repository.getAllPhrases().first()
        assertTrue(phrases.isEmpty())
    }

    @Test
    fun updatePhrase_updatesInRepository() = runTest {
        val phrase = Phrase(id = 1, type = "greeting", saying = "Hello")
        fakePhraseDao.insertPhrase(phrase)

        viewModel.updatePhrase(phrase.copy(saying = "Hi there"))

        val phrases = repository.getAllPhrases().first()
        assertEquals("Hi there", phrases[0].saying)
    }

    @Test
    fun phraseTypes_containsAllFourTypes() {
        assertEquals(listOf("greeting", "first", "second", "ending"), PHRASE_TYPES)
    }
}
