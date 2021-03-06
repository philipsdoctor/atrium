package ch.tutteli.atrium.assertions.charsequence.contains.creators

import ch.tutteli.atrium.assertions.AssertionGroup
import ch.tutteli.atrium.assertions.charsequence.contains.CharSequenceContains
import ch.tutteli.atrium.assertions.charsequence.contains.builders.CharSequenceContainsCheckerBuilder
import ch.tutteli.atrium.assertions.charsequence.contains.searchbehaviours.CharSequenceContainsIgnoringCaseSearchBehaviour
import ch.tutteli.atrium.assertions.charsequence.contains.searchbehaviours.CharSequenceContainsNoOpSearchBehaviour
import ch.tutteli.atrium.assertions.charsequence.contains.searchers.CharSequenceContainsIgnoringCaseIndexSearcher
import ch.tutteli.atrium.assertions.charsequence.contains.searchers.CharSequenceContainsIgnoringCaseRegexSearcher
import ch.tutteli.atrium.assertions.charsequence.contains.searchers.CharSequenceContainsIndexSearcher
import ch.tutteli.atrium.assertions.charsequence.contains.searchers.CharSequenceContainsRegexSearcher
import ch.tutteli.atrium.reporting.translating.Translatable

fun <T : CharSequence> _containsValues(
    checker: CharSequenceContainsCheckerBuilder<T, CharSequenceContainsNoOpSearchBehaviour>,
    expected: Any,
    otherExpected: Array<out Any>
): AssertionGroup
    = checkOnlyAllowedTypeAndCreateAssertionGroup(checker, CharSequenceContainsIndexSearcher(), expected, otherExpected)

fun <T : CharSequence> _containsValuesIgnoringCase(
    checker: CharSequenceContainsCheckerBuilder<T, CharSequenceContainsIgnoringCaseSearchBehaviour>,
    expected: Any,
    otherExpected: Array<out Any>
): AssertionGroup
    = checkOnlyAllowedTypeAndCreateAssertionGroup(checker, CharSequenceContainsIgnoringCaseIndexSearcher(), expected, otherExpected)

private fun <T : CharSequence, S : CharSequenceContains.SearchBehaviour> checkOnlyAllowedTypeAndCreateAssertionGroup(
    checker: CharSequenceContainsCheckerBuilder<T, S>,
    searcher: CharSequenceContains.Searcher<S>,
    expected: Any,
    otherExpected: Array<out Any>
): AssertionGroup {
    listOf(expected, *otherExpected).forEach {
        require(it is CharSequence || it is Number || it is Char) {
            "Only CharSequence, Number and Char are allowed, `$it` given.\nWe provide an API with Any for convenience (so that you can mix String and Int for instance)."
        }
    }
    return createAssertionGroup(checker, searcher, expected, otherExpected)
}

fun <T : CharSequence> _containsDefaultTranslationOf(
    checker: CharSequenceContainsCheckerBuilder<T, CharSequenceContainsNoOpSearchBehaviour>,
    expected: Translatable,
    otherExpected: Array<out Translatable>
): AssertionGroup
    = _containsValues(checker, expected.getDefault(), mapDefaultTranslations(otherExpected))

fun <T : CharSequence> _containsDefaultTranslationOfIgnoringCase(
    checker: CharSequenceContainsCheckerBuilder<T, CharSequenceContainsIgnoringCaseSearchBehaviour>,
    expected: Translatable,
    otherExpected: Array<out Translatable>
): AssertionGroup
    = _containsValuesIgnoringCase(checker, expected.getDefault(), mapDefaultTranslations(otherExpected))

private fun mapDefaultTranslations(otherExpected: Array<out Translatable>) =
    otherExpected.map { it.getDefault() }.toTypedArray()

fun <T : CharSequence> _containsRegex(
    checker: CharSequenceContainsCheckerBuilder<T, CharSequenceContainsNoOpSearchBehaviour>,
    expected: String,
    otherExpected: Array<out String>
): AssertionGroup
    = createAssertionGroup(checker, CharSequenceContainsRegexSearcher(), expected, otherExpected)

fun <T : CharSequence> _containsRegexIgnoringCase(
    checker: CharSequenceContainsCheckerBuilder<T, CharSequenceContainsIgnoringCaseSearchBehaviour>,
    expected: String,
    otherExpected: Array<out String>
): AssertionGroup
    = createAssertionGroup(checker, CharSequenceContainsIgnoringCaseRegexSearcher(), expected, otherExpected)

private fun <T : CharSequence, S : CharSequenceContains.SearchBehaviour> createAssertionGroup(
    checker: CharSequenceContainsCheckerBuilder<T, S>,
    searcher: CharSequenceContains.Searcher<S>,
    expected: Any,
    otherExpected: Array<out Any>
): AssertionGroup {
    return CharSequenceContainsAssertionCreator<T, S>(checker.containsBuilder.searchBehaviour, searcher, checker.checkers)
        .createAssertionGroup(checker.containsBuilder.plant, expected, otherExpected)
}
