package io.github.louistsaitszho.louisshutterstock.model

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.Credentials
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class RepositoryImpl : Repository {
    private val auth = Credentials.basic(
            "56aa0-676c4-45958-d7c9b-76a0a-4ce11",
            "88e71-95b74-b8db4-27bb2-2b810-9b1bd"
    )

    private val shutterstockService = Retrofit.Builder()
            .baseUrl("https://api.shutterstock.com/v2/images/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ShutterstockService::class.java)

    override fun getCategories(): Single<List<Category>> {
        return shutterstockService.getCategories(auth)
                .map {
                    it.data.randomSubList(5).map {
                        //TODO there has to be a better way than blockingGet
                        Category(
                                it.id,
                                it.name,
                                getImagesByCategory(it.id)
                                        .subscribeOn(Schedulers.io())
                                        .blockingGet()
                        )
                    }
                }
    }

    private fun getImagesByCategory(categoryId: Int): Single<List<Image>> {
        return shutterstockService.getImagesByCategoryId(auth, categoryId)
                .map { it.data.map { it.assets.largeThumbnail } }
    }
}

/**
 * [List.subList] but randomly: pick [limit] random [E] from your list and give it back to you
 */
private fun <E> List<E>.randomSubList(limit: Int): List<E> {
    //1. get [limit] random integers that is within this.size and 0 as a list
    val randomPositionList = (0..limit).map { Random().nextInt(this.size) }
    //2. get those items specifically with a map
    return randomPositionList.map { this[it] }
}
