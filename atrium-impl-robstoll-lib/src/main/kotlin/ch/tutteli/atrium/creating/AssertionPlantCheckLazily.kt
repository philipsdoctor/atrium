package ch.tutteli.atrium.creating

import ch.tutteli.atrium.assertions.IAssertion
import ch.tutteli.atrium.assertions.BasicAssertion
import ch.tutteli.atrium.reporting.translating.ITranslatable
import java.util.*

/**
 * An [IAssertionPlant] which does not check each added [IAssertion] immediately but only if
 * [checkAssertions] is called and does not fail fast.
 *
 * @param T The type of the [subject] of this [IAssertionPlant].
 *
 * @constructor An [IAssertionPlant] which does not check each added [IAssertion] immediately but only if
 *              [checkAssertions] is called and does not fail fast.
 * @param commonFields The [IAssertionPlantWithCommonFields.CommonFields] of this [IAssertionPlant].
 *
 * This class is not thread-safe, but is also not intended for long-running procedures.
 */
open class AssertionPlantCheckLazily<out T : Any>(
    override val commonFields: IAssertionPlantWithCommonFields.CommonFields<T>) : IAssertionPlant<T> {

    /**
     * All made assertions so far. They are cleared every time one calls [checkAssertions].
     * This list is intentionally not thread-safe, this class is not intended for multi-thread usage.
     */
    private val assertions: MutableList<IAssertion> = ArrayList()


    override final fun createAndAddAssertion(description: ITranslatable, expected: Any, test: () -> Boolean)
        = addAssertion(BasicAssertion(description, expected, test))

    override fun addAssertion(assertion: IAssertion): IAssertionPlant<T> {
        assertions.add(assertion)
        return this
    }

    override final fun checkAssertions(): IAssertionPlant<T> {
        try {
            commonFields.check(assertions)
        } finally {
            assertions.clear()
        }
        return this
    }
}
