package com.hansck.shadowingu.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView

/**
 * Created by Hans CK on 07-Mar-18.
 */
class ListDivider(context: Context, drawableId: Int) : RecyclerView.ItemDecoration() {

	private var mDivider: Drawable = ContextCompat.getDrawable(context, drawableId)!!

	override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
		val left = parent.paddingLeft
		val right = parent.width - parent.paddingRight

		val childCount = parent.childCount
		for (i in 0 until childCount) {
			val child = parent.getChildAt(i)
			val params = child.layoutParams as RecyclerView.LayoutParams
			val top = child.bottom + params.bottomMargin
			val bottom = top + mDivider.intrinsicHeight
			mDivider.setBounds(left, top, right, bottom)
			mDivider.draw(c)
		}
	}
}