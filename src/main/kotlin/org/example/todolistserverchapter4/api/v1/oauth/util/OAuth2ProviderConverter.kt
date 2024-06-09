package org.example.todolistserverchapter4.api.v1.oauth.util

import org.example.todolistserverchapter4.api.v1.oauth.common.OAuth2Provider
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class OAuth2ProviderConverter : Converter<String, OAuth2Provider> {
    override fun convert(source: String): OAuth2Provider? {
        return OAuth2Provider.entries.firstOrNull { it.name.equals(source, ignoreCase = true) }
    }
}