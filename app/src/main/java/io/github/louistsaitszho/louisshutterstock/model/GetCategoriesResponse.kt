package io.github.louistsaitszho.louisshutterstock.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCategoriesResponse(
        val data: List<GetCategoriesDataResponse> = emptyList()
)

@JsonClass(generateAdapter = true)
data class GetCategoriesDataResponse(
        val id: Int,
        val name: String
)

data class Category(
        val id: Int,
        val name: String,
        val images: List<Image> = emptyList()
)