package app.peptalkgenerator.model

import app.peptalkgenerator.data.PepTalk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PepTalkDetailsExtensionsTest {

    @Test
    fun pepTalkDetails_toPepTalk_convertsCorrectly() {
        val details = PepTalkDetails(
            id = 5,
            pepTalk = "You are great!",
            favorite = true,
            block = false
        )

        val pepTalk = details.toPepTalk()

        assertEquals(5, pepTalk.id)
        assertEquals("You are great!", pepTalk.pepTalk)
        assertEquals(true, pepTalk.favorite)
        assertEquals(false, pepTalk.block)
    }

    @Test
    fun pepTalk_toPepTalkDetails_convertsCorrectly() {
        val pepTalk = PepTalk(
            id = 3,
            pepTalk = "Keep going!",
            favorite = true,
            block = false
        )

        val details = pepTalk.toPepTalkDetails()

        assertEquals(3, details.id)
        assertEquals("Keep going!", details.pepTalk)
        assertEquals(true, details.favorite)
        assertEquals(false, details.block)
    }

    @Test
    fun pepTalkDetails_toPepTalk_handlesNullFavoriteAndBlock() {
        val details = PepTalkDetails(
            id = 1,
            pepTalk = "Hello",
            favorite = null,
            block = null
        )

        val pepTalk = details.toPepTalk()

        assertNull(pepTalk.favorite)
        assertNull(pepTalk.block)
    }

    @Test
    fun pepTalk_toPepTalkDetails_handlesNullFavoriteAndBlock() {
        val pepTalk = PepTalk(
            id = 1,
            pepTalk = "Hello",
            favorite = null,
            block = null
        )

        val details = pepTalk.toPepTalkDetails()

        assertNull(details.favorite)
        assertNull(details.block)
    }

    @Test
    fun roundTrip_pepTalkDetails_preservesData() {
        val original = PepTalkDetails(
            id = 10,
            pepTalk = "Round trip test",
            favorite = true,
            block = false
        )

        val converted = original.toPepTalk().toPepTalkDetails()

        assertEquals(original.id, converted.id)
        assertEquals(original.pepTalk, converted.pepTalk)
        assertEquals(original.favorite, converted.favorite)
        assertEquals(original.block, converted.block)
    }

    @Test
    fun pepTalkUiState_defaultValues() {
        val state = PepTalkUiState()

        assertEquals(0, state.pepTalkDetails.id)
        assertEquals("", state.pepTalkDetails.pepTalk)
        assertNull(state.pepTalkDetails.favorite)
        assertNull(state.pepTalkDetails.block)
    }

    @Test
    fun pepTalkDetailsUIState_defaultValues() {
        val state = PepTalkDetailsUIState()

        assertEquals(0, state.pepTalkDetails.id)
        assertEquals("", state.pepTalkDetails.pepTalk)
        assertNull(state.pepTalkDetails.favorite)
        assertNull(state.pepTalkDetails.block)
    }

    @Test
    fun favoritesUiState_defaultValues() {
        val state = FavoritesUiState()

        assertEquals(emptyList<PepTalk>(), state.favoritesList)
    }
}
