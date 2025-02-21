package com.julianvelandia.bizorder.data.local.realm

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class PreOrderObject : RealmObject {
    @PrimaryKey
    var id: Long = System.currentTimeMillis()
    var customerName: String = ""
    var item: String = ""
    var isSent: Boolean = false
}