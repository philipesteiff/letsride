package com.transportation.letsride.feature.pickupdropoff.viewmodel

import android.os.Parcel
import android.os.Parcelable
import com.transportation.letsride.data.model.PinPoint

data class PickupDropOffViewModelState(
    val pickupAddress: PinPoint?,
    val dropOffAddress: PinPoint?
) : Parcelable {

  constructor(parcel: Parcel) : this(
      parcel.readParcelable(PinPoint::class.java.classLoader),
      parcel.readParcelable(PinPoint::class.java.classLoader)
  )

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeParcelable(pickupAddress, flags)
    parcel.writeParcelable(dropOffAddress, flags)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object {

    const val KEY_STATE = "PickupDropOffViewModelState"

    @JvmField
    val CREATOR: Parcelable.Creator<PickupDropOffViewModelState> = object : Parcelable.Creator<PickupDropOffViewModelState> {
      override fun createFromParcel(parcel: Parcel): PickupDropOffViewModelState {
        return PickupDropOffViewModelState(parcel)
      }

      override fun newArray(size: Int): Array<PickupDropOffViewModelState?> {
        return arrayOfNulls(size)
      }
    }


  }
}
