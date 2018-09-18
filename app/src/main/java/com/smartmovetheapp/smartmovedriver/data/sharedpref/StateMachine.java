package com.smartmovetheapp.smartmovedriver.data.sharedpref;

import android.support.annotation.IntDef;

/**
 * Class to handle the state of the application.
 */
public class StateMachine {

    private static final String STATE_KEY = "app_state";

    @ApplicationState
    public static int getCurrentState() {
        return SharedPrefs.getInstance().getInt(STATE_KEY, State.NO_LOGIN);
    }

    public static void changeStateTo(@ApplicationState int state) {
        SharedPrefs.getInstance().addInt(STATE_KEY, state);
    }

    public static boolean canLogIntoTheApp() {
        return getCurrentState() == State.LOGGED_IN;
    }

    public interface State {

        /*state where no user is logged in*/
        int NO_LOGIN = 1;

        /*state where user successfully logged in to app*/
        int LOGGED_IN = 2;
    }

    @IntDef({State.NO_LOGIN, State.LOGGED_IN})
    public @interface ApplicationState {}
}
