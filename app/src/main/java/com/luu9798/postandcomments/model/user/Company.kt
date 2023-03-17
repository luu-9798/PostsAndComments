package com.luu9798.postandcomments.model.user

import com.luu9798.postandcomments.database.`object`.CompanyRealm

data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String
) {
    fun toRealm(): CompanyRealm {
        return CompanyRealm(
            name = name,
            catchPhrase = catchPhrase,
            bs = bs
        )
    }
}
