package com.github.peterhoburg.filepopup.settings
import com.intellij.openapi.components.BaseState
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
class FilePopupSettingsState: BaseState() {
    private val helpFileName = "JETBRAINS_FILE_POPUP.html"
    private val userHome = System.getProperty("user.home")
    private val helpFilePath= "$userHome/$helpFileName"

    var popupFilePath by string(helpFilePath)
}
