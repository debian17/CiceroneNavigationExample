package ru.debian17.cicerone.navigation

import androidx.collection.ArrayMap
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

object LocalCiceroneHolder {

    private val containers = ArrayMap<String, Cicerone<Router>>()

    fun getCicerone(containerTag: String): Cicerone<Router> {
        if (!containers.containsKey(containerTag)) {
            containers[containerTag] = Cicerone.create()
        }
        return containers[containerTag]!!
    }

}