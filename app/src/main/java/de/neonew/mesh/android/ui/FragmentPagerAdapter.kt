package de.neonew.mesh.android.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class FragmentPagerAdapter(fragmentManager: FragmentManager, val pages: List<FragmentPage>) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment? {
        return pages[position].fragmentCreator.invoke()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pages[position].title
    }

    override fun getCount(): Int = pages.size
}

data class FragmentPage(val title: String, val fragmentCreator: () -> Fragment)
