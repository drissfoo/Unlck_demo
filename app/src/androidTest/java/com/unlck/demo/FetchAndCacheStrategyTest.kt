package com.unlck.demo

import com.unlck.data.repository.AlbumsRepositoryImpl
import com.unlck.data.source.local.dao.AlbumDao
import com.unlck.data.source.local.db.AppDatabase
import com.unlck.data.source.remote.api.ApiService
import com.unlck.demo.di.ControllerModule
import com.unlck.domain.usecases.GetListAlbumsUseCase
import com.unlck.domain.util.RefreshController
import dagger.Module
import dagger.Provides
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject
import javax.inject.Singleton

@HiltAndroidTest
class FetchAndCacheStrategyTest {
    companion object {
        const val TEST_FETCH_RATE: Long = 3000
    }

    private lateinit var forceListAlbumsUseCase: GetListAlbumsUseCase

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var listAlbumsUseCase: GetListAlbumsUseCase

    @Inject
    lateinit var refreshController: RefreshController

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var appDatabase: AppDatabase

    lateinit var albumDao: AlbumDao


    @Before
    fun init() {
        hiltRule.inject()
        albumDao = mock(AlbumDao::class.java)
        val repository = AlbumsRepositoryImpl(albumDao, apiService, refreshController)
        forceListAlbumsUseCase = GetListAlbumsUseCase(repository)
    }

    @Test
    fun testCallApiStrategy() {
        runBlocking {
            // First time refresh controller should request getting data from remote api
            assertEquals(true, refreshController.isExpired())
            // Fetch and cache
            listAlbumsUseCase(false).onStart { }.catch { }.collect { }
            // We just fetched from remote, there's no need to fetch again
            assertEquals(false, refreshController.isExpired())
            // Wait more than the fetch rate
            delay(TEST_FETCH_RATE + 1000)
            // Refresh controller should request getting data from remote api
            assertEquals(true, refreshController.isExpired())
        }
    }

    @Test
    fun testCallApiStrategyForceRefresh() {
        runBlocking {
            // First time refresh controller should request getting data from remote api
            assertEquals(true, refreshController.isExpired())
            // Fetch force refresh cache
            forceListAlbumsUseCase(true).onStart { }.catch { }.collect { }
            // We just fetched from remote, verify we replaced our entities
            verify(albumDao).deleteAll()
            assertEquals(false, refreshController.isExpired())
            verify(albumDao).getListAlbums()
        }
    }
}


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ControllerModule::class]
)
object TestControllerModule {

    @Provides
    @Singleton
    fun provideRefreshController(): RefreshController =
        RefreshController(FetchAndCacheStrategyTest.TEST_FETCH_RATE)
}