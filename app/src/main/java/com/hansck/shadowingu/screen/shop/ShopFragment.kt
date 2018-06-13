package com.hansck.shadowingu.screen.shop


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.presenter.ShopPresenter
import com.hansck.shadowingu.presentation.presenter.ShopPresenter.ShopView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 *
 */
class ShopFragment : BaseFragment(), ShopPresenter.ShopView {

    private lateinit var presenter: ShopPresenterImpl
    private lateinit var model: ShopViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        presenter.presentState(LOAD_AVATARS)
    }

    private fun init() {
        this.model = ShopViewModel(activity)
        this.presenter = ShopPresenterImpl(this)
    }

    override fun showState(viewState: ShopPresenter.ShopView.ViewState) {
        when (viewState) {
            IDLE -> showProgress(false)
            LOADING -> showProgress(true)
            SHOW_AVATARS -> showAvatars()
            ERROR -> showError(null, getString(R.string.failed_request_general))
        }
    }

    override fun doRetrieveModel(): ShopViewModel = this.model

    private fun showAvatars() {

    }
}
