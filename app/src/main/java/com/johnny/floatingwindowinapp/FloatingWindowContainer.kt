package com.johnny.floatingwindowinapp

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.core.view.children


class FloatingWindowContainer : FrameLayout {

    private var _xDelta = 0
    private var _yDelta = 0

    var marginCalculation: MarginCalculation? = null

    var floatingWindow: View? = null

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView()
    }

    private fun initView() {
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupFloatingWindow() {
        floatingWindow = this.children.first()
        floatingWindow?.setOnTouchListener(OnTouchListener { view, event ->
            val eventX = event.rawX.toInt()
            val eventY = event.rawY.toInt()
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    val p = view.layoutParams as LayoutParams
                    _xDelta = eventX - p.leftMargin
                    _yDelta = eventY - p.topMargin
                }
                MotionEvent.ACTION_UP -> {
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                }
                MotionEvent.ACTION_POINTER_UP -> {
                }
                MotionEvent.ACTION_MOVE -> {
                    val container = view.parent as ViewGroup

                    val layoutParams =
                        view.layoutParams as LayoutParams
                    var newLeftMargin = eventX - _xDelta
                    var newTopMargin = eventY - _yDelta
                    if (floatingWindow != null) {
                        marginCalculation?.let { cal ->
                            val minLeft = cal.getMinLeftMargin(
                                container.width,
                                view.width
                            )
                            val minTop = cal.getMinTopMargin(
                                container.height,
                                view.height
                            )
                            newLeftMargin =
                                if (newLeftMargin <= minLeft) minLeft else newLeftMargin
                            newTopMargin =
                                if (newTopMargin <= minTop) minTop else newTopMargin
                            val maxLeft = cal.getMaxLeftMargin(
                                container.width,
                                view.width
                            )
                            val maxTop = cal.getMaxTopMargin(
                                container.height,
                                view.height
                            )
                            newLeftMargin =
                                if (newLeftMargin >= maxLeft) maxLeft else newLeftMargin
                            newTopMargin =
                                if (newTopMargin >= maxTop) maxTop else newTopMargin
                        }
                    }

                    layoutParams.leftMargin = newLeftMargin
                    layoutParams.topMargin = newTopMargin
                    view.layoutParams = layoutParams
                }
            }
            view.invalidate()
            return@OnTouchListener true
        })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (floatingWindow == null) {
            setupFloatingWindow()
            layoutTransition = LayoutTransition()
        }
    }

    fun setFloatingWindowMargin(
        leftMargin: Int,
        topMargin: Int,
        animated: Boolean = false,
        completeCallback: (() -> Unit)? = null
    ) {
        floatingWindow?.let { view ->
            val layoutParams =
                view.layoutParams as LayoutParams
            layoutParams.leftMargin = leftMargin
            layoutParams.topMargin = topMargin
            view.layoutParams = layoutParams
        }
        if (animated) {
            layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            layoutTransition.addTransitionListener(object : LayoutTransition.TransitionListener {
                override fun endTransition(
                    transition: LayoutTransition?, arg1: ViewGroup?,
                    arg2: View, arg3: Int
                ) {
                    transition?.disableTransitionType(LayoutTransition.CHANGING)
                    transition?.removeTransitionListener(this)
                    completeCallback?.invoke()
                }

                override fun startTransition(
                    transition: LayoutTransition?,
                    container: ViewGroup?, view: View, transitionType: Int
                ) {
                }
            })
        } else {
            completeCallback?.invoke()
        }
    }

    interface MarginCalculation {
        fun getMinLeftMargin(containerWidth: Int, floatingWindowWidth: Int): Int
        fun getMinTopMargin(containerHeight: Int, floatingWindowHeight: Int): Int
        fun getMaxLeftMargin(containerWidth: Int, floatingWindowWidth: Int): Int
        fun getMaxTopMargin(containerHeight: Int, floatingWindowHeight: Int): Int
    }

    class MarginCalculationToEdge :
        MarginCalculation {
        override fun getMinLeftMargin(containerWidth: Int, floatingWindowWidth: Int): Int {
            return 0
        }

        override fun getMinTopMargin(containerHeight: Int, floatingWindowHeight: Int): Int {
            return 0
        }

        override fun getMaxLeftMargin(containerWidth: Int, floatingWindowWidth: Int): Int {
            return containerWidth - floatingWindowWidth
        }

        override fun getMaxTopMargin(containerHeight: Int, floatingWindowHeight: Int): Int {
            return containerHeight - floatingWindowHeight
        }
    }
}