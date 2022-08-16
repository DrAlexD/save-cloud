/**
 * 4 pictures with animation and screenshots from SAVE
 */

package com.saveourtool.save.frontend.components.views.welcome.pagers

import com.saveourtool.save.frontend.externals.animations.*

import csstype.Color
import csstype.Width
import react.ChildrenBuilder
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.h1

import kotlinx.js.jso

@Suppress("CUSTOM_GETTERS_SETTERS")
object GeneralInfoFirstPicture : WelcomePager {
    override val animation: Animation
        get() = fadeUpTopLeft

    override fun renderPage(childrenBuilder: ChildrenBuilder) {
        childrenBuilder.renderAnimatedPage()
    }

    private fun ChildrenBuilder.renderAnimatedPage() {
        h1 {
            style = jso {
                color = "rgb(6, 7, 89)".unsafeCast<Color>()
            }
            +"Easy to start"
        }
        ReactHTML.img {
            style = jso {
                width = "65%".unsafeCast<Width>()
            }
            src = "img/run_view.png"
        }
    }
}

@Suppress("CUSTOM_GETTERS_SETTERS")
object GeneralInfoSecondPicture : WelcomePager {
    override val animation: Animation
        get() = fadeUpTopRight

    override fun renderPage(childrenBuilder: ChildrenBuilder) {
        childrenBuilder.renderAnimatedPage()
    }

    private fun ChildrenBuilder.renderAnimatedPage() {
        h1 {
            style = jso {
                color = "rgb(6, 7, 89)".unsafeCast<Color>()
            }
            +"User-friendly dashboards"
        }
        ReactHTML.img {
            style = jso {
                width = "130%".unsafeCast<Width>()
            }
            src = "img/exec_view.png"
        }
    }
}

@Suppress("CUSTOM_GETTERS_SETTERS")
object GeneralInfoThirdPicture : WelcomePager {
    override val animation: Animation
        get() = fadeUpBottomLeft

    override fun renderPage(childrenBuilder: ChildrenBuilder) {
        childrenBuilder.renderAnimatedPage()
    }

    private fun ChildrenBuilder.renderAnimatedPage() {
        h1 {
            style = jso {
                color = "rgb(6, 7, 89)".unsafeCast<Color>()
            }
            +"Statistics for your tool"
        }
        ReactHTML.img {
            style = jso {
                width = "65%".unsafeCast<Width>()
            }
            src = "img/stat_view.png"
        }
    }
}

@Suppress("CUSTOM_GETTERS_SETTERS")
object GeneralInfoFourthPicture : WelcomePager {
    override val animation: Animation
        get() = fadeUpBottomRight

    override fun renderPage(childrenBuilder: ChildrenBuilder) {
        childrenBuilder.renderAnimatedPage()
    }

    private fun ChildrenBuilder.renderAnimatedPage() {
        h1 {
            style = jso {
                color = "rgb(6, 7, 89)".unsafeCast<Color>()
            }
            +"Build a team"
        }
        ReactHTML.img {
            style = jso {
                width = "135%".unsafeCast<Width>()
            }
            src = "img/organization_view.png"
        }
    }
}