package com.smartmovetheapp.smartmovedriver.util;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

/**
 * Helper class to provide common functionality to {@link Fragment}.
 */
public class FragmentHelper {

    private static FragmentTransaction fragmentTransaction;

    /**
     * Method to add a fragment to activity and also to add it to back stack
     * @param fragment fragment that is to be added to the activity
     * @param fragmentManager the fragment manager to be used
     * @param fragmentContainer the view that is to be filled with the fragment
     * @param bundle the data to pass to the fragment
     */
    public static void addFragment(@NonNull Fragment fragment, @NonNull FragmentManager fragmentManager, @NonNull @IdRes int fragmentContainer, @Nullable Bundle bundle) {
        fragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragmentContainer, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    /**
     * Method to add a fragment to activity and also to add it to back stack
     * @param fragment fragment that is to be added to the activity
     * @param fragmentManager the fragment manager to be used
     * @param fragmentContainer the view that is to be filled with the fragment
     * @param bundle the data to pass to the fragment
     */
    public static void addFragment(@NonNull Fragment fragment, @NonNull FragmentManager fragmentManager, @NonNull @IdRes int fragmentContainer, @Nullable Bundle bundle, View sharedView, String sharedName) {
        fragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragmentContainer, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.addSharedElement(sharedView, sharedName);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    /**
     * Method to replace a fragment to activity. This method doesn't add the fragment to back stack.
     * @param fragment fragment that is to be added to the activity
     * @param fragmentManager the fragment manager to be used
     * @param fragmentContainer the view that is to be filled with the fragment
     * @param bundle the data to pass to the fragment
     */
    public static void replaceFragment(@NonNull Fragment fragment, @NonNull FragmentManager fragmentManager, @IdRes int fragmentContainer, @Nullable Bundle bundle) {
        fragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    /**
     * Method to pop all the fragments from the back stack
     * @param fragmentManager the fragment manager to be used
     */
    public static void popAllFragment(FragmentManager fragmentManager) {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void popCurrentFragment(FragmentManager fragmentManager) {
        fragmentManager.popBackStack();
    }

    /**
     * Method to pop all the fragments from the back stack
     * @param fragmentManager the fragment manager to be used
     */
    public static void popAllFragment(String name, FragmentManager fragmentManager) {
        fragmentManager.popBackStack(name, 0);
    }
}