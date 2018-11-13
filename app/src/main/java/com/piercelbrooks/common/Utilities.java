package com.piercelbrooks.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;

import com.android.grafika.gles.EglCore;
import com.android.grafika.gles.WindowSurface;

import java.util.List;

public class Utilities {
    private static final String TAG = "PLB-Utilities";

    public static boolean enable(@Nullable View view) {
        if (view == null) {
            return false;
        }
        view.setClickable(true);
        view.setVisibility(View.VISIBLE);
        return true;
    }

    public static boolean disable(@Nullable View view) {
        if (view == null) {
            return false;
        }
        view.setClickable(false);
        view.setVisibility(View.INVISIBLE);
        return true;
    }

    public static boolean bringToFront(@Nullable View view) {
        if (view == null) {
            return false;
        }
        ViewParent parent = view.getParent();
        if (parent == null) {
            return false;
        }
        parent.bringChildToFront(view);
        parent.requestLayout();
        return true;
    }

    public static boolean resetParent(@Nullable View view, @Nullable View parent) {
        return resetParent(view, parent, false);
    }

    public static boolean resetParent(@Nullable View view, @Nullable View parent, boolean force) {
        if (view == null) {
            return false;
        }
        ViewParent temp = view.getParent();
        if (temp == null) {
            if (parent != null) {
                ((ViewGroup) parent).addView(view);
            }
        } else {
            if (force) {
                ((ViewGroup) temp).removeView(view);
                if (parent != null) {
                    ((ViewGroup) parent).addView(view);
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean clearSurface(@Nullable SurfaceTexture surfaceTexture, @Nullable Surface surface) {
        if (surfaceTexture == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!surfaceTexture.isReleased()) {
                return false;
            }
        }
        return clearSurface(surface);
    }

    public static boolean clearSurface(@Nullable SurfaceTexture surfaceTexture) {
        if (surfaceTexture == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!surfaceTexture.isReleased()) {
                return false;
            }
        }
        return clearSurface(new Surface(surfaceTexture));
    }

    public static boolean clearSurface(@Nullable Surface surface) {
        if (surface == null) {
            return false;
        }
        if (!surface.isValid()) {
            return false;
        }
        boolean success = true;
        try {
            EglCore eglCore = new EglCore();
            WindowSurface win = new WindowSurface(eglCore, surface, true);
            win.makeCurrent();
            GLES20.glClearColor(0, 0, 0, 0);
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            win.swapBuffers();
            win.release();
            eglCore.release();
        } catch (Exception exception) {
            exception.printStackTrace();
            success = false;
        }
        return success;
    }

    public static void throwInstanceException(@NonNull String tag, int limit) throws InstanceException {
        throw new InstanceException(tag, limit);
    }

    public static void throwSingletonException(@NonNull String tag) {
        throw new SingletonException(tag);
    }

    public static String getIdentifier(@Nullable Object object) {
        if (object == null) {
            return "<NULL>";
        }
        return "<\"" + object.toString() + "\" (#" + Integer.toHexString(object.hashCode()) + ")>";
    }

    public static String getHax(int number) {
        return Integer.toHexString(number);
    }

    public static boolean closeKeyboard(Activity activity) {
        if (activity == null) {
            return false;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return false;
        }
        View focus = activity.getCurrentFocus();
        if (focus == null) {
            return false;
        }
        imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
        return true;

    }

    public static boolean openKeyboard(Activity activity) {
        if (activity == null) {
            return false;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return false;
        }
        imm.toggleSoftInput(0, 0);
        return true;
    }

    public static void add(List<String> list, List<String> other) {
        add(list, other, null);
    }

    public static void add(List<String> list, List<String> other, String prefix) {
        if ((list != null) && (other != null)) {
            if (prefix != null) {
                for (int i = 0; i != other.size(); ++i) {
                    list.add(prefix+other.get(i));
                }
            } else {
                for (int i = 0; i != other.size(); ++i) {
                    list.add(other.get(i));
                }
            }
        }
    }

    public static int count(String subject, char object) {
        if (subject == null) {
            return 0;
        }
        int tally = 0;
        for (int i = 0; i != subject.length(); ++i) {
            if (subject.charAt(i) == object) {
                ++tally;
            }
        }
        return tally;
    }
}
