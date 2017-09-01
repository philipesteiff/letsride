package com.transportation.letsride.feature.map.fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.transportation.letsride.R
import com.transportation.letsride.common.di.Injectable
import com.transportation.letsride.common.util.plusAssign
import com.transportation.letsride.common.util.unsafeLazy
import com.transportation.letsride.feature.map.viewmodel.CustomMapViewModel
import com.transportation.letsride.feature.pickup.viewmodel.PickupViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class CustomMapFragment : SupportMapFragment(), LifecycleOwner, Injectable {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  private var lifecycleRegistry = LifecycleRegistry(this)

  private val disposables = CompositeDisposable()

  val sharedViewModel: PickupViewModel by unsafeLazy {
    ViewModelProviders.of(activity, viewModelFactory)
        .get(PickupViewModel::class.java)
  }

  val viewModel: CustomMapViewModel by unsafeLazy {
    ViewModelProviders.of(this, viewModelFactory)
        .get(CustomMapViewModel::class.java)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.currentMapPosition
        .observe(this, Observer { moveMap(it) })

    sharedViewModel.addressChange
        .observe(this, Observer {
          viewModel.addressChange(it)
        })


  }

  private fun moveMap(it: LatLng?) {
    getMapAsync { googleMap ->
      it?.let { googleMap.moveCamera(CameraUpdateFactory.newLatLng(it)) }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    disposables.clear()
  }

  override fun getLifecycle() = lifecycleRegistry

  @SuppressLint("MissingPermission")
  fun locationPermissionGranted() {
    getMapAsync { googleMap ->
      googleMap.apply {
        isMyLocationEnabled = true
        setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.midnight_map_style))
        listenMapEvents(googleMap)
      }
    }
  }

  private fun listenMapEvents(googleMap: GoogleMap) {
    viewModel.mapReady()

    disposables += googleMap.onMapDragged()
        .doOnNext { Timber.d("MapDragged: $it") }
        .subscribe(
            { latLng ->
              viewModel.mapDragged(latLng)
              sharedViewModel.mapDragged(latLng)
            },
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
