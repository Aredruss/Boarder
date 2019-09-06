package com.redbox.anchan.page


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.redbox.anchan.network.ApiService
import com.redbox.anchan.network.NetworkModule
import com.redbox.anchan.network.pojo.Page
import com.redbox.anchan.network.pojo.Post
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class PageViewModel : ViewModel() {
    var posts = MutableLiveData<List<Post>>()

    //Loading First Posts from the API
    fun loadPage(board: String, page: Int = 1) {
        NetworkModule.retrofit.create(ApiService::class.java)
            .getPage(board, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Page>() {
                override fun onSuccess(t: Page) {
                    val p: MutableList<Post> = mutableListOf()
                    for (element in t.threads) {
                        p.add(element.posts[0])
                    }
                    posts.postValue(p)
                }
                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    fun getPosts(
        lifecycleOwner: LifecycleOwner,
        callback: (List<Post>) -> Unit
    ) = posts.observe(lifecycleOwner, Observer(callback))


}
