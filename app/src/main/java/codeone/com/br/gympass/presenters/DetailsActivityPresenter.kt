package codeone.com.br.gympass.presenters

import android.content.Context
import android.os.Bundle
import codeone.com.br.gympass.api.BaseUrl
import codeone.com.br.gympass.api.services.ServiceApi
import codeone.com.br.gympass.models.Company
import codeone.com.br.gympass.models.Details
import codeone.com.br.gympass.models.Photos
import codeone.com.br.gympass.models.Reviews
import codeone.com.br.gympass.utils.BundlesConstants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

open class DetailsActivityPresenter(val viewCallBack:ViewCallBack) {

    private lateinit var company: Company

    interface ViewCallBack{

        fun setDetails(details: Details)
        fun setUpToolbar(title:String?)
        fun setUpRecycler()
        fun setMapDetails(extras: Bundle)
    }

    fun onCreate(extras: Bundle, context: Context){
        //Recuperando a company pela key
        company = extras.getParcelable(BundlesConstants.COMPANY_KEY)
        viewCallBack.setUpToolbar(company.name)
        viewCallBack.setUpRecycler()
        taskDetails(context)
    }

    open fun taskDetails(context: Context){
        Observable.fromCallable { ServiceApi.getDetails(company.placeId, BaseUrl.API_KEY) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onNext = {
                    if(it == null){
                        return@subscribeBy
                    }
                    viewCallBack.setDetails(it)
                    viewCallBack.setMapDetails(setBundleDetails(it))
                })
    }

    fun clickedItem(photo: Photos) {
        val bundle = Bundle()

    }

    fun clickedItemReview(review:Reviews){
        val bundle = Bundle()
    }


    private fun setBundleDetails(details: Details):Bundle{
        val bundle = Bundle()
        bundle.putParcelable(BundlesConstants.DETAILS_KEY, details)
        return bundle
    }
}