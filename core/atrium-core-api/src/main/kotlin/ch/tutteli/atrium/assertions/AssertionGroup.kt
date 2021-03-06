package ch.tutteli.atrium.assertions

import ch.tutteli.atrium.reporting.translating.Translatable

/**
 * The base interface for [Assertion] groups, providing a default implementation for [Assertion.holds]
 * which returns `true` if all its [assertions] hold.
 */
interface AssertionGroup : Assertion {
    /**
     * The name of the group.
     */
    val name: Translatable
    /**
     * The type of the group, e.g. [RootAssertionGroupType].
     */
    val type: AssertionGroupType

    /**
     * The subject for which the [assertions] are defined.
     */
    val subject: Any
    /**
     * The assertions of this group, which are defined for [subject].
     */
    val assertions: List<Assertion>

    /**
     * Holds if all its [assertions] hold.
     *
     * @return `true` if all [assertions] hold; `false` otherwise.
     */
    override fun holds() = assertions.all(Assertion::holds)

    /**
     * Represents a builder for [AssertionGroup].
     */
    object Builder {
        val root = BasicAssertionGroupBuilder(RootAssertionGroupType)
        val list = BasicAssertionGroupBuilder(DefaultListAssertionGroupType)
        val feature = BasicAssertionGroupBuilder(DefaultFeatureAssertionGroupType)
        val summary = BasicAssertionGroupBuilder(DefaultSummaryAssertionGroupType)
        val explanatory = ExplanatoryAssertionGroupOption()
        val invisible = InvisibleAssertionGroupBuilder()

        fun withType(groupType: AssertionGroupType) = BasicAssertionGroupBuilder(groupType)

        class BasicAssertionGroupBuilder(private val groupType: AssertionGroupType) {
            fun create(name: Translatable, subject: Any, assertion: Assertion)
                = create(name, subject, listOf(assertion))

            fun create(name: Translatable, subject: Any, assertions: List<Assertion>): AssertionGroup
                = BasicAssertionGroup(groupType, name, subject, assertions)
        }

        class ExplanatoryAssertionGroupOption {
            val withDefault = ExplanatoryAssertionGroupBuilder(DefaultExplanatoryAssertionGroupType)
            val withWarning = ExplanatoryAssertionGroupBuilder(WarningAssertionGroupType)
            fun withType(groupType: ExplanatoryAssertionGroupType) = ExplanatoryAssertionGroupBuilder(groupType)
        }

        class ExplanatoryAssertionGroupBuilder(private val groupType: ExplanatoryAssertionGroupType) {
            fun create(assertion: Assertion)
                = create(listOf(assertion))

            fun create(assertions: List<Assertion>): ExplanatoryAssertionGroup
                = ExplanatoryAssertionGroup(groupType, assertions)
        }

        class InvisibleAssertionGroupBuilder {
            fun create(assertion: Assertion)
                = create(listOf(assertion))

            fun create(assertions: List<Assertion>): InvisibleAssertionGroup
                = InvisibleAssertionGroup(assertions)
        }
    }
}
