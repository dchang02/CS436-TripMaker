package course.examples.ui.recyclerview

import android.util.Log
import androidx.lifecycle.*

class RecyclerViewModel : ViewModel(), DefaultLifecycleObserver {

    private var _names: MutableLiveData<MutableList<String>> = MutableLiveData()
    private var _vicinity: MutableLiveData<MutableList<String>> = MutableLiveData()
    private var _namesToAdd: MutableLiveData<MutableList<String>> = MutableLiveData()
    private var _vicinityToAdd: MutableLiveData<MutableList<String>> = MutableLiveData()

    internal val names: MutableLiveData<MutableList<String>>
        get() = _names

    internal val vicinity: MutableLiveData<MutableList<String>>
        get() = _vicinity

    internal val namesToAdd: MutableLiveData<MutableList<String>>
        get() = _namesToAdd

    internal val vicinityToAdd: MutableLiveData<MutableList<String>>
        get() = _vicinityToAdd

    internal fun bindToActivityLifecycle(mainActivity: MainActivity) {
        mainActivity.lifecycle.addObserver(this)
    }

    fun setNames(list: MutableList<String>) {
        _names.value = list
    }

    fun setVicinity(list: MutableList<String>) {
        _vicinity.value = list
    }

    fun setNamesToAdd(list: MutableList<String>) {
        _namesToAdd.value = list
    }

    fun setVicinityToAdd(list: MutableList<String>) {
        _vicinityToAdd.value = list
    }

    fun removeItem(position: Int) {
        _namesToAdd.value!!.add(_names.value!!.removeAt(position))
        _vicinityToAdd.value!!.add(_vicinity.value!!.removeAt(position))
    }

    fun addItem(position: Int) {
        _names.value!!.add(_namesToAdd.value!!.removeAt(position))
        _vicinity.value!!.add(_vicinityToAdd.value!!.removeAt(position))
    }
}