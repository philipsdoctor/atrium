package ch.tutteli.atrium.spec.reporting

import ch.tutteli.atrium.AtriumFactory
import ch.tutteli.atrium.api.cc.en_UK.isEmpty
import ch.tutteli.atrium.assertions.*
import ch.tutteli.atrium.assertions.DescriptionAnyAssertion.TO_BE
import ch.tutteli.atrium.reporting.AssertionFormatterFacade
import ch.tutteli.atrium.reporting.Reporter
import ch.tutteli.atrium.reporting.translating.UsingDefaultTranslator
import ch.tutteli.atrium.spec.AssertionVerb
import ch.tutteli.atrium.spec.AssertionVerbFactory
import ch.tutteli.atrium.spec.describeFun
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.SpecBody
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it

abstract class OnlyFailureReporterSpec(
    verbs: AssertionVerbFactory,
    testeeFactory: (AssertionFormatterFacade) -> Reporter,
    describePrefix: String = "[Atrium] "
) : Spek({

    fun describeFun(vararg funName: String, body: SpecBody.() -> Unit)
        = describeFun(describePrefix, funName, body = body)

    val translator = UsingDefaultTranslator()
    val facade = AtriumFactory.newAssertionFormatterFacade(AtriumFactory.newAssertionFormatterController())
    facade.register {
        AtriumFactory.newTextFallbackAssertionFormatter(
            mapOf(RootAssertionGroupType::class.java to "[]"),
            it,
            AtriumFactory.newDetailedObjectFormatter(translator), translator)
    }
    val testee = testeeFactory(facade)

    describeFun(testee::format.name) {
        val sb = StringBuilder()
        val assertion = object : Assertion {
            override fun holds() = true
        }
        val basicAssertion = BasicDescriptiveAssertion(TO_BE, 0, true)
        val basicAssertionAnonymous = object : DescriptiveAssertion {
            override val expected = 1
            override val description = AssertionVerb.VERB
            override fun holds() = true
        }

        val assertionGroupAnonymous = object : AssertionGroup {
            override val type = RootAssertionGroupType
            override val name = AssertionVerb.VERB
            override val subject = 0
            override val assertions = listOf(assertion, basicAssertion, basicAssertionAnonymous)
        }
        val assertionGroup = AssertionGroup.Builder.root.create(AssertionVerb.VERB, 1, listOf(assertion, basicAssertion, basicAssertionAnonymous, assertionGroupAnonymous))

        mapOf(
            "object: ${Assertion::class.simpleName}" to assertion,
            "object: ${DescriptiveAssertion::class.simpleName}" to basicAssertionAnonymous,
            "${basicAssertion::class.simpleName}" to basicAssertion,
            "object: ${AssertionGroup::class.simpleName}" to assertionGroupAnonymous,
            "${assertionGroup::class.simpleName}" to assertionGroup
        ).forEach { (typeRepresentation, assertion) ->
            it("does not append anything if $typeRepresentation holds") {
                testee.format(assertion, sb)
                verbs.checkImmediately(sb).isEmpty()
            }
        }

        context("dependencies") {
            val assertionFormatterFacade = mock<AssertionFormatterFacade>()
            val testeeWithMockedFacade = testeeFactory(assertionFormatterFacade)

            it("delegates to ${assertionFormatterFacade::class.java.simpleName}") {
                testeeWithMockedFacade.format(basicAssertion, sb)
                verify(assertionFormatterFacade).format(eq(basicAssertion), eq(sb), any())
            }
        }
    }
})
