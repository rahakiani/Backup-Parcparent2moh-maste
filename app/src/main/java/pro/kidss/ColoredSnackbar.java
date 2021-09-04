package pro.kidss;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class ColoredSnackbar {

    private static final int BLUE = 0xff0465ff;
    private static final int ORANGE = 0xffffc071;
    private static final int RED = 0xfff34234;
    private static final int GREEN = 0xff49af49;


    private static View getSnackbarLayout(Snackbar snackbar) {
        if (snackbar == null) {
            return null;
        } else {
            return snackbar.getView();
        }
    }

    private static Snackbar colorSnackbar(Snackbar snackbar, int color) {
        View sbview = ColoredSnackbar.getSnackbarLayout( snackbar );
        if (sbview != null) {
            sbview.setBackgroundColor( color );
        }
        return snackbar;
    }


    public static Snackbar info(Snackbar snackbar) {
        return colorSnackbar( snackbar, BLUE );
    }

    public static Snackbar warning(Snackbar snackbar) {
        return colorSnackbar( snackbar, ORANGE );
    }

    public static Snackbar success(Snackbar snackbar) {
        return colorSnackbar( snackbar, GREEN );
    }

    public static Snackbar error(Snackbar snackbar) {
        return colorSnackbar( snackbar, RED );
    }
}