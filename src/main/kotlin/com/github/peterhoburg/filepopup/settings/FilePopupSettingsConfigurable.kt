package com.github.peterhoburg.filepopup.settings

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.BoundSearchableConfigurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.util.Disposer
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.layout.ValidationInfoBuilder
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.notExists


internal class FilePopupSettingsConfigurable(private val project: Project): BoundSearchableConfigurable(
    "FilePopup",
    "FilePopup",
    _id = ID
) {
    private val settings
        get() = FilePopupSettings.getInstance(project)
    companion object {
    const val ID = "Settings.FilePopup"
        }

    override fun createPanel(): DialogPanel {
        return panel{
            row{
                customCssTextFieldWithBrowserButton()
                    .align(AlignX.FILL)
                    .enabled(true)
                    .applyIfEnabled()
                    .bindText(
                        getter = { settings.popupFilePath.orEmpty() },
                        setter = { settings.popupFilePath = it }
                    )
            }
        }



    }
    private fun validateCustomStylesheetPath(builder: ValidationInfoBuilder, textField: TextFieldWithBrowseButton): ValidationInfo? {
        val text = textField.text
        val file = runCatching { Path.of(text) }.getOrNull()
        if (file == null || file.notExists() || file.isDirectory()) {
            return builder.error("File does not exist")
        }
        return null
    }

    private fun Row.customCssTextFieldWithBrowserButton(): Cell<TextFieldWithBrowseButton> {
        val field = textFieldWithBrowseButton(
            project = project,
            fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor("html")
        )
        field.applyToComponent {
            disposable?.let { Disposer.register(it, this@applyToComponent) }
        }
        return field.validationOnInput(::validateCustomStylesheetPath).validationOnApply(::validateCustomStylesheetPath)
    }
}
