package com.nipun.oceanbin

import android.content.Context
import com.nipun.oceanbin.core.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainDi {

    @Provides
    @Singleton
    fun providePrefManager(@ApplicationContext context : Context) : PreferenceManager = PreferenceManager(context)
}