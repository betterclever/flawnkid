package org.freactive.flawnkid

/**
 * Created by akshat on 18/3/18.
 */

data class Feeds(
        val source: Source,
        val author: String,
        val title: String,
        val description: String,
        val url: String,
        val urlToImage: String,
        val publishedAt: String
)

data class Source(
        val id: String,
        val name: String
)