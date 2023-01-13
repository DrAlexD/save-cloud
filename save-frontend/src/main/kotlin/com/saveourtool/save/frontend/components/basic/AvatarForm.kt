/**
 * Function component for avatar form
 */

@file:Suppress("FILE_NAME_MATCH_CLASS")

package com.saveourtool.save.frontend.components.basic

import com.saveourtool.save.frontend.components.modal.modalAvatarBuilder
import com.saveourtool.save.frontend.externals.imageeditor.reactAvatarImageCropper
import csstype.ClassName
import csstype.rem
import js.core.jso
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import web.file.File

val avatarForm = avatarForm()

/**
 * AvatarForm component props
 */
external interface AvatarFormProps : Props {
    /**
     * Flag to handle avatar Window
     */
    var isOpen: Boolean

    /**
     * Title of window
     */
    var title: String

    /**
     * Callback to update state for close window.
     */
    var onCloseWindow: () -> Unit

    /**
     * Callback to upload avatar.
     */
    var imageUpload: (File) -> Unit
}

private fun avatarForm() = FC<AvatarFormProps> { props ->

    modalAvatarBuilder(
        isOpen = props.isOpen,
        title = props.title,
        onCloseButtonPressed = {
            props.onCloseWindow()
        }
    ) {
        div {
            className = ClassName("shadow")
            style = jso {
                height = 18.rem
                width = 18.rem
            }
            reactAvatarImageCropper {
                apply = { file, _ ->
                    props.imageUpload(file)
                    props.onCloseWindow()
                }
            }
        }
    }
}