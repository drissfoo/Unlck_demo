import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/*
* Usage: 
* `private var binding: TheViewBinding by viewLifecycle()`
*/

/**
 * An extension to bind and unbind a value based on the view lifecycle of a Fragment.
 * The binding will be unbound in onDestroyView.
 *
 * @throws IllegalStateException If the getter is invoked before the binding is set,
 *                               or after onDestroyView an exception is thrown.
 */
fun <T> Fragment.viewLifecycle(): ReadWriteProperty<Fragment, T> =
    object : ReadWriteProperty<Fragment, T>, DefaultLifecycleObserver {

        private var binding: T? = null

        init {
            // Observe the view lifecycle of the Fragment.
            // The view lifecycle owner is null before onCreateView and after onDestroyView.
            // The observer is automatically removed after the onDestroy event.
            this@viewLifecycle
                .viewLifecycleOwnerLiveData
                .observe(this@viewLifecycle, { owner: LifecycleOwner? ->
                    owner?.lifecycle?.addObserver(this)
                })
        }

        override fun onDestroy(owner: LifecycleOwner) {
            binding = null
        }

        override fun getValue(
            thisRef: Fragment,
            property: KProperty<*>
        ): T {
            return this.binding ?: error("Called before onCreateView or after onDestroyView.")
        }

        override fun setValue(
            thisRef: Fragment,
            property: KProperty<*>,
            value: T
        ) {
            this.binding = value
        }
    }

fun Fragment.safeLifecycle(): ReadWriteProperty<Fragment, Boolean> =
    object : ReadWriteProperty<Fragment, Boolean>, DefaultLifecycleObserver {

        private var isActiveLifeCycle: Boolean? = false

        init {
            // Observe the view lifecycle of the Fragment.
            // The view lifecycle owner is null before onCreateView and after onDestroyView.
            // The observer is automatically removed after the onDestroy event.
            this@safeLifecycle
                .viewLifecycleOwnerLiveData
                .observe(this@safeLifecycle, { owner: LifecycleOwner? ->
                    owner?.lifecycle?.addObserver(this)
                })
        }

        override fun onDestroy(owner: LifecycleOwner) {
            isActiveLifeCycle = false
        }

        override fun getValue(
            thisRef: Fragment,
            property: KProperty<*>
        ): Boolean {
            return this.isActiveLifeCycle
                ?: error("Called before onCreateView or after onDestroyView.")
        }

        override fun setValue(
            thisRef: Fragment,
            property: KProperty<*>,
            value: Boolean
        ) {
            this.isActiveLifeCycle = value
        }
    }