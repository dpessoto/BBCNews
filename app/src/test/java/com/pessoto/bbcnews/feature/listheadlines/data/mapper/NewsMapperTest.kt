package com.pessoto.bbcnews.feature.listheadlines.data.mapper

import com.pessoto.bbcnews.feature.listheadlines.data.mappers.NewsMapper
import com.pessoto.bbcnews.feature.listheadlines.util.mockNews
import com.pessoto.bbcnews.feature.listheadlines.util.mockNewsResponse
import org.junit.Assert
import org.junit.Test

internal class NewsMapperTest {

    @Test
    fun `map Should return News When to put NewsResponse`() {
        // Given
        val mapper = NewsMapper()

        // When
        val resultMapper = mapper.map(mockNewsResponse())

        // Then
        Assert.assertEquals(resultMapper, mockNews())
    }
}