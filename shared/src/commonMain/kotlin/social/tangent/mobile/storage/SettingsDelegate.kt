package social.tangent.mobile.storage

import com.russhwolf.settings.Settings
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

fun <T : Any> T.persistString(key: String, default: String) =
    SettingsDelegate<T, String>(key, String::class, default)
fun <T : Any> T.persistStringOrNull(key: String) =
    SettingsDelegate<T, String?>(key, String::class, null)

fun <T : Any> T.persistInt(key: String, default: Int) =
    SettingsDelegate<T, Int>(key, Int::class, default)
fun <T : Any> T.persistIntOrNull(key: String) =
    SettingsDelegate<T, Int?>(key, Int::class, null)

fun <T : Any> T.persistFloat(key: String, default: Float) =
    SettingsDelegate<T, Float>(key, Float::class, default)
fun <T : Any> T.persistFloatOrNull(key: String) =
    SettingsDelegate<T, Float?>(key, Float::class, null)

class SettingsDelegate<T : Any, V : Any?>(
    val key: String,
    val cls: KClass<*>,
    val default: V
) : ReadWriteProperty<T, V>, KoinComponent {

    private val settings: Settings by inject()

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: T, property: KProperty<*>): V {
        return when (cls) {
            String::class -> {
                if (default == null) settings.getStringOrNull(key) else settings.getString(key, default as String)
            }
            Int::class -> {
                if (default == null) settings.getIntOrNull(key) else settings.getInt(key, default as Int)
            }
            Float::class -> {
                if (default == null) settings.getFloatOrNull(key) else settings.getFloat(key, default as Float)
            }
            // TODO rest.
            else -> throw RuntimeException("Unexpected type ${cls.simpleName}")
        } as V
    }

    override fun setValue(thisRef: T, property: KProperty<*>, value: V) {
        if (value == null) {
            settings.remove(key)
            return
        }
        when (cls) {
            String::class -> {
                settings.putString(key, value as String)
            }
            Int::class -> {
                settings.putInt(key, value as Int)
            }
            Float::class -> {
                settings.putFloat(key, value as Float)
            }
            // TODO rest.
            else -> throw RuntimeException("Unexpected type ${cls.simpleName}")
        }
    }
}