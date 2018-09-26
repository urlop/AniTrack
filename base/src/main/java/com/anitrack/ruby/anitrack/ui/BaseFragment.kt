package com.anitrack.ruby.anitrack.ui

import androidx.fragment.app.Fragment
import com.hlab.fabrevealmenu.view.FABRevealMenu

class BaseFragment : Fragment() {

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
}