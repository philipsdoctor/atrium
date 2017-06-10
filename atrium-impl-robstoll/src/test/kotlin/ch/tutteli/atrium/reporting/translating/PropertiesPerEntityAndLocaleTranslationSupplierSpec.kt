package ch.tutteli.atrium.reporting.translating

import ch.tutteli.atrium.AssertionVerbFactory
import ch.tutteli.atrium.reporting.ReporterBuilder
import ch.tutteli.atrium.spec.reporting.translating.TranslationSupplierSpec
import java.util.*

object PropertiesPerEntityAndLocaleTranslationSupplierSpec : TranslationSupplierSpec(
    AssertionVerbFactory,
    ReporterBuilder
        .withTranslations(PropertiesPerEntityAndLocaleTranslationSupplier(), Locale("de", "CH"), Locale("fr"))
        .withDetailedObjectFormatter()
        .withSameLineAssertionMessageFormatter()
        .buildOnlyFailureReporting()
)