package uk.ac.reigate.config.testing

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile


@Configuration
@Profile("test")
class TestContextConfiguration {
}
