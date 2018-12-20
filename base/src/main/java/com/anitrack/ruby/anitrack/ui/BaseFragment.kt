package com.anitrack.ruby.anitrack.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.hlab.fabrevealmenu.view.FABRevealMenu

open class BaseFragment : Fragment() {

    lateinit var fabMenu : FABRevealMenu

    /*public boolean onBackPressed() {
        if (fabMenu != null) {
            if (fabMenu.isShowing()) {
                fabMenu.closeMenu();
                return false;
            }
        }
        return true;
    }*/

    /**
     * Solves problem "when the fragment is detached and re-attached, it is not destroyed and a new identical observer instance will be added in onActivityCreated()".
     * From: https://medium.com/@BladeCoder/architecture-components-pitfalls-part-1-9300dd969808
     */
    fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>) {
        removeObserver(observer)
        observe(owner, observer)
    }
}