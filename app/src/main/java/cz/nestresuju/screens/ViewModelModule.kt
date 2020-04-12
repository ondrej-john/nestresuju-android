package cz.nestresuju.screens

import cz.nestresuju.model.entities.domain.program.ProgramId
import cz.nestresuju.router.RouterViewModel
import cz.nestresuju.screens.about.AboutAppViewModel
import cz.nestresuju.screens.diary.DiaryViewModel
import cz.nestresuju.screens.home.HomeViewModel
import cz.nestresuju.screens.library.LibraryViewModel
import cz.nestresuju.screens.login.LoginViewModel
import cz.nestresuju.screens.program.evaluation.ProgramEvaluationViewModel
import cz.nestresuju.screens.program.first.ProgramFirstOverviewViewModel
import cz.nestresuju.screens.program.first.ProgramFirstQuestionViewModel
import cz.nestresuju.screens.program.first.ProgramFirstSatisfiabilityViewModel
import cz.nestresuju.screens.program.first.ProgramFirstSummaryViewModel
import cz.nestresuju.screens.program.overview.ProgramViewModel
import cz.nestresuju.screens.program.second.ProgramSecondInstructionsViewModel
import cz.nestresuju.screens.program.second.ProgramSecondRelaxationViewModel
import cz.nestresuju.screens.program.third.ProgramThirdActivitiesSummaryIntroViewModel
import cz.nestresuju.screens.program.third.ProgramThirdActivitiesSummaryViewModel
import cz.nestresuju.screens.program.third.ProgramThirdActivitiesViewModel
import cz.nestresuju.screens.program.third.ProgramThirdTimetableViewModel
import cz.nestresuju.screens.tests.input.InputTestViewModel
import cz.nestresuju.screens.tests.screening.ScreeningTestViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for providing ViewModels.
 */

val viewModelModule = module {

    viewModel { RouterViewModel(oAuthManager = get(), sharedPreferencesInteractor = get()) }

    viewModel { LoginViewModel(authRepository = get()) }

    viewModel { InputTestViewModel(inputTestsRepository = get()) }

    viewModel { ScreeningTestViewModel(inputTestsRepository = get()) }

    viewModel { ProgramViewModel(programFirstRepository = get(), programSecondRepository = get(), programThirdRepository = get()) }

    viewModel { (programId: ProgramId) -> ProgramEvaluationViewModel(programId, programEvaluationRepository = get()) }

    viewModel { (progress: Int) -> ProgramFirstQuestionViewModel(progress, programRepository = get()) }

    viewModel { ProgramFirstSatisfiabilityViewModel(programRepository = get()) }

    viewModel { ProgramFirstOverviewViewModel(programRepository = get()) }

    viewModel { ProgramFirstSummaryViewModel(programRepository = get()) }

    viewModel { (progress: Int) -> ProgramSecondInstructionsViewModel(progress, programRepository = get()) }

    viewModel { ProgramSecondRelaxationViewModel(programRepository = get()) }

    viewModel { ProgramThirdTimetableViewModel(programRepository = get()) }

    viewModel { ProgramThirdActivitiesViewModel(programRepository = get()) }

    viewModel { ProgramThirdActivitiesSummaryIntroViewModel(programRepository = get()) }

    viewModel { ProgramThirdActivitiesSummaryViewModel(programRepository = get()) }

    viewModel { AboutAppViewModel() }

    viewModel { DiaryViewModel(androidContext(), diaryRepository = get()) }

    viewModel { HomeViewModel() }

    viewModel { LibraryViewModel() }

}