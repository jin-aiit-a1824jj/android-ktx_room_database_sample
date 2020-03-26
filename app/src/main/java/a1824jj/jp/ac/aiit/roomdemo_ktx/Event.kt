package a1824jj.jp.ac.aiit.roomdemo_ktx

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set


    fun getContentIfNotHandled(): T? {
        return if(hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content

}