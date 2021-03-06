package ch.tutteli.atrium.verbs

import ch.tutteli.atrium.reporting.Reporter
import ch.tutteli.atrium.reporting.ReporterBuilder
import ch.tutteli.atrium.verbs.assert.assert
import ch.tutteli.atrium.verbs.assertthat.assertThat
import ch.tutteli.atrium.verbs.expect.expect

/**
 * Supplies the [Reporter] for the assertion verbs [assert], [assertThat] and [expect].
 */
@PublishedApi internal object AtriumReporterSupplier {
    /**
     * The [Reporter] for the assertion verbs [assert], [assertThat] and [expect].
     */
    val REPORTER by lazy {
        ReporterBuilder
            .withoutTranslations()
            .withDetailedObjectFormatter()
            .withDefaultAssertionFormatterController()
            .withDefaultAssertionFormatterFacade()
            .withTextSameLineAssertionPairFormatter()
            .withDefaultTextCapabilities()
            .buildOnlyFailureReporter()
    }
}
