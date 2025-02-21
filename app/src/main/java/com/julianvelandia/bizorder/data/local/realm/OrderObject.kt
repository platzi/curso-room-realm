package com.julianvelandia.bizorder.data.local.realm

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class OrderObject : RealmObject {
    @PrimaryKey
    var id: String = ""
    var customerName: String = ""
    var item: String = ""
    var total: Double = 0.0
    var imageUrl: String = ""
}