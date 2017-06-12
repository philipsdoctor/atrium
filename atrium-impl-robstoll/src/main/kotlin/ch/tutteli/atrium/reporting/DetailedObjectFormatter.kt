package ch.tutteli.atrium.reporting

import ch.tutteli.atrium.reporting.translating.ITranslator
import ch.tutteli.atrium.reporting.translating.TranslatableRawString
import kotlin.reflect.KClass

/**
 * Formats an object by using its [toString] representation, its [Class.getName] and its [System.identityHashCode]
 * (in most cases).
 *
 * Some objects are treated differently, refer to [format] for details.
 *
 * The aim of representing more information than just [toString] is to avoid situations where an assert may fail
 * and [toString] does not distinguish two compared objects.
 * Consider the following error message "error, assert: 1 to be 1" would not be very helpful.
 * "error, assert: 1 (Int <123>) to be 1 (Double <456>)" on the other hand is helpful.
 *
 * @property translator The [ITranslator] used to translate [TranslatableRawString]s.
 *
 * @constructor
 * @param translator The [ITranslator] used to translate [TranslatableRawString]s.
 */
internal class DetailedObjectFormatter(private val translator: ITranslator) : IObjectFormatter {

    /**
     * Returns a formatted version of the given [value].
     *
     * The following rules apply for the representation of an object:
     * - `null` is represented as [RawString.NULL].[RawString.string]
     * - [Char] is put in apostrophes
     * - [Boolean] is represented with its [toString] representation
     * - [String] is put in quotes and its [Class.getName] is omitted
     * - [CharSequence] is put in quotes, but [KClass.qualifiedName] is used in contrast to [String]
     * - [RawString] is represented as [RawString.string]
     * - [TranslatableRawString] is represented as result of its translation (by [translator])
     * - [Class] is represented as "[Class.getSimpleName] ([Class.getName])"
     * - [KClass] is represented as "[Class.getSimpleName] ([Class.getName])"
     * - [Enum] is represented as "[toString] ([Class.getName])
     * - All other objects are represented as "[toString] ([Class.getName] <[System.identityHashCode]>)"
     *
     * @param value The value which shall be formatted.
     *
     * @return The formatted [value].
     */
    override fun format(value: Any?): String = when (value) {
        null -> RawString.NULL.string
        is Char -> "'$value'"
        is Boolean -> value.toString()
        is String -> format(value)
        is CharSequence -> format(value)
        is RawString -> value.string
        is TranslatableRawString -> translator.translate(value.translatable)
        is Class<*> -> format(value)
        is KClass<*> -> format(value)
        is Enum<*> -> format(value)
        else -> value.toString() + INDENT + classNameAndIdentity(value)
    }

    private fun format(string: String) = "\"$string\"" + INDENT + identityHash(string)
    private fun format(charSequence: CharSequence) = "\"$charSequence\"" + INDENT + classNameAndIdentity(charSequence)
    private fun format(clazz: Class<*>) = "${clazz.simpleName} (${clazz.name})"
    private fun format(clazz: KClass<*>): String {
        val kotlin = "${clazz.simpleName} (${clazz.qualifiedName})"
        return if (clazz.qualifiedName == clazz.java.name) {
            kotlin
        } else if (clazz.java.isPrimitive) {
            "$kotlin -- Class: ${clazz.java.simpleName}"
        } else {
            "$kotlin -- Class: ${format(clazz.java)}"
        }
    }

    private fun format(enum: Enum<*>) = enum.toString() + INDENT + "(" + enum::class.java.name + ")"

    private fun classNameAndIdentity(any: Any)
        = "(${any::class.java.name} ${identityHash(any)})"

    private fun identityHash(any: Any) = "<${System.identityHashCode(any)}>"

    companion object {
        internal const val INDENT: String = "        "
    }
}
