package ru.debian17.cicerone.di.module

import dagger.Module
import dagger.Provides
import ru.debian17.cicerone.navigation.LocalCiceroneHolder
import javax.inject.Singleton

@Module
class LocalNavigationModule {

    @Singleton
    @Provides
    fun provideLocalNavigationHolder(): LocalCiceroneHolder {
        return LocalCiceroneHolder()
    }

}