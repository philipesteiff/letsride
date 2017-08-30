package com.transportation.letsride.feature.map.fragment

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.util.plusAssign
import com.transportation.letsride.common.util.unsafeLazy
import com.transportation.letsride.feature.location.PickupViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class CustomMapFragment : SupportMapFragment(), LifecycleOwner {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  private val disposables = CompositeDisposable()

  val viewModel: PickupViewModel by unsafeLazy {
    ViewModelProviders.of(this, viewModelFactory)
        .get(PickupViewModel::class.java)
  }

  private var mLifecycleRegistry = LifecycleRegistry(this)

  override fun getLifecycle(): LifecycleRegistry {
    return mLifecycleRegistry
  }

  override fun onDestroy() {
    super.onDestroy()
    disposables.clear()
  }

  fun locationPermissionGranted() {
    getMapAsync { googleMap -> listenMapEvents(googleMap) }
  }

  private fun listenMapEvents(googleMap: GoogleMap) {

    viewModel.onMapReady()

    disposables += googleMap.onMapDragged().subscribe(
        { latLng -> viewModel.onMapDragged(latLng) },
        { Timber.e(it) }
    )
  }

  private fun GoogleMap.onMapDragged(): Flowable<LatLng> {
    val onCameraMoveStarted = Flowable.create<Int>(
        { emitter ->
          setOnCameraMoveStartedListener {
            emitter.onNext(it)
          }

          emitter.setCancellable {
            Timber.d("Removing OnCameraMoveStartedListener")
            setOnCameraMoveStartedListener(null)
          }
        },
        BackpressureStrategy.BUFFER
    )

    val onCameraIdle = Flowable.create<LatLng>(
        { emitter ->
          setOnCameraIdleListener {
            emitter.onNext(cameraPosition.target)
          }

          emitter.setCancellable {
            Timber.d("Removing OnCameraIdleListener")
            setOnCameraIdleListener(null)
          }
        },
        BackpressureStrategy.BUFFER
    )

    return onCameraMoveStarted
        .filter { it == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE }
        .flatMap { onCameraIdle.take(1) }
        .doOnNext { Timber.d("Dragging stop") }
  }

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
