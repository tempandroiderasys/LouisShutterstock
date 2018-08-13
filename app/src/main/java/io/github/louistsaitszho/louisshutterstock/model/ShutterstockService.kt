package io.github.louistsaitszho.louisshutterstock.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ShutterstockService {
    @GET("search")
    fun getImagesByCategoryId(
            @Header("Authorization") auth: String,
            @Query("category") categoryId: Int
    ): Single<GetSearchResponse>

    @GET("categories")
    fun getCategories(@Header("Authorization") auth: String): Single<GetCategoriesResponse>
}