package app.peptalkgenerator.notification

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NotificationPreferencesTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        // Clear prefs before each test
        context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
            .edit()
            .clear()
            .commit()
    }

    @Test
    fun isEnabled_defaultsToTrue() {
        assertTrue(NotificationPreferences.isEnabled(context))
    }

    @Test
    fun isEnabled_returnsFalseWhenDisabled() {
        context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("notifications_enabled", false)
            .commit()

        assertFalse(NotificationPreferences.isEnabled(context))
    }

    @Test
    fun isEnabled_returnsTrueWhenEnabled() {
        context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("notifications_enabled", true)
            .commit()

        assertTrue(NotificationPreferences.isEnabled(context))
    }

    @Test
    fun getHour_defaultsToSchedulerDefaultHour() {
        val hour = NotificationPreferences.getHour(context)

        assertEquals(MorningNotificationScheduler.DEFAULT_HOUR, hour)
    }

    @Test
    fun getHour_returnsStoredValue() {
        context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
            .edit()
            .putInt("notification_hour", 10)
            .commit()

        assertEquals(10, NotificationPreferences.getHour(context))
    }

    @Test
    fun getMinute_defaultsToSchedulerDefaultMinute() {
        val minute = NotificationPreferences.getMinute(context)

        assertEquals(MorningNotificationScheduler.DEFAULT_MINUTE, minute)
    }

    @Test
    fun getMinute_returnsStoredValue() {
        context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
            .edit()
            .putInt("notification_minute", 45)
            .commit()

        assertEquals(45, NotificationPreferences.getMinute(context))
    }

    @Test
    fun allPreferences_readBackAfterWrite() {
        val prefs = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean("notifications_enabled", false)
            .putInt("notification_hour", 14)
            .putInt("notification_minute", 30)
            .commit()

        assertFalse(NotificationPreferences.isEnabled(context))
        assertEquals(14, NotificationPreferences.getHour(context))
        assertEquals(30, NotificationPreferences.getMinute(context))
    }
}
