package app.peptalkgenerator.widget

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.getAppWidgetState
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.color.ColorProvider
import app.peptalkgenerator.PepTalkApplication

private val pepTalkTextKey = stringPreferencesKey("widget_pep_talk_text")

class PepTalkWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val application = context.applicationContext as PepTalkApplication

        // Seed the widget with an initial talk if not yet set
        val currentPrefs = getAppWidgetState(context, PreferencesGlanceStateDefinition, id)
        if (currentPrefs[pepTalkTextKey] == null) {
            val initialTalk = application.pepTalkRepository.generateNewTalk()
            updateAppWidgetState(context, PreferencesGlanceStateDefinition, id) { prefs ->
                prefs.toMutablePreferences().apply {
                    this[pepTalkTextKey] = initialTalk
                }
            }
        }

        provideContent {
            val prefs = currentState<Preferences>()
            val pepTalkText = prefs[pepTalkTextKey] ?: "You've got this! Keep going!"
            WidgetContent(pepTalkText)
        }
    }
}

@androidx.compose.runtime.Composable
private fun WidgetContent(pepTalkText: String) {
    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ColorProvider(day = Color(0xFF6A3FD1), night = Color(0xFF6A3FD1))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Text(
                text = pepTalkText,
                style = TextStyle(
                    color = ColorProvider(day = Color.White, night = Color.White),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                ),
                modifier = GlanceModifier.fillMaxWidth()
            )
            androidx.glance.Button(
                text = "New Pep Talk",
                onClick = actionRunCallback<GenerateNewTalkAction>(),
                modifier = GlanceModifier.padding(top = 12.dp)
            )
        }
    }
}

class GenerateNewTalkAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val application = context.applicationContext as PepTalkApplication
        val newTalk = application.pepTalkRepository.generateNewTalk()
        updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId) { prefs ->
            prefs.toMutablePreferences().apply {
                this[pepTalkTextKey] = newTalk
            }
        }
        PepTalkWidget().update(context, glanceId)
    }
}

class PepTalkWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = PepTalkWidget()
}
