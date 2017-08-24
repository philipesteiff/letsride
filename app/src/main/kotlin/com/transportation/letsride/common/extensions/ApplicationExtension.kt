package com.transportation.letsride.common.extensions

import android.app.Application
import com.transportation.letsride.App

fun Application.appComponent() = (this as App).appComponent
