package com.transportation.letsride.feature.pickupdropoff.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.transportation.letsride.R
import com.transportation.letsride.common.extensions.getColorCompat
import com.transportation.letsride.data.model.PinPoint
import kotlinx.android.synthetic.main.view_address.view.textAddressInput
import kotlinx.android.synthetic.main.view_address.view.textAddressLabel

class AddressView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

  private val PICKUP_ADDRESS_TYPE = 0

  init {
    inflate(context, R.layout.view_address, this)

    context.obtainStyledAttributes(attributeSet, R.styleable.AddressView, 0, 0).apply {
      getInteger(R.styleable.AddressView_addressType, PICKUP_ADDRESS_TYPE)
          .let {
            setLayoutType(it)
            setTagAddressInput(it)
          }
      getString(R.styleable.AddressView_addressHint).let { setAddressHint(it) }
    }.run { recycle() }
  }

  fun applyAddress(pinPoint: PinPoint?) {
    pinPoint?.apply {
      setAddressText(address)
    } ?: setAddressNotFound()
  }

  private fun setAddressNotFound() {
    setAddressText("PinPoint not found")
  }

  private fun setLayoutType(type: Int) {
    if (type == PICKUP_ADDRESS_TYPE) {
      "Pickup" to context.getColorCompat(R.color.colorAccent)
    } else {
      "Dropoff" to context.getColorCompat(R.color.colorPrimary)
    }.let { (labelText, labelColor) ->
      textAddressLabel.apply {
        text = labelText
        setTextColor(labelColor)
      }
    }
  }

  private fun setTagAddressInput(type: Int) {
    textAddressInput.tag = if (type == PICKUP_ADDRESS_TYPE) {
      PICKUP_ADDRESS_INPUT_TAG
    } else {
      DROPOFF_ADDRESS_INPUT_TAG
    }
  }

  private fun setAddressHint(textHint: String?) {
    textAddressInput.hint = textHint.orEmpty()
  }

  private fun setAddressText(address: String?) {
    textAddressInput.text = address.orEmpty()
  }

  companion object {
    const val PICKUP_ADDRESS_INPUT_TAG = "pickup_address_input_tag"
    const val DROPOFF_ADDRESS_INPUT_TAG = "dropoff_address_input_tag"
  }

}
