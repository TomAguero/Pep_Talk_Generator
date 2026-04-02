package app.peptalkgenerator.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakePepTalkDao : PepTalkDao {

    private val pepTalks = mutableListOf<PepTalk>()
    private val pepTalksFlow = MutableStateFlow<List<PepTalk>>(emptyList())

    override suspend fun insertPepTalk(pepTalk: PepTalk) {
        val newPepTalk = if (pepTalk.id == 0) pepTalk.copy(id = (pepTalks.maxOfOrNull { it.id } ?: 0) + 1) else pepTalk
        pepTalks.add(newPepTalk)
        emitUpdate()
    }

    override suspend fun deletePepTalk(pepTalk: PepTalk) {
        pepTalks.removeAll { it.id == pepTalk.id }
        emitUpdate()
    }

    override fun getPepTalk(id: Int): Flow<PepTalk> =
        pepTalksFlow.map { list -> list.first { it.id == id } }

    override fun getFavoritePepTalks(): Flow<List<PepTalk>> =
        pepTalksFlow.map { list -> list.filter { it.favorite == true } }

    private fun emitUpdate() {
        pepTalksFlow.value = pepTalks.toList()
    }
}
