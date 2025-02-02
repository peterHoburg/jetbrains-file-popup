package com.github.peterhoburg.filepopup.settings

import com.intellij.openapi.options.BoundSearchableConfigurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel

internal class FilePopupSettingsConfigurable(private val project: Project): BoundSearchableConfigurable(
    "FilePopup",
    "FilePopup",
    _id = ID
) {
    companion object {
    const val ID = "Settings.FilePopup"
        }

    override fun createPanel(): DialogPanel {
        TODO("Not yet implemented")
    }
}
