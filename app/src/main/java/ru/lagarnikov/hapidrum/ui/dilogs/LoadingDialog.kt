package ru.lagarnikov.hapidrum.ui.dilogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.lagarnikov.hapidrum.R

class LoadingDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val dialogBuilder = AlertDialog.Builder(it)
            dialogBuilder.setTitle(R.string.loading)
            dialogBuilder.setMessage(R.string.loading_text)
            dialogBuilder.setCancelable(true)
            dialogBuilder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}