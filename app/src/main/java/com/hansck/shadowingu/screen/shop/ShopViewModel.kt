package com.hansck.shadowingu.screen.shop

import android.content.Context
import com.hansck.shadowingu.model.Avatar
import com.hansck.shadowingu.util.DataManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class ShopViewModel(var context: Context?) {

    var avatars: ArrayList<Avatar> = ArrayList()

    fun setAvatars(){
        avatars = DataManager.instance.avatars
    }
}