package ch.tutteli.atrium.assertions

import ch.tutteli.atrium.api.cc.en_UK.contains
import ch.tutteli.atrium.api.cc.en_UK.exactly
import ch.tutteli.atrium.api.cc.en_UK.value
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context

object FloatArrayContainsSpec : Spek({

    context("float array 1.1f") {
        test("search for '1.1f' finds 1 hit") {
            ch.tutteli.atrium.assert("aaaa").contains.exactly(3).value("aa")

            floatArrayOf(1.0f).asIterable()
            ch.tutteli.atrium.assert<FloatArray>(floatArrayOf(1.1f)).contains.exactly(3).value("aa")
        }
    }

})
