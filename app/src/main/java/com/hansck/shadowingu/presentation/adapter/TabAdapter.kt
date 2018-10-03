package com.hansck.shadowingu.presentation.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Hans CK on 13-Jun-18.
 */
class TabAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

	private val fragments = ArrayList<Fragment>()
	private val titles = ArrayList<String>()

	override fun getItem(position: Int): Fragment {
		return fragments.get(position)
	}

	override fun getCount(): Int {
		return fragments.size
	}

	override fun getPageTitle(position: Int): CharSequence {
		return titles[position]
	}

	fun addFragment(fragment: Fragment, title: String) {
		fragments.add(fragment)
		titles.add(title)
	}
}