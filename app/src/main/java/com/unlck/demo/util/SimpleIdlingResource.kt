package com.unlck.demo.util

import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean


class SimpleIdlingResource : IdlingResource {

    @Volatile
    var mCallback: IdlingResource.ResourceCallback? = null
    private val mIsIdleNow = AtomicBoolean(true)
    override fun getName(): String {
        return this.javaClass.name
    }

    override fun isIdleNow(): Boolean {
        return mIsIdleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.mCallback = callback
    }

    /**
     * Sets the new idle state, if isIdleNow is true, it pings the [androidx.test.espresso.IdlingResource.ResourceCallback].
     * @param isIdleNow false if there are pending operations, true if idle.
     */
    fun setIdleState(isIdleNow: Boolean) {
        this.mIsIdleNow.set(isIdleNow)
        if (isIdleNow) {
            mCallback?.onTransitionToIdle()
        }
    }
}