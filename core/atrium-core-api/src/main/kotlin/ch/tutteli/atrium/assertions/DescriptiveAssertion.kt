package ch.tutteli.atrium.assertions

import ch.tutteli.atrium.reporting.translating.Translatable

/**
 * The base interface for [Assertion]s which only consists of the [expected] result with a complementary [description].
 */
interface DescriptiveAssertion : Assertion {
    /**
     * The expected result.
     */
    val expected: Any

    /**
     * The complementary description to the [expected] result such as `contains`, `is not` etc.
     */
    val description: Translatable
}
