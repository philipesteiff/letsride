package com.transportation.letsride.feature.map.ui.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import com.transportation.letsride.R
import com.transportation.letsride.common.extensions.onLayoutReady

class PinView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attributeSet, defStyleAttr) {

  init {
    // TODO Change vector to an image without padding.
    setImageResource(R.drawable.ic_pin) /* ༼ つ ˵ ╥ ͟ʖ ╥ ˵༽つ This ic_pin have an annoying padding. */
  }

  fun centerOn(pickupMapContainer: ViewGroup) {
    onLayoutReady {
      val marginBottom = (pickupMapContainer.height / 2)

      val parentConstraintLayout = (parent as ConstraintLayout)
      val applyConstraintSet = ConstraintSet().apply { clone(parentConstraintLayout) }
      applyConstraintSet.setMargin(this@PinView.id, ConstraintSet.BOTTOM, marginBottom)
      applyConstraintSet.applyTo(parentConstraintLayout)

    }
  }


}
