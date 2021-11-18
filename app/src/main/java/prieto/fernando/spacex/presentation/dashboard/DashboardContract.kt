package prieto.fernando.spacex.presentation.dashboard

import prieto.fernando.core.presentation.ViewEvent
import prieto.fernando.core.presentation.ViewSideEffect
import prieto.fernando.core.presentation.ViewState


class DashboardContract {
    sealed class Event : ViewEvent

    data class State(
        val companyInfo: CompanyInfo?
    ) : ViewState

    sealed class Effect : ViewSideEffect
}