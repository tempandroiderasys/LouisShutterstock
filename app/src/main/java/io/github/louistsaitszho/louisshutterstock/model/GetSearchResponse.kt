package io.github.louistsaitszho.louisshutterstock.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetSearchResponse(
        @Json(name = "total_count") val totalCount: Int,
        @Json(name = "search_id") val searchId: String,
        @Json(name = "per_page") val perPage: Int,
        @Json(name = "page") val page: Int,
        @Json(name = "data") val data: List<GetSearchDataResponse>
)

@JsonClass(generateAdapter = true)
data class GetSearchDataResponse(
        @Json(name = "id") val id: Long,
        @Json(name = "aspect") val aspect: Float,
        @Json(name = "media_type") val mediaType: String,
        @Json(name = "contributor") val contributor: ImageContributor,
        @Json(name = "assets") val assets: ImageAssets,
        @Json(name = "description") val description: String,
        @Json(name = "image_type") val imageType: String
)

@JsonClass(generateAdapter = true)
data class ImageAssets(
        @Json(name = "huge_thumb") val hugeThumbnail: Image,
        @Json(name = "small_thumb") val smallThumbnail: Image,
        @Json(name = "preview") val preview: Image,
        @Json(name = "large_thumb") val largeThumbnail: Image
)

@JsonClass(generateAdapter = true)
data class Image(
        @Json(name = "url") val url: String,
        @Json(name = "width") val width: Int,
        @Json(name = "height") val height: Int
)

@JsonClass(generateAdapter = true)
data class ImageContributor(val id: Long)