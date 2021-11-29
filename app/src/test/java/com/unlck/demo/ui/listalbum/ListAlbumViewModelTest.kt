package com.unlck.demo.ui.listalbum

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.unlck.domain.model.Album
import com.unlck.domain.repository.AlbumsRepository
import com.unlck.domain.usecases.GetListAlbumsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import com.unlck.domain.util.Result

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ListAlbumViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var listViewModel: ListAlbumViewModel

    @Mock
    private lateinit var observer: Observer<Result<List<Album>>>

    @Mock
    private lateinit var repository: AlbumsRepository

    @InjectMocks
    lateinit var getListAlbumsUseCase: GetListAlbumsUseCase

    @Before
    fun setUp() {
        listViewModel = ListAlbumViewModel(getListAlbumsUseCase)
        listViewModel.albumsResult.observeForever(observer)
    }

    @Test
    fun `Test ViewModel Loading and success`() {
        val listAlbums = FakeData.albums
        `when`(getListAlbumsUseCase.invoke(true)).thenReturn(
            flow {
                emit(Result.Success(listAlbums))
            }
        )

        listViewModel.refresh(true)

        verify(observer).onChanged(Result.Loading)
        verify(observer).onChanged(Result.Success(listAlbums))
    }


    @Test
    fun `Test ViewModel Loading and error`() {
        `when`(getListAlbumsUseCase.invoke(true)).thenReturn(
            flow {
                emit(Result.Failure())
            }
        )

        listViewModel.refresh(true)

        verify(observer).onChanged(Result.Loading)
        verify(observer).onChanged(Result.Failure())
    }
}