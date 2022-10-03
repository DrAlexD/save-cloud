@file:Suppress("FILE_NAME_MATCH_CLASS", "HEADER_MISSING_IN_NON_SINGLE_CLASS_FILE")

package com.saveourtool.save.frontend.components.basic.codeeditor

import com.saveourtool.save.frontend.components.basic.cardComponent
import com.saveourtool.save.frontend.externals.reactace.AceModes
import com.saveourtool.save.frontend.externals.reactace.AceThemes
import com.saveourtool.save.frontend.externals.reactace.aceBuilder
import com.saveourtool.save.frontend.utils.buttonBuilder
import com.saveourtool.save.frontend.utils.selectorBuilder
import csstype.ClassName
import react.ChildrenBuilder
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h6
import react.useState

/**
 * CodeEditor component
 */
val codeEditorComponent = codeEditorComponent()

private val toolbarCard = cardComponent(isBordered = true)

/**
 * CodeEditor functional component [Props]
 */
external interface CodeEditorComponentProps : Props {
    /**
     * Title of an editor
     */
    var editorTitle: String

    /**
     * Currently inputted text
     */
    var editorText: String

    /**
     * Currently selected [FileType]
     */
    var selectedFile: FileType?

    /**
     * Callback invoked for selectFile change
     */
    var onSelectedFileUpdate: (FileType?) -> Unit

    /**
     * Callback invoked on ace editor change
     */
    var onEditorTextUpdate: (String) -> Unit
}

/**
 * @property prettyName displayed name
 * @property editorMode highlight mode that should be enabled, if null, mode can be chosen using selector
 */
enum class FileType(val prettyName: String, val editorMode: AceModes?) {
    CODE("code", null),
    SAVE_TOML("save.toml", AceModes.TOML),
    SETUP_SH("setup.sh", AceModes.SHELL),
    ;
}

@Suppress("TOO_MANY_PARAMETERS", "LongParameterList")
private fun ChildrenBuilder.displayEditorToolbar(
    selectedMode: AceModes,
    selectedTheme: AceThemes,
    selectedFileType: FileType?,
    setSelectedMode: (String) -> Unit,
    setSelectedTheme: (String) -> Unit,
    onFileTypeChange: (FileType) -> Unit,
) {
    toolbarCard {
        div {
            className = ClassName("input-group")
            div {
                className = ClassName("input-group-prepend")
                FileType.values().forEach { fileType ->
                    buttonBuilder(
                        fileType.prettyName,
                        "primary",
                        isOutline = true,
                        isActive = selectedFileType == fileType,
                    ) {
                        onFileTypeChange(fileType)
                    }
                }
            }
            selectorBuilder(
                selectedFileType?.editorMode?.modeName ?: selectedMode.modeName,
                AceModes.values().map { it.modeName },
                "custom-select",
                selectedFileType?.editorMode != null,
            ) { event ->
                setSelectedMode(event.target.value)
            }
            selectorBuilder(
                selectedTheme.themeName,
                AceThemes.values().map { it.themeName },
                "custom-select",
                false,
            ) { event ->
                setSelectedTheme(event.target.value)
            }
        }
    }
}

private fun codeEditorComponent() = FC<CodeEditorComponentProps> { props ->
    val (selectedMode, setSelectedMode) = useState(AceModes.KOTLIN)
    val (selectedTheme, setSelectedTheme) = useState(AceThemes.CHROME)
    div {
        h6 {
            className = ClassName("text-center text-primary")
            +props.editorTitle
        }
        displayEditorToolbar(
            selectedMode,
            selectedTheme,
            props.selectedFile,
            { newModeName ->
                setSelectedMode(AceModes.values().find { it.modeName == newModeName }!!)
            },
            { newThemeName ->
                setSelectedTheme(AceThemes.values().find { it.themeName == newThemeName }!!)
            },
        ) { fileType ->
            if (fileType == props.selectedFile) {
                props.onSelectedFileUpdate(null)
            } else {
                props.onSelectedFileUpdate(fileType)
            }
        }

        aceBuilder(props.editorText, selectedMode, selectedTheme, props.selectedFile == null, props.onEditorTextUpdate)
    }
}