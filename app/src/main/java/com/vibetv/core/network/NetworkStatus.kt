package com.vibetv.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.vibetv.common.utils.isAtLeastNougat

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

enum class NetworkStatus { Available, Unavailable }

class NoNetworkException : Throwable()

fun Context.networkStatus(): Flow<NetworkStatus> {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return callbackFlow {
        val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities,
            ) {
                val hasInternet = networkCapabilities.hasCapability(
                    if (isAtLeastNougat()) {
                        NetworkCapabilities.NET_CAPABILITY_VALIDATED
                    } else {
                        NetworkCapabilities.NET_CAPABILITY_INTERNET
                    },
                )
                trySend(if (hasInternet) NetworkStatus.Available else NetworkStatus.Unavailable)
            }

            override fun onUnavailable() {
                trySend(NetworkStatus.Unavailable)
            }

            override fun onLost(network: Network) {
                trySend(NetworkStatus.Unavailable)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        if (isAtLeastNougat()) {
            connectivityManager.registerDefaultNetworkCallback(networkStatusCallback)
        } else {
            connectivityManager.registerNetworkCallback(request, networkStatusCallback)
        }

        val hasInternetInitially = if (isAtLeastNougat()) {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) == true
        } else {
            @Suppress("DEPRECATION")
            connectivityManager.activeNetworkInfo?.let {
                it.isConnected && it.type == ConnectivityManager.TYPE_MOBILE || it.type == ConnectivityManager.TYPE_WIFI
            } ?: false
        }
        trySend(if (hasInternetInitially) NetworkStatus.Available else NetworkStatus.Unavailable)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkStatusCallback)
        }
    }
        .distinctUntilChanged()
}
