package app.peptalkgenerator.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PepTalkRepositoryTest {

    private lateinit var fakePhraseDao: FakePhraseDao
    private lateinit var fakePepTalkDao: FakePepTalkDao
    private lateinit var repository: PepTalkRepository

    @Before
    fun setup() {
        fakePhraseDao = FakePhraseDao()
        fakePepTalkDao = FakePepTalkDao()
        repository = PepTalkRepository(fakePhraseDao, fakePepTalkDao)
    }

    // region generateNewTalk

    @Test
    fun generateNewTalk_combinesAllFourPhraseTypes() = runTest {
        fakePhraseDao.insertPhrase(Phrase(type = "greeting", saying = "Hey"))
        fakePhraseDao.insertPhrase(Phrase(type = "first", saying = "you are"))
        fakePhraseDao.insertPhrase(Phrase(type = "second", saying = "really awesome"))
        fakePhraseDao.insertPhrase(Phrase(type = "ending", saying = "never forget it"))

        val result = repository.generateNewTalk()

        assertEquals("Hey you are really awesome never forget it", result)
    }

    @Test
    fun generateNewTalk_handlesNullGreeting() = runTest {
        // No greeting phrases inserted
        fakePhraseDao.insertPhrase(Phrase(type = "first", saying = "you are"))
        fakePhraseDao.insertPhrase(Phrase(type = "second", saying = "awesome"))
        fakePhraseDao.insertPhrase(Phrase(type = "ending", saying = "always"))

        val result = repository.generateNewTalk()

        assertEquals("you are awesome always", result)
    }

    @Test
    fun generateNewTalk_handlesAllNullPhrases() = runTest {
        val result = repository.generateNewTalk()

        assertEquals("", result)
    }

    @Test
    fun generateNewTalk_trimsWhitespace() = runTest {
        // Only greeting exists, the rest are null -> "Hey   " should be trimmed
        fakePhraseDao.insertPhrase(Phrase(type = "greeting", saying = "Hey"))

        val result = repository.generateNewTalk()

        assertEquals("Hey", result)
    }

    // endregion

    // region Phrase operations

    @Test
    fun insertPhrase_addsToDao() = runTest {
        val phrase = Phrase(type = "greeting", saying = "Hello")
        repository.insertPhrase(phrase)

        val allPhrases = repository.getAllPhrases().first()
        assertEquals(1, allPhrases.size)
        assertEquals("Hello", allPhrases[0].saying)
    }

    @Test
    fun deletePhrase_removesFromDao() = runTest {
        val phrase = Phrase(id = 1, type = "greeting", saying = "Hello")
        fakePhraseDao.insertPhrase(phrase)

        repository.deletePhrase(phrase)

        val allPhrases = repository.getAllPhrases().first()
        assertTrue(allPhrases.isEmpty())
    }

    @Test
    fun updatePhrase_updatesInDao() = runTest {
        val phrase = Phrase(id = 1, type = "greeting", saying = "Hello")
        fakePhraseDao.insertPhrase(phrase)

        repository.updatePhrase(phrase.copy(saying = "Hi there"))

        val allPhrases = repository.getAllPhrases().first()
        assertEquals("Hi there", allPhrases[0].saying)
    }

    @Test
    fun getAllPhrases_returnsFlowFromDao() = runTest {
        fakePhraseDao.insertPhrase(Phrase(type = "greeting", saying = "Hey"))
        fakePhraseDao.insertPhrase(Phrase(type = "ending", saying = "Bye"))

        val phrases = repository.getAllPhrases().first()

        assertEquals(2, phrases.size)
    }

    // endregion

    // region PepTalk operations

    @Test
    fun insertPepTalk_addsToDao() = runTest {
        val pepTalk = PepTalk(pepTalk = "You rock!", favorite = true, block = false)
        repository.insertPepTalk(pepTalk)

        val favorites = repository.getFavorites().first()
        assertEquals(1, favorites.size)
        assertEquals("You rock!", favorites[0].pepTalk)
    }

    @Test
    fun deletePepTalk_removesFromDao() = runTest {
        val pepTalk = PepTalk(id = 1, pepTalk = "You rock!", favorite = true, block = false)
        fakePepTalkDao.insertPepTalk(pepTalk)

        repository.deletePepTalk(pepTalk)

        val favorites = repository.getFavorites().first()
        assertTrue(favorites.isEmpty())
    }

    @Test
    fun getFavorites_returnsOnlyFavorites() = runTest {
        fakePepTalkDao.insertPepTalk(PepTalk(id = 1, pepTalk = "Fav", favorite = true, block = false))
        fakePepTalkDao.insertPepTalk(PepTalk(id = 2, pepTalk = "Not fav", favorite = false, block = false))

        val favorites = repository.getFavorites().first()

        assertEquals(1, favorites.size)
        assertEquals("Fav", favorites[0].pepTalk)
    }

    @Test
    fun getPepTalk_returnsPepTalkById() = runTest {
        fakePepTalkDao.insertPepTalk(PepTalk(id = 1, pepTalk = "Talk 1", favorite = true, block = false))
        fakePepTalkDao.insertPepTalk(PepTalk(id = 2, pepTalk = "Talk 2", favorite = true, block = false))

        val pepTalk = repository.getPepTalk(2).first()

        assertEquals("Talk 2", pepTalk.pepTalk)
    }

    // endregion
}
