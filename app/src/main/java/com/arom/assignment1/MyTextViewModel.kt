package com.arom.assignment1

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

enum class ActionType{
    SAVE
}
class MyTextViewModel:ViewModel(){
    private val _currenValue= MutableStateFlow<String>("")
    val currenValue: StateFlow<String>
        get()=_currenValue
    companion object{
        const val TAG :String="로그"
    }
    init {
        Log.d(TAG, "생성자 호출")
        _currenValue.value ="입력해주세요"
    }

    fun updateValue(actionType: ActionType, input:String){
        viewModelScope.launch {
            _currenValue.value=input
            val current = _currenValue.value ?: 0

            when(actionType){
                ActionType.SAVE->_currenValue.emit(input)
            }


        }




    }








}

