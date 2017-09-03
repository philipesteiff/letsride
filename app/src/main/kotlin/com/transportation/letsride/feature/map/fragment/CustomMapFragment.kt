package com.transportation.letsride.feature.map.fragment

import android.content.Context
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.transportation.letsride.R
import com.transportation.letsride.common.util.plusAssign
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class CustomMapFragment : SupportMapFragment() {

  interface MapListener {
    fun mapDragged(newLocation: LatLng)
  }

  var listener: MapListener? = null

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    when (context) {
      is MapListener -> listener = context
      else -> throw RuntimeException("$context must implement CustomMapFragment.MapListener")
    }
  }

  private var disposables = CompositeDisposable()

  override fun onActivityCreated(bundle: Bundle?) {
    super.onActivityCreated(bundle)
    initMap()
  }

  private fun initMap() {
    Single.fromCallable { loadMapStyleOptions() }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { mapStyle ->
          getMapAsync {
            it.apply {
              setMapStyle(mapStyle)

              isIndoorEnabled = false
              uiSettings?.apply {
                isCompassEnabled = false
                isRotateGesturesEnabled = false
                isMyLocationButtonEnabled = false
                isMapToolbarEnabled = false
                isRotateGesturesEnabled = false
              }

              it.onMapDragged()
                  .subscribe({ listener?.mapDragged(it) })
                  .also { disposables += it }

            }
          }
        }
  }

  private fun loadMapStyleOptions() = MapStyleOptions
      .loadRawResourceStyle(context, R.raw.map_style)

  fun moveMapToLocation(newLocation: LatLng?) {
    getMapAsync {
      val cameraPosition = CameraPosition.Builder()
          .target(newLocation)
          .zoom(DEFAULT_ZOOM)
          .build()
      it.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
  }

  private fun GoogleMap.onMapDragged(): Observable<LatLng> {
    val onCameraMoveStarted = Observable.create<Int> { emitter ->
      setOnCameraMoveStartedListener {
        Timber.d("setOnCameraMoveStartedListener")
        emitter.onNext(it)
      }

      emitter.setCancellable {
        Timber.d("Removing OnCameraMoveStartedListener")
        setOnCameraMoveStartedListener(null)
      }
    }

    val onCameraIdle = Observable.create<LatLng> { emitter ->
      setOnCameraIdleListener {
        emitter.onNext(cameraPosition.target)
      }

      emitter.setCancellable {
        Timber.d("Removing OnCameraIdleListener")
        setOnCameraIdleListener(null)
      }
    }

    return onCameraMoveStarted
        .filter { it == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE }
        .flatMap { onCameraIdle.take(1) }
        .doOnNext { Timber.d("Dragging stop") }
  }

  companion object {
    val TAG: String = CustomMapFragment::class.java.canonicalName
    val DEFAULT_ZOOM = 17f

    fun newInstance(): CustomMapFragment {
      return CustomMapFragment().apply {
        val extra = Bundle()
        arguments = extra
      }
    }
  }

}
