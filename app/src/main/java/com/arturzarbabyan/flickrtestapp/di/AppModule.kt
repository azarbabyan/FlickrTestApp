package com.arturzarbabyan.flickrtestapp.di

import android.content.Context
import androidx.room.Room
import com.arturzarbabyan.flickrtestapp.data.local.AppDatabase
import com.arturzarbabyan.flickrtestapp.data.local.PhotoDao
import com.arturzarbabyan.flickrtestapp.data.remote.FlickrApi
import com.arturzarbabyan.flickrtestapp.data.repository.PhotoRepository
import com.arturzarbabyan.flickrtestapp.domain.usecase.GetRecentPhotosUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import javax.inject.Singleton
import okhttp3.MediaType.Companion.toMediaType

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideFlickrApi(retrofit: Retrofit): FlickrApi {
        return retrofit.create(FlickrApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "photos-db").build()
    }

    @Provides
    fun providePhotoDao(database: AppDatabase): PhotoDao {
        return database.photoDao()
    }

    @Provides
    @Singleton
    fun providePhotoRepository(api: FlickrApi, photoDao: PhotoDao,@ApplicationContext context: Context): PhotoRepository {
        return PhotoRepository(api, photoDao,context)
    }

    @Provides
    @Singleton
    fun provideGetRecentPhotosUseCase(repository: PhotoRepository): GetRecentPhotosUseCase {
        return GetRecentPhotosUseCase(repository)
    }
}