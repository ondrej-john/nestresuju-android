package cz.nestresuju.screens.diary

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import cz.nestresuju.databinding.ViewEpoxyListBinding
import cz.nestresuju.model.entities.domain.diary.DiaryEntry
import cz.nestresuju.screens.base.BaseFragment
import cz.nestresuju.screens.diary.epoxy.DiaryController
import cz.nestresuju.screens.diary.errors.DiaryErrorHandler
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate

class DiaryFragment : BaseFragment<ViewEpoxyListBinding>(),
    DiaryEditEntryDialogFragment.OnEntryEditConfirmedListener,
    DiaryDeleteEntryDialogFragment.OnEntryDeleteConfirmedListener {

    companion object {

        private const val TAG_EDIT_NOTE_DIALOG = "edit_note_dialog"
        private const val TAG_DELETE_NOTE_DIALOG = "delete_note_dialog"
    }

    private lateinit var controller: DiaryController

    override val viewModel by viewModel<DiaryViewModel>()

    override val errorHandlers = super.errorHandlers + DiaryErrorHandler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ViewEpoxyListBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = DiaryController(
            onStressLevelSelected = { stressLevel -> viewModel.onStressLevelSelected(stressLevel) },
            onInputConfirmed = { viewModel.onAnswerConfirmed() },
            onAnswerChanged = { answer -> viewModel.answer = answer },
            onStressLevelEditClicked = { entry -> onShowEditEntryDialog(entry) },
            onNoteEditClicked = { entry -> onShowEditEntryDialog(entry) },
            onNoteDeleteClicked = { entry -> onShowDeleteEntryConfirmationDialog(entry) }
        ).apply {
            answer = viewModel.answer
        }

        viewBinding.list.setController(controller)
        controller.requestModelBuild()

        viewModel.inputStream.observe(viewLifecycleOwner, Observer { input ->
            controller.input = input
        })

        viewModel.clearAnswerEvent.observe(viewLifecycleOwner, Observer {
            controller.answer = null
            (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(view.windowToken, 0)
        })

        viewModel.entriesStream.observe(viewLifecycleOwner, Observer { entries ->
            controller.entries = entries
            controller.showSmallInput = entries.any { it.dateCreated.toLocalDate() == LocalDate.now() }
        })
    }

    override fun onDiaryEntryEditConfirmed(entryId: Long, modifiedText: String) {
        viewModel.onEditEntry(entryId, modifiedText)
    }

    override fun onDiaryEntryDeleteConfirmed(entryId: Long) {
        viewModel.onDeleteEntry(entryId)
    }

    private fun onShowEditEntryDialog(entry: DiaryEntry) {
        DiaryEditEntryDialogFragment.newInstance(entry.id, entry.text).show(childFragmentManager, TAG_EDIT_NOTE_DIALOG)
    }

    private fun onShowDeleteEntryConfirmationDialog(entry: DiaryEntry) {
        DiaryDeleteEntryDialogFragment.newInstance(entry.id).show(childFragmentManager, TAG_DELETE_NOTE_DIALOG)
    }
}
