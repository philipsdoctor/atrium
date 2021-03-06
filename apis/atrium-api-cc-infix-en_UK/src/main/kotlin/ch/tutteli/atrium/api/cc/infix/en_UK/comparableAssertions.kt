package ch.tutteli.atrium.api.cc.infix.en_UK

import ch.tutteli.atrium.assertions._isGreaterOrEquals
import ch.tutteli.atrium.assertions._isGreaterThan
import ch.tutteli.atrium.assertions._isLessOrEquals
import ch.tutteli.atrium.assertions._isLessThan
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.creating.AssertionPlant

/**
 * Makes the assertion that [AssertionPlant.subject] is less than [expected].
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
infix fun <T : Comparable<T>> Assert<T>.isLessThan(expected: T)
    = addAssertion(_isLessThan(this, expected))

/**
 * Makes the assertion that [AssertionPlant.subject] is less than or equals [expected].
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
infix fun <T : Comparable<T>> Assert<T>.isLessOrEquals(expected: T)
    = addAssertion(_isLessOrEquals(this, expected))

/**
 * Makes the assertion that [AssertionPlant.subject] is greater than [expected].
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
infix fun <T : Comparable<T>> Assert<T>.isGreaterThan(expected: T)
    = addAssertion(_isGreaterThan(this, expected))

/**
 * Makes the assertion that [AssertionPlant.subject] is greater than or equals [expected].
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
infix fun <T : Comparable<T>> Assert<T>.isGreaterOrEquals(expected: T)
    = addAssertion(_isGreaterOrEquals(this, expected))

