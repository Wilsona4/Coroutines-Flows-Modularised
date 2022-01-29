package prieto.fernando.spacex.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import prieto.fernando.domain.model.CompanyInfoDomainModel
import prieto.fernando.domain.usecase.GetCompanyInfo
import prieto.fernando.spacex.presentation.screens.dashboard.CompanyInfoUiModel
import prieto.fernando.spacex.presentation.vm.mapper.CompanyInfoDomainToUiModelMapper

@ExperimentalCoroutinesApi
class DashboardViewModelTest {
    private lateinit var cut: DashboardViewModel

    @MockK
    lateinit var getCompanyInfo: GetCompanyInfo

    @MockK
    lateinit var companyInfoMapper: CompanyInfoDomainToUiModelMapper

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        cut = DashboardViewModel(getCompanyInfo, companyInfoMapper)
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `When companyInfo Then companyInfoUiModelRetrieved with expected result`() {
        // Given
        val companyInfoDomainModel = CompanyInfoDomainModel(
            "name",
            "founder",
            "foundedYear",
            "employees",
            1,
            23
        )
        val expected = CompanyInfoUiModel(
            "name",
            "founder",
            "founded",
            "employees",
            1,
            23
        )

        coEvery { getCompanyInfo.execute() } returns flow {
            emit(companyInfoDomainModel)
        }
        every { companyInfoMapper.toUiModel(companyInfoDomainModel) } returns expected

        // When
        cut.companyInfo()
        val actual = cut.viewState.value.companyInfoUiModel

        // Then
        coVerify(exactly = 2) { getCompanyInfo.execute() }

        assertEquals(expected, actual)
    }

    @Test
    fun `Given Error When companyInfo Then expected error state`() {
        runBlockingTest {
            // Given
            val expectedErrorState = true
            coEvery { getCompanyInfo.execute() } returns flow {
                emit(throw Exception("Network Exception"))
            }

            // When
            cut.companyInfo()
            val actual = cut.viewState.value.isError

            // Then
            coVerify(exactly = 2) { getCompanyInfo }
            assertEquals(expectedErrorState, actual)
        }
    }
}
