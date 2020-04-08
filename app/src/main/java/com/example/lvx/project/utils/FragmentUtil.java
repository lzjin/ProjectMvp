package com.example.lvx.project.utils;


import com.example.lvx.project.base.BaseMvpActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 *  Fragment  切换
 */
public class FragmentUtil {
    public static void setChildContentFragment(int id, Fragment context, Fragment show, Fragment hide) {
        if (show == hide) {
            return;
        }
//		if (first_fragment != show) {
//			first_fragment = show;
//		}
        FragmentManager fragmentManager = context.getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (show.isAdded()) {
            fragmentTransaction.show(show);
            fragmentTransaction.hide(hide);
        } else {
            fragmentTransaction.hide(hide);
            fragmentTransaction.add(id, show);
        }
        fragmentTransaction.commit();
    }

    public static void addChildFragment(int id, Fragment context, Fragment fragment) {
        FragmentManager fragmentManager = context.getChildFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    public static void setContentFragment(int id, BaseMvpActivity context, Fragment show, Fragment hide) {
        if (show == hide) {
            return;
        }
//		if (first_fragment != show) {
//			first_fragment = show;
//		}
        FragmentManager fragmentManager = context.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//		fragmentTransaction.setCustomAnimations(R.anim.alpha_anim_show, R.anim.alpha_anim_hide);
        if (show.isAdded()) {
            fragmentTransaction.show(show);
            fragmentTransaction.hide(hide);
        } else {
            fragmentTransaction.hide(hide);
            fragmentTransaction.add(id, show);
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    public static void addFragment(int id, BaseMvpActivity context, Fragment fragment) {
        if(!fragment.isAdded()){
            FragmentManager fragmentManager = context.getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(id, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    public static void replaceFragment(int id, BaseMvpActivity context, Fragment fragment) {
        FragmentManager fragmentManager = context.getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit();
    }
}
