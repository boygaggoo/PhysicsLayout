package com.jawnnypoo.physicslayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Typical RelativeLayout with some physics added on. Call {@link #getPhysics()} to get the
 * physics component.
 */
public class PhysicsRelativeLayout extends RelativeLayout {

    private Physics physics;

    public PhysicsRelativeLayout(Context context) {
        super(context);
        init(null);
    }

    public PhysicsRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PhysicsRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(21)
    public PhysicsRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setWillNotDraw(false);
        physics = new Physics(this, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        physics.onSizeChanged(w, h);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        physics.onLayout(changed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        physics.onDraw(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return physics.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        return physics.onTouchEvent(event);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public Physics getPhysics() {
        return physics;
    }

    private static class LayoutParams extends RelativeLayout.LayoutParams implements PhysicsLayoutParams {

        PhysicsConfig config;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            config = PhysicsLayoutParamsProcessor.process(c, attrs);
        }

        @Override
        public PhysicsConfig getConfig() {
            return config;
        }
    }
}
