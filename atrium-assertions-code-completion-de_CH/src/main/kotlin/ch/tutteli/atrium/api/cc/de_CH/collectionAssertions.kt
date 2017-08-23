package ch.tutteli.atrium.api.cc.de_CH

import ch.tutteli.atrium.assertions._hasSize
import ch.tutteli.atrium.assertions._isEmpty
import ch.tutteli.atrium.creating.IAssertionPlant

/**
 * Makes the assertion that [IAssertionPlant.subject]'s [Collection.size] is [size].
 *
 * @return This plant to support a fluent-style API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct
 */
fun <T : Collection<*>> IAssertionPlant<T>.hatDieGroesse(size: Int): IAssertionPlant<T>
    = addAssertion(_hasSize(this, size))

/**
 * Makes the assertion that [IAssertionPlant.subject] is an empty [Collection].
 *
 * @return This plant to support a fluent-style API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct
 */
fun <T : Collection<*>> IAssertionPlant<T>.istLeer(): IAssertionPlant<T>
    = addAssertion(_isEmpty(this))
