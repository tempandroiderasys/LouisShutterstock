package io.github.louistsaitszho.louisshutterstock.model

import io.reactivex.Single

interface Repository {
    //get list of categories
    fun getCategories(): Single<List<Category>>
}