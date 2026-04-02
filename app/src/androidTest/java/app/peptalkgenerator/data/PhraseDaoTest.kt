package app.peptalkgenerator.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PhraseDaoTest {

    private lateinit var phraseDao: PhraseDao
    private lateinit var db: PepTalksDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PepTalksDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        phraseDao = db.phraseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertPhrase_andReadBack() = runTest {
        val phrase = Phrase(type = "greeting", saying = "Hello there")
        phraseDao.insertPhrase(phrase)

        val allPhrases = phraseDao.getAllPhrases().first()
        assertEquals(1, allPhrases.size)
        assertEquals("Hello there", allPhrases[0].saying)
        assertEquals("greeting", allPhrases[0].type)
    }

    @Test
    fun updatePhrase_modifiesSaying() = runTest {
        val phrase = Phrase(type = "greeting", saying = "Hello")
        phraseDao.insertPhrase(phrase)

        val inserted = phraseDao.getAllPhrases().first()[0]
        phraseDao.updatePhrase(inserted.copy(saying = "Hi there"))

        val updated = phraseDao.getAllPhrases().first()[0]
        assertEquals("Hi there", updated.saying)
    }

    @Test
    fun deletePhrase_removesFromDatabase() = runTest {
        val phrase = Phrase(type = "greeting", saying = "Hello")
        phraseDao.insertPhrase(phrase)

        val inserted = phraseDao.getAllPhrases().first()[0]
        phraseDao.deletePhrase(inserted)

        val allPhrases = phraseDao.getAllPhrases().first()
        assertTrue(allPhrases.isEmpty())
    }

    @Test
    fun getAllPhrases_returnsSortedByTypeAndSaying() = runTest {
        phraseDao.insertPhrase(Phrase(type = "second", saying = "Zebra"))
        phraseDao.insertPhrase(Phrase(type = "ending", saying = "Apple"))
        phraseDao.insertPhrase(Phrase(type = "greeting", saying = "Hello"))
        phraseDao.insertPhrase(Phrase(type = "first", saying = "Banana"))

        val phrases = phraseDao.getAllPhrases().first()

        assertEquals("ending", phrases[0].type)
        assertEquals("first", phrases[1].type)
        assertEquals("greeting", phrases[2].type)
        assertEquals("second", phrases[3].type)
    }

    @Test
    fun getGreeting_returnsGreetingPhrase() = runTest {
        phraseDao.insertPhrase(Phrase(type = "greeting", saying = "Hey friend"))
        phraseDao.insertPhrase(Phrase(type = "first", saying = "you are"))

        val greeting = phraseDao.getGreeting()

        assertEquals("Hey friend", greeting)
    }

    @Test
    fun getGreeting_returnsNullWhenNoGreetings() = runTest {
        phraseDao.insertPhrase(Phrase(type = "first", saying = "you are"))

        val greeting = phraseDao.getGreeting()

        assertNull(greeting)
    }

    @Test
    fun getFirst_returnsFirstPhrase() = runTest {
        phraseDao.insertPhrase(Phrase(type = "first", saying = "you are wonderful"))

        val first = phraseDao.getFirst()

        assertEquals("you are wonderful", first)
    }

    @Test
    fun getFirst_returnsNullWhenNoFirstPhrases() = runTest {
        val first = phraseDao.getFirst()
        assertNull(first)
    }

    @Test
    fun getSecond_returnsSecondPhrase() = runTest {
        phraseDao.insertPhrase(Phrase(type = "second", saying = "and really smart"))

        val second = phraseDao.getSecond()

        assertEquals("and really smart", second)
    }

    @Test
    fun getSecond_returnsNullWhenNoSecondPhrases() = runTest {
        val second = phraseDao.getSecond()
        assertNull(second)
    }

    @Test
    fun getEnding_returnsEndingPhrase() = runTest {
        phraseDao.insertPhrase(Phrase(type = "ending", saying = "never forget it"))

        val ending = phraseDao.getEnding()

        assertEquals("never forget it", ending)
    }

    @Test
    fun getEnding_returnsNullWhenNoEndingPhrases() = runTest {
        val ending = phraseDao.getEnding()
        assertNull(ending)
    }

    @Test
    fun getGreeting_returnsOneOfMultipleGreetings() = runTest {
        phraseDao.insertPhrase(Phrase(type = "greeting", saying = "Hey"))
        phraseDao.insertPhrase(Phrase(type = "greeting", saying = "Hello"))
        phraseDao.insertPhrase(Phrase(type = "greeting", saying = "Hi"))

        val greeting = phraseDao.getGreeting()

        assertNotNull(greeting)
        assertTrue(greeting in listOf("Hey", "Hello", "Hi"))
    }

    @Test
    fun insertMultiplePhrases_allRetrievable() = runTest {
        phraseDao.insertPhrase(Phrase(type = "greeting", saying = "Hey"))
        phraseDao.insertPhrase(Phrase(type = "first", saying = "you"))
        phraseDao.insertPhrase(Phrase(type = "second", saying = "are great"))
        phraseDao.insertPhrase(Phrase(type = "ending", saying = "today"))

        val phrases = phraseDao.getAllPhrases().first()
        assertEquals(4, phrases.size)
    }
}
