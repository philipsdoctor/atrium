package ch.tutteli.atrium.reporting

import ch.tutteli.atrium.AssertionVerbFactory
import ch.tutteli.atrium.reporting.translating.ITranslator
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.include

class TextListAssertionGroupFormatterSpec : Spek({
    include(AtriumsTextFallbackAssertionFormatterSpec)
    include(AtriumsAssertionFormatterSpec)
}) {
    object AtriumsTextFallbackAssertionFormatterSpec : ch.tutteli.atrium.spec.reporting.TextListAssertionGroupFormatterSpec(
        AssertionVerbFactory, factory(), "[Atrium's TextList...Spec] ")

    object AtriumsAssertionFormatterSpec : ch.tutteli.atrium.spec.reporting.AssertionFormatterSpec(
        AssertionVerbFactory, factoryWithBullet(), "[Atrium's AssertionFormatterSpec] "
    )

    companion object {
        internal fun factory() = { bulletPoint: String, assertionFormatterController: IAssertionFormatterController, objectFormatter: IObjectFormatter, translator: ITranslator ->
            TextListAssertionGroupFormatter(bulletPoint, assertionFormatterController, TextSameLineAssertionPairFormatter(objectFormatter, translator))
        }

        internal fun factoryWithBullet() = { assertionFormatterController: IAssertionFormatterController, objectFormatter: IObjectFormatter, translator: ITranslator ->
            factory()("▪", assertionFormatterController, objectFormatter, translator)
        }
    }
}
