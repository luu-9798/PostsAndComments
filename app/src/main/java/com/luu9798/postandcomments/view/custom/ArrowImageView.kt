package com.luu9798.postandcomments.view.custom

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.luu9798.postandcomments.R

class ArrowImageView(context: Context, attrs: AttributeSet?): AppCompatImageView(context, attrs) {
    private var isUp: Boolean = false
    private var animator: ObjectAnimator? = null

    init {
        setImageResource(R.drawable.ic_arrow_down_24)
        setOnClickListener { toggleArrow() }
    }

    private fun toggleArrow() {
        isUp = !isUp
        if (animator != null) {
            animator?.cancel()
        }
        val startAngle = if (isUp) 0f else 180f
        val endAngle = if (isUp) 180f else 0f
        animator = ObjectAnimator.ofFloat(this, View.ROTATION, startAngle, endAngle)
        animator?.duration = 300
        animator?.start()
    }
}
