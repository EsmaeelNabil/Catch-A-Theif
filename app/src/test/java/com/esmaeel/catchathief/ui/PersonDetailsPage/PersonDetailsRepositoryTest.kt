package com.esmaeel.catchathief.ui.PersonDetailsPage

import com.esmaeel.catchathief.Utils.Contract
import com.esmaeel.catchathief.Utils.Status
import com.esmaeel.catchathief.data.models.PersonsImagesResponse
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PersonDetailsRepositoryTest {

    @Mock
    lateinit var repository: PersonDetailsRepository

    @Before
    fun setUp() {
    }

    @Test
    fun getPersonsImages_with_person_id_returns_data_with_success_status() {
        //Mock the data
        Mockito.`when`(repository.getPersonsImages(200))
            .thenReturn(
                flow {
                    emit(
                        Contract(
                            data = PersonsImagesResponse(id = 200),
                            message = "page must be greater than 0",
                            status = Status.SUCCESS
                        )
                    )
                }
            )

        var result: Contract<PersonsImagesResponse?>? = null
        val expectedResult = Contract(
            data = PersonsImagesResponse(id = 200),
            message = null,
            status = Status.SUCCESS
        )

        runBlocking {
            repository.getPersonsImages(200).collect {
                result = it
            }
        }

        Assert.assertEquals(expectedResult.data!!.id, result!!.data!!.id)

    }

    @Test
    fun getPersonsImages_with_person_id_equal_null_returns_no_data_with_error_status() {
        runBlocking {
            //Mock the data
            Mockito.`when`(repository.getPersonsImages(null))
                .thenReturn(
                    flow {
                        emit(
                            Contract(
                                data = null,
                                message = "The resource you requested could not be found.",
                                status = Status.ERROR
                            )
                        )
                    }
                )

            var result: Contract<PersonsImagesResponse?>? = null
            val expectedResult = Contract(
                data = null,
                message = "The resource you requested could not be found.",
                status = Status.ERROR
            )


            repository.getPersonsImages(null).collect {
                result = it
            }

            Assert.assertEquals(expectedResult.status, result!!.status)
        }

    }
}