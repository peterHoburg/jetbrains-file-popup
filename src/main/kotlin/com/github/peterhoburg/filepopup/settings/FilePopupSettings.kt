package com.github.peterhoburg.filepopup.settings

import com.intellij.openapi.components.*
import com.intellij.openapi.editor.colors.impl.AppEditorFontOptions
import com.intellij.openapi.project.Project
import com.intellij.ui.jcef.JBCefApp
import com.intellij.util.messages.Topic

@Service(Service.Level.PROJECT)
@State(name = "FilePopupSettings", storages = [(Storage("file_popup.xml"))])
class FilePopupSettings(internal val project: Project): SimplePersistentStateComponent<FilePopupSettingsState>(FilePopupSettingsState()) {
    var popupFilePath
        get () = state.popupFilePath
        set(value) {state.popupFilePath = value}
}

