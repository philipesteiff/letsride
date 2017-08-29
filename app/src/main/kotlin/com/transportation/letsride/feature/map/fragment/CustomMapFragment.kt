package com.transportation.letsride.feature.map.fragment

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.squareup.leakcanary.RefWatcher
import com.transportation.letsride.App
import com.transportation.letsride.common.util.Signal
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

interface MapListener {
  fun onMapReady(): Observable<Signal>
}

class CustomMapFragment : SupportMapFragment(), MapListener {

  val mapReady = BehaviorSubject.create<Signal>()
  val onDragStarted = PublishSubject.create<Signal>()
  val onDragFinished = PublishSubject.create<LatLng>()

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    getMapAsync { mapReady.onNext(Signal.INSTANCE) }
  }

  override fun onMapReady(): Observable<Signal> = mapReady

  companion object {
    val TAG: String = CustomMapFragment::class.java.canonicalName

    fun newInstance(): CustomMapFragment {
      return CustomMapFragment().apply {
        val extra = Bundle()
        arguments = extra
      }
    }
  }

}
