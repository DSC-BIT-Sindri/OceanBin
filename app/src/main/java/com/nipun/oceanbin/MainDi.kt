package com.nipun.oceanbin

import android.content.Context
import com.nipun.oceanbin.core.PreferenceManager
import com.nipun.oceanbin.core.firebase.FireStoreManager
import com.nipun.oceanbin.feature_oceanbin.feature_home.data.LocationRepositoryImpl
import com.nipun.oceanbin.feature_oceanbin.feature_home.data.remote.WeatherApi
import com.nipun.oceanbin.feature_oceanbin.feature_home.local.LocationRepository
import com.nipun.oceanbin.feature_oceanbin.feature_map.data.MapRepositoryImpl
import com.nipun.oceanbin.feature_oceanbin.feature_map.local.MapRepository
import com.nipun.oceanbin.feature_oceanbin.feature_profile.repository.ProfileRepository
import com.nipun.oceanbin.feature_oceanbin.feature_search.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
 * Dagger hilt module
 * We define all our injectable object here
 */
@Module
@InstallIn(SingletonComponent::class)
object MainDi {

    /*
     * This function provide object of Preference Manager class
     */
    @Provides
    @Singleton
    fun providePrefManager(@ApplicationContext context: Context): PreferenceManager =
        PreferenceManager(context)

    @Provides
    @Singleton
    fun provideLocationRepo(
        @ApplicationContext context: Context,
        weatherApi: WeatherApi
    ): LocationRepository = LocationRepositoryImpl(context,weatherApi)

    @Singleton
    @Provides
    fun provideMapRepo(
        @ApplicationContext context: Context
    ) : MapRepository = MapRepositoryImpl(context)

    @Singleton
    @Provides
    fun provideSearchRepo(
        @ApplicationContext context: Context
    ) : SearchRepository = SearchRepository(context)

    @Singleton
    @Provides
    fun provideFireStoreManager(
        @ApplicationContext context: Context
    ) : FireStoreManager = FireStoreManager(context)

    @Singleton
    @Provides
    fun provideProfileRepository(
        @ApplicationContext context: Context,
        preferenceManager: PreferenceManager
    ) : ProfileRepository = ProfileRepository(context,preferenceManager)

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(WeatherApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}