package ch.tutteli.atrium.assertions.charsequence.contains.builders

import ch.tutteli.atrium.assertions.basic.contains.builders.validateAtMost
import ch.tutteli.atrium.assertions.charsequence.contains.CharSequenceContains.Checker
import ch.tutteli.atrium.assertions.charsequence.contains.CharSequenceContains.SearchBehaviour
import ch.tutteli.atrium.assertions.charsequence.contains.checkers.CharSequenceContainsAtLeastChecker
import ch.tutteli.atrium.assertions.charsequence.contains.checkers.CharSequenceContainsAtMostChecker

/**
 * The base class for builders which create a `contains at least once but at most` check within the fluent API of a
 * sophisticated `contains` assertion for [CharSequence].
 *
 * @param T The input type of the search.
 * @param S The search behaviour which should be applied for the input of the search.
 *
 * @property times The number which the check will compare against the actual number of times an expected object
 *   is found in the input of the search.
 *
 * @constructor The base class for builders which create a `contains at most` check within the fluent API of a
 *   sophisticated `contains` assertion for [CharSequence].
 * @param times The number which the check will compare against the actual number of times an expected object is
 *   found in the input of the search.
 * @param containsBuilder The previously used [CharSequenceContainsBuilder].
 * @param nameContainsNotFun The name of the function which represents a `CharSequence contains not` assertion.
 * @param atMostCall The name of the function which was called and created this builder.
 * @param atLeastCall The name of the function which represents a `CharSequence contains at least` assertion.
 * @param exactlyCall The name of the function which represents a `CharSequence contains exactly` assertion.
 */
abstract class CharSequenceContainsAtMostCheckerBuilderBase<out T : CharSequence, out S : SearchBehaviour>(
    val times: Int,
    containsBuilder: CharSequenceContainsBuilder<T, S>,
    nameContainsNotFun: String,
    atMostCall: (Int) -> String,
    atLeastCall: (Int) -> String,
    exactlyCall: (Int) -> String
) : CharSequenceContainsCheckerBuilder<T, S>(containsBuilder) {

    init {
        validateAtMost(times, atMostCall, atLeastCall, exactlyCall)
    }

    override val checkers: List<Checker> = listOf(
        CharSequenceContainsAtLeastChecker(1, nameContainsNotFun, atLeastCall),
        CharSequenceContainsAtMostChecker(times, nameContainsNotFun, atMostCall)
    )
}
