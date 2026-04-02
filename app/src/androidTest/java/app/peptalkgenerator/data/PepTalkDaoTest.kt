package app.peptalkgenerator.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PepTalkDaoTest {

    private lateinit var pepTalkDao: PepTalkDao
    private lateinit var db: PepTalksDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PepTalksDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        pepTalkDao = db.pepTalkDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertPepTalk_andReadBack() = runTest {
        val pepTalk = PepTalk(pepTalk = "You are amazing!", favorite = true, block = false)
        pepTalkDao.insertPepTalk(pepTalk)

        val favorites = pepTalkDao.getFavoritePepTalks().first()
        assertEquals(1, favorites.size)
        assertEquals("You are amazing!", favorites[0].pepTalk)
    }

    @Test
    fun deletePepTalk_removesFromDatabase() = runTest {
        val pepTalk = PepTalk(pepTalk = "Delete me", favorite = true, block = false)
        pepTalkDao.insertPepTalk(pepTalk)

        val inserted = pepTalkDao.getFavoritePepTalks().first()[0]
        pepTalkDao.deletePepTalk(inserted)

        val favorites = pepTalkDao.getFavoritePepTalks().first()
        assertTrue(favorites.isEmpty())
    }

    @Test
    fun getPepTalk_returnsPepTalkById() = runTest {
        pepTalkDao.insertPepTalk(PepTalk(pepTalk = "Talk 1", favorite = true, block = false))
        pepTalkDao.insertPepTalk(PepTalk(pepTalk = "Talk 2", favorite = true, block = false))

        val allFavorites = pepTalkDao.getFavoritePepTalks().first()
        val secondId = allFavorites.find { it.pepTalk == "Talk 2" }!!.id

        val result = pepTalkDao.getPepTalk(secondId).first()
        assertEquals("Talk 2", result.pepTalk)
    }

    @Test
    fun getFavoritePepTalks_returnsOnlyFavorites() = runTest {
        pepTalkDao.insertPepTalk(PepTalk(pepTalk = "Fav 1", favorite = true, block = false))
        pepTalkDao.insertPepTalk(PepTalk(pepTalk = "Not fav", favorite = false, block = false))
        pepTalkDao.insertPepTalk(PepTalk(pepTalk = "Fav 2", favorite = true, block = false))

        val favorites = pepTalkDao.getFavoritePepTalks().first()

        assertEquals(2, favorites.size)
        assertTrue(favorites.all { it.favorite == true })
    }

    @Test
    fun getFavoritePepTalks_returnsEmptyWhenNoFavorites() = runTest {
        pepTalkDao.insertPepTalk(PepTalk(pepTalk = "Not fav", favorite = false, block = false))

        val favorites = pepTalkDao.getFavoritePepTalks().first()

        assertTrue(favorites.isEmpty())
    }

    @Test
    fun insertPepTalk_withNullFavorite_notReturnedInFavorites() = runTest {
        pepTalkDao.insertPepTalk(PepTalk(pepTalk = "Null fav", favorite = null, block = null))

        val favorites = pepTalkDao.getFavoritePepTalks().first()

        assertTrue(favorites.isEmpty())
    }

    @Test
    fun insertMultiplePepTalks_allFavoritesRetrievable() = runTest {
        repeat(5) { i ->
            pepTalkDao.insertPepTalk(PepTalk(pepTalk = "Talk $i", favorite = true, block = false))
        }

        val favorites = pepTalkDao.getFavoritePepTalks().first()

        assertEquals(5, favorites.size)
    }
}
