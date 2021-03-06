package codeone.com.br.gympass.views

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import codeone.com.br.gympass.R
import codeone.com.br.gympass.adpters.CompanyAdpter
import codeone.com.br.gympass.api.BaseUrl
import codeone.com.br.gympass.models.Company
import codeone.com.br.gympass.models.Details
import codeone.com.br.gympass.models.Features
import codeone.com.br.gympass.presenters.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_company.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        MainActivityPresenter.ViewCallBack {

    private val presenter:MainActivityPresenter by lazy { MainActivityPresenter(this) }
    private var adapter:CompanyAdpter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        presenter.onViewCreated()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun setUpRecycler() {
        rvCompany.layoutManager = LinearLayoutManager(this)
        rvCompany.itemAnimator = DefaultItemAnimator()
    }

    override fun showProgressView() {
        pbRecyclerCompany.visibility = View.VISIBLE
    }

    override fun hideProgressView() {
        pbRecyclerCompany.visibility = View.GONE
    }


    override fun onSwipeCompleteLoadItems() {
        srCompany.setOnRefreshListener {

            srCompany.isRefreshing = false
        }
    }

    override fun setFeaturesNearBySearch(company: MutableList<Company>) {
        rvCompany.visibility = View.VISIBLE
        if(adapter == null){
            adapter = CompanyAdpter(this, company, onClickItem())
            rvCompany.adapter = adapter
        }else{
            adapter?.setList(company)
            adapter?.notifyDataSetChanged()
        }
    }

    override fun showActivityDetails(params:Pair<String, Any>) {
        startActivity<DetailsActivity>(params)
    }
    private fun onClickItem():(Company) -> Unit = {
        company ->  presenter.clickedItem(company)
    }
}
