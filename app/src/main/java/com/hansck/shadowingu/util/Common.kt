package com.hansck.shadowingu.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.hansck.shadowingu.R
import com.hansck.shadowingu.util.ConnectivityReceiver
import java.text.NumberFormat
import java.util.*

/**
 * Created by Hans CK on 1-Nov-17.
 */
class Common private constructor() {

    companion object {
        val instance = Common()
    }

    //region Public methods
    fun showToast(activity: Activity, text: String, duration: Int) {
        try {
            val snackbar = Snackbar.make(activity.currentFocus!!, text, duration)
            val tv = snackbar.view.findViewById<View>(R.id.snackbar_text) as TextView
            tv.maxLines = 5
            snackbar.show()
        } catch (e: Exception) {
//            val view = activity.getWindow().getDecorView().findViewById<View>(R.id.content)
//            if (view != null) {
//                Snackbar.make(view, text, duration).show()
//            } else {
////                Crashlytics.logException(e)
//            }
        }
    }

    fun showAlertToast(activity: Activity, text: String) {
        showToast(activity, text, Snackbar.LENGTH_LONG)
    }

    fun showSuccessToast(activity: Activity, text: String) {
        showToast(activity, text, Snackbar.LENGTH_SHORT)
    }

    fun showErrorMessage(activity: Activity) {
        if (!ConnectivityReceiver.isConnected) {
//            showAlertToast(activity, activity.getString(R.string.no_internet_alert))
        } else {
//            showAlertToast(activity, activity.getString(R.string.failed_request_general))
        }
    }

    fun formatPrice(amount: Float): String {
        return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(amount)
    }

    fun hideSoftKeyboard(activity: Activity) {
        try {
            val i = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (activity.currentFocus != null) {
                i.hideSoftInputFromWindow(activity.currentFocus?.windowToken, InputMethodManager
                        .HIDE_NOT_ALWAYS)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun verifyPermission(grantResults: IntArray): Boolean {
        if (grantResults.size < 1) {
            return false
        }
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun getQueryString(root: String, map: Map<String, String>?): String {
        var query = root
        if (map != null) {
            query += "?"
            val treemap = TreeMap(map)
            for ((key, value) in treemap) {
                query += "$key=$value&"
            }
            query = query.substring(0, query.length - 1)
        }
        return query
    }
    //endregion

    //region API Calls
    //	public void updateNotificationBadge(final Context context) {
    //		if (UserManager.getInstance().getActiveUser() != null) {
    //			if (UserManager.getInstance().isTokenExpired()) {
    //				UserManager.getInstance().relogin((Activity) context, new APICall() {
    //					@Override
    //					public void run() {
    //						updateNotificationBadge(context);
    //					}
    //				});
    //			} else {
    //				NotificationAPIRequest.getInstance().getUnreadNotification(new CallbackResponse<NotificationUnreadResponse>() {
    //					@Override
    //					public void run() {
    //						Response<NotificationUnreadResponse> response = getResponse();
    //						if (response.isSuccessful()) {
    //							NotificationUnreadResponse unread = response.body();
    //							setNotificationBadge(context, unread.getValue().getUnread());
    //						}
    //					}
    //				}, new CallbackError() {
    //					@Override
    //					public void run() {
    //						//Actually do nothing
    //					}
    //				});
    //			}
    //		}
    //	}
    //endregion
}