package org.example.todolistserverchapter4.api.v1.oauth.config

import org.example.todolistserverchapter4.api.v1.oauth.util.OAuth2ProviderConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class OAuthWebConfig(private val oauth2ProviderConverter: OAuth2ProviderConverter) : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(oauth2ProviderConverter)
    }
}