package com.open.core_base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.open.core_base.utils.system.ScreenUtil
import com.open.core_base.utils.system.StatusBarUtil

abstract class AbsListFragment : CommonFragment() {

    private var recyclerView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    protected var adapter: RecyclerView.Adapter<*>? = null

    private val isRefreshing: MutableLiveData<Boolean> by lazy {
        val liveData = MutableLiveData<Boolean>()
        liveData.observe(this@AbsListFragment) {
            swipeRefreshLayout?.isRefreshing = it
        }
        liveData
    }

    protected open fun enableRefresh(): Boolean = true

    protected open fun getRefreshListener(): SwipeRefreshLayout.OnRefreshListener? = null

    protected fun setRefreshing(isRefreshing: Boolean) {
        this.isRefreshing.postValue(isRefreshing)
    }

    protected open fun getCustomChildView(container: ViewGroup): View? = null

    override fun getRootView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = FrameLayout(requireContext())
        rootView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        swipeRefreshLayout = SwipeRefreshLayout(requireContext())
        swipeRefreshLayout?.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        swipeRefreshLayout?.isEnabled = enableRefresh()
        swipeRefreshLayout?.setOnRefreshListener(getRefreshListener())

        val customChildView = getCustomChildView(rootView)
        customChildView?.alpha = 0f

        recyclerView = RecyclerView(requireContext())
        recyclerView?.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        swipeRefreshLayout?.addView(recyclerView)
        rootView.addView(swipeRefreshLayout)
        if (customChildView != null) {
            rootView.addView(customChildView)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(recyclerView!!)
        adapter = initAdapter()
        recyclerView?.adapter = adapter
    }

    protected abstract fun initRecyclerView(recyclerView: RecyclerView)

    protected abstract fun initAdapter(): RecyclerView.Adapter<*>
}