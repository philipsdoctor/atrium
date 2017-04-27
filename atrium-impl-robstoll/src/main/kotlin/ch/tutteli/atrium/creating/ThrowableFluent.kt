package ch.tutteli.atrium.creating

import ch.tutteli.atrium.AtriumFactory
import ch.tutteli.atrium.checking.IAssertionChecker
import ch.tutteli.atrium.reporting.translating.IEnTranslatable
import kotlin.reflect.KClass

/**
 * Provides [toThrow] methods for making assertions about a [Throwable]
 * which one expects was thrown.
 *
 * @property commonFields The [commonFields]'s [subject][IAssertionPlantWithCommonFields.CommonFields.subject]
 *        represents the thrown [Throwable] and will be used in [ThrowableFluent.toThrow]. Its method
 *        [IAssertionPlantWithCommonFields.CommonFields.fail] is used for failure reporting etc.
 *
 * @constructor Use [AtriumFactory.newThrowableFluent] to create an instance.
 * @param commonFields The [commonFields]'s [subject][IAssertionPlantWithCommonFields.CommonFields.subject]
 *        represents the thrown [Throwable] and will be used in [ThrowableFluent.toThrow]. Its method
 *        [IAssertionPlantWithCommonFields.CommonFields.fail] could be used for failure reporting etc.
 */
class ThrowableFluent internal constructor(val commonFields: IAssertionPlantWithCommonFields.CommonFields<Throwable?>) {

    private constructor(assertionVerb: String, throwable: Throwable?, assertionChecker: IAssertionChecker)
        : this(IAssertionPlantWithCommonFields.CommonFields(assertionVerb, throwable, assertionChecker))

    /**
     * Makes an assertion about the [commonFields]'s [subject][IAssertionPlantWithCommonFields.CommonFields.subject]
     * that it is of the expected type [TExpected] and reports an error if subject is `null` or another type
     * than the expected one.
     *
     * @return This builder to support a fluent-style API.
     *
     * @throws AssertionError In case the made assertion fails.
     * @throws IllegalStateException In case reporting a failure does not throw itself.
     */
    inline fun <reified TExpected : Throwable> toThrow(): IAssertionPlant<TExpected>
        = toThrow(TExpected::class)


    /**
     * Use the overload with reified type parameter whenever possible.
     *
     * Makes an assertion about the [commonFields]'s [subject][IAssertionPlantWithCommonFields.CommonFields.subject]
     * that it is of the expected type [TExpected] and reports an error if subject is `null` or another type
     * than the expected one.
     *
     * @param expectedType The expected type of the thrown [Throwable].
     *
     * @return This builder to support a fluent-style API.
     *
     * @throws AssertionError In case the made assertion fails.
     * @throws IllegalStateException In case reporting a failure does not throw itself.
     */
    fun <TExpected : Throwable> toThrow(expectedType: KClass<TExpected>): IAssertionPlant<TExpected>
        = AtriumFactory.newDownCastBuilder(Translatable.IS_A, expectedType, commonFields)
        .withNullRepresentation(Translatable.NO_EXCEPTION_OCCURRED)
        .cast()

    /**
     * Makes an assertion about the [commonFields]'s [subject][IAssertionPlantWithCommonFields.CommonFields.subject]
     * that it is of the expected type [TExpected] and reports an error if subject is null or another type
     * than the expected one -- furthermore it [createAssertions] which are checked additionally as well.
     *
     * @return This builder to support a fluent-style API.
     *
     * @throws AssertionError In case the made assertion fails.
     * @throws IllegalStateException In case reporting a failure does not throw itself.
     */
    inline fun <reified TExpected : Throwable> toThrow(noinline createAssertions: IAssertionPlant<TExpected>.() -> Unit): IAssertionPlant<TExpected>
        = toThrow(TExpected::class, createAssertions)

    /**
     * Use the overload with reified type parameter whenever possible.
     *
     * Makes an assertion about the [commonFields]'s [subject][IAssertionPlantWithCommonFields.CommonFields.subject]
     * that it is of the expected type [TExpected] and reports an error if subject is null or another type
     * than the expected one -- furthermore it [createAssertions] which are checked additionally as well.
     *
     * @param expectedType The expected type of the thrown [Throwable].
     *
     * @return This builder to support a fluent-style API.
     *
     * @throws AssertionError Might throw an [AssertionError] if an assertion fails.
     * @throws IllegalStateException In case reporting a failure does not throw itself.
     */
    fun <TExpected : Throwable> toThrow(expectedType: KClass<TExpected>, createAssertions: IAssertionPlant<TExpected>.() -> Unit): IAssertionPlant<TExpected>
        = AtriumFactory.newDownCastBuilder(Translatable.IS_A, expectedType, commonFields)
        .withNullRepresentation(Translatable.NO_EXCEPTION_OCCURRED)
        .withLazyAssertions(createAssertions)
        .cast()

    enum class Translatable(override val value: String) : IEnTranslatable {
        IS_A("is a"),
        NO_EXCEPTION_OCCURRED("no exception occurred"),
        ;
    }

    companion object {
        /**
         * Creates a [ThrowableFluent] where executing [act] will determine the
         * [subject](IAssertionPlantWithCommonFields.CommonFields.subject) of [commonFields].
         *
         * @return The newly created [ThrowableFluent].
         */
        fun create(assertionVerb: String, act: () -> Unit, assertionChecker: IAssertionChecker): ThrowableFluent {
            var throwable: Throwable? = null
            try {
                act()
            } catch(t: Throwable) {
                throwable = t
            }
            return ThrowableFluent(assertionVerb, throwable, assertionChecker)
        }
    }
}
