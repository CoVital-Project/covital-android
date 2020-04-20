package org.covital.common.extensions

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import timber.log.Timber
import java.lang.reflect.Field

fun MotionLayout.after(completion: () -> Unit) {
    Timber.v("Started listening, currently at ${currentState.resName(context)}")
    this.setTransitionListener(object: MotionLayout.TransitionListener {

        override fun onTransitionTrigger(layout: MotionLayout?, startId: Int, something: Boolean, progress: Float) {}
        override fun onTransitionStarted(layout: MotionLayout?, startId: Int, endId: Int) {
            layout?.context?.let { context ->
                Timber.d("Started from ${startId.resName(context)} -> ${layout.currentState.resName(context)} -> to ${endId.resName(context)}")
            }
        }
        override fun onTransitionChange(layout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
            layout?.context?.let { context ->
                Timber.v("Changing from ${startId.resName(context)} -> $progress -> to ${endId.resName(context)}")
            }
        }
        override fun onTransitionCompleted(layout: MotionLayout?, currentId: Int) {
            layout?.context?.let { context ->
                Timber.d("Completed at ${currentId.resName(context)}")
            }
            completion()
        }

    })
}

fun MotionLayout.stopListening() {
    this.setTransitionListener(object: MotionLayout.TransitionListener {
        override fun onTransitionTrigger(layout: MotionLayout?, startId: Int, something: Boolean, progress: Float) {}
        override fun onTransitionStarted(layout: MotionLayout?, startId: Int, endId: Int) {}
        override fun onTransitionChange(layout: MotionLayout?, startId: Int, endId: Int, progress: Float) {}
        override fun onTransitionCompleted(layout: MotionLayout?, currentId: Int) {}
    })
}

fun MotionLayout.setActionListeners(vararg listeners: MotionLayoutActionListener) {
    stopListening()
    var currentStartScene = 0

    this.setTransitionListener(object: MotionLayout.TransitionListener {
        override fun onTransitionTrigger(layout: MotionLayout?, startId: Int, something: Boolean, progress: Float) {}
        override fun onTransitionStarted(layout: MotionLayout?, startId: Int, endId: Int) {
            currentStartScene = startId
        }
        override fun onTransitionChange(layout: MotionLayout?, startId: Int, endId: Int, progress: Float) {}
        override fun onTransitionCompleted(layout: MotionLayout?, currentId: Int) {
            listeners.forEach { it.performActionIfCorrectTransition(currentStartScene, currentId) }
        }
    })
}

class MotionLayoutActionListener(
    private val startScene: Int,
    private val endScene: Int,
    val action: () -> Unit
) {
    fun performActionIfCorrectTransition(start: Int, end: Int) {
        if (startScene == start && endScene == end) action()
    }
}

fun MotionLayout.transitionTo(currentState: Int, nextState: Int) {
    Timber.v("transitionTo ${currentState.resName(context)} -> ${nextState.resName(context)}, currently at ${currentState.resName(context)}")
    setTransition(currentState, nextState)
    progress = 0f
    transitionToEnd()
}

fun MotionLayout.goTo(next: Int, immediately: Boolean = false, completion: () -> Unit = {}) {
    Timber.d("goTo ${next.resName(context)} immediately $immediately, currently at ${currentState.resName(context)}")
    val currentMotion = currentState
    val start = startState
    val end = endState
    stopListening()
    after(completion)
    when (currentMotion) {
        -1 -> when (end) {
            -1 -> {
                stopListening()
                completion()
            }
            next -> transitionToEnd()
            else -> transitionTo(end, next)
        }
        start -> when (end) {
            next -> transitionToEnd()
            else -> transitionTo(start, next)
        }
        end -> when (end) {
            next -> {
                stopListening()
                completion()
            }
            else -> {
                transitionTo(end, next)
                // TODO: On Discover the completion handler does not run the first time.
                // This results in a bug where the Done button is not tappable.
            }
        }
    }

    if (immediately) {
        progress = 1f
        Timber.v("Used immediate progress to transition, currently at ${currentState.resName(context)}")
    }
}

fun MotionLayout.goTo(next: Int, delay: Long, immediately: Boolean = false, completion: () -> Unit = {}) {
    Timber.d("goTo WITH DELAY ($delay) ${next.resName(context)}, currently at ${currentState.resName(context)}")
    stopListening()
    if (needsTransition(next)) {
        postDelayed({
            goTo(next, immediately, completion)
        }, delay)
    } else {
        goTo(next, immediately, completion)
    }
}

fun MotionLayout.needsTransition(destination: Int): Boolean {
    val end = endState
    return when (currentState) {
        -1 -> end != destination
        startState -> end != destination
        end -> end != destination
        else -> false
    }.also {
        if (it) {
            Timber.v("needs transition to reach ${destination.resName(context)}, currently at ${currentState.resName(context)}: $it")
        } else {
            Timber.v("does not need transition to reach ${destination.resName(context)}, currently at ${currentState.resName(context)}: $it")
        }
    }
}

private fun MotionScene.Transition.setAutoTransition(value: Int) {
    try {
        val field: Field = MotionScene.Transition::class.java.getDeclaredField("mAutoTransition")
        field.isAccessible = true
        field.setInt(this, value)
        field.isAccessible = false
    } catch (ex: NoSuchFieldException) {
        Timber.e(ex, "Could not set autoTransition flag")
    }
}

@Suppress("UNUSED")
fun MotionLayout.filterTransitions(predicate: (MotionScene.Transition) -> Boolean): List<MotionScene.Transition> {
    return definedTransitions?.filterNotNull()?.filter(predicate) ?: emptyList()
}

@Suppress("UNUSED")
fun MotionScene.Transition.removeAutoTransition() {
    setAutoTransition(MotionScene.Transition.AUTO_NONE)
}

@Suppress("UNUSED")
fun MotionScene.Transition.autoAnimateToEnd() {
    setAutoTransition(MotionScene.Transition.AUTO_ANIMATE_TO_END)
}

@SuppressLint("ClickableViewAccessibility")
fun MotionLayout.jumpToStartOnRelease(start: Int, end: Int) {
    // stopListening()
    setOnTouchListener { v, event ->
        when {
            event.action != MotionEvent.ACTION_UP -> false
            startState != start -> false
            endState != end -> false
            else -> {
                setOnTouchListener(null)
                val progressValue = progress
                setTransitionDuration((600 * progressValue).toInt().coerceAtLeast(200))
                after {
                    jumpToStartOnRelease(start, end)
                }
                transitionToStart()
                true
            }
        }
    }
}
