package ch.tutteli.atrium.assertions

import ch.tutteli.atrium.assertions.iterable.contains.builders.ArrayContainsBuilder
import ch.tutteli.atrium.assertions.iterable.contains.builders.FloatArrayContainsBuilder
import ch.tutteli.atrium.assertions.iterable.contains.searchbehaviours.IterableContainsNoOpSearchBehaviour
import ch.tutteli.atrium.creating.AssertionPlant

fun <E> _containsBuilder(plant: AssertionPlant<Array<out E>>)
    = ArrayContainsBuilder(plant, IterableContainsNoOpSearchBehaviour())

fun _containsBuilder(plant: AssertionPlant<FloatArray>)
    = FloatArrayContainsBuilder(plant, IterableContainsNoOpSearchBehaviour())

fun <E> _containsNotBuilder(plant: AssertionPlant<Array<out E>>)
    = ArrayContainsBuilder(plant, IterableContainsNoOpSearchBehaviour())

