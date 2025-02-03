package com.github.peterhoburg.filepopup.settings

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
@State(name = "FilePopupSettings", storages = [(Storage("file_popup.xml"))])
class FilePopupSettings(internal val project: Project) :
    SimplePersistentStateComponent<FilePopupSettingsState>(FilePopupSettingsState()) {
    var popupFilePath
        get() = state.popupFilePath
        set(value) {
            state.popupFilePath = value
        }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): FilePopupSettings = project.service()
    }
}

