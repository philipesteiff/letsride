package com.transportation.letsride.common.ui.fragment

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class MapFragment : SupportMapFragment() {

  companion object {
    val TAG: String = MapFragment::class.java.canonicalName

    fun newInstance(): MapFragment {
      return MapFragment().apply {
        val extra = Bundle()
        arguments = extra
      }
    }
  }

  object Subscriptions {

    interface OnDrag {
      fun bindDragStarted(): Observable<Void>
      fun bindDragFinished(): Observable<LatLng>
    }

    interface OnMap {
      fun bindMapReady(): Observable<Void>
    }
  }

  val mapReady = BehaviorSubject.create<Void>()
  val onDragStarted = PublishSubject.create<Void>()
  val onDragFinished = PublishSubject.create<LatLng>()

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }


}

class MapFragmentViewModel {

}
