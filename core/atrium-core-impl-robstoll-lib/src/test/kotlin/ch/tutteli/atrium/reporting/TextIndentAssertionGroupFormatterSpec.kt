package ch.tutteli.atrium.reporting

import ch.tutteli.atrium.AssertionVerbFactory
import ch.tutteli.atrium.assertions.BulletPointIdentifier
import ch.tutteli.atrium.assertions.DefaultIndentAssertionGroupType
import ch.tutteli.atrium.assertions.IndentAssertionGroupType
import ch.tutteli.atrium.reporting.translating.Translator
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.include

class TextIndentAssertionGroupFormatterSpec : Spek({

    include(AtriumsTextIndentAssertionGroupFormatterSpec)
    include(AtriumsEmptyNameAndSubjectAssertionGroupFormatterSpec)
    include(AtriumsSingleAssertionGroupTypeFormatterSpec)
    include(AtriumsAssertionFormatterSpec)

}) {
    object AtriumsTextIndentAssertionGroupFormatterSpec : ch.tutteli.atrium.spec.reporting.TextIndentAssertionGroupFormatterSpec(
        AssertionVerbFactory, ::TextIndentAssertionGroupFormatter, "[Atrium's IndentGroup...Spec] ")

    object AtriumsEmptyNameAndSubjectAssertionGroupFormatterSpec : ch.tutteli.atrium.spec.reporting.EmptyNameAndSubjectAssertionGroupFormatterSpec<IndentAssertionGroupType>(
        AssertionVerbFactory, factory(),
        IndentAssertionGroupType::class.java,
        DefaultIndentAssertionGroupType,
        object : IndentAssertionGroupType {},
        "[Atrium's EmptyNameAndSubject...Spec] ")

    object AtriumsSingleAssertionGroupTypeFormatterSpec : ch.tutteli.atrium.spec.reporting.SingleAssertionGroupTypeFormatterSpec<IndentAssertionGroupType>(
        AssertionVerbFactory, factoryWithBulletPoints(),
        IndentAssertionGroupType::class.java,
        DefaultIndentAssertionGroupType,
        object : IndentAssertionGroupType {},
        "[Atrium's SingleAssertionGroupType...Spec] "
    )

    object AtriumsAssertionFormatterSpec : ch.tutteli.atrium.spec.reporting.AssertionFormatterSpec(
        AssertionVerbFactory, factoryWithBulletPoints(), "[Atrium's AssertionFormatterSpec] ")

    companion object {
        fun factory() = { assertionFormatterController: AssertionFormatterController ->
            TextIndentAssertionGroupFormatter(mapOf(IndentAssertionGroupType::class.java to "**"), assertionFormatterController)
        }

        fun factoryWithBulletPoints() = { _: Map<Class<out BulletPointIdentifier>, String>, assertionFormatterController: AssertionFormatterController, _: ObjectFormatter, _: Translator ->
            TextIndentAssertionGroupFormatter(mapOf(IndentAssertionGroupType::class.java to "**"), assertionFormatterController)
        }
    }
}
