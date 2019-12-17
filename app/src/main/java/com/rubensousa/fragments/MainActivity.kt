package com.rubensousa.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_a.*
import kotlinx.android.synthetic.main.fragment_b.*

class MainActivity : AppCompatActivity() {

    // Compare behavior with FragmentContainerView and FrameLayout
    private val useFragmentContainerViewContainer = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(getContainerId(), FragmentA(), FragmentA.TAG)
                .commit()
        }
    }

    fun addFragmentB() {
        val fragmentA = supportFragmentManager.findFragmentByTag(FragmentA.TAG)
        val fragmentB = supportFragmentManager.findFragmentByTag(FragmentB.TAG)
        val transaction = supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)
        if (fragmentB == null) {
            transaction.add(getContainerId(), FragmentB(), FragmentB.TAG)
                .hide(fragmentA!!)
                .commit()
        } else {
            transaction.show(fragmentB)
                .hide(fragmentA!!)
                .commit()
        }
    }

    fun hideFragmentB() {
        val fragmentA = supportFragmentManager.findFragmentByTag(FragmentA.TAG)
        val fragmentB = supportFragmentManager.findFragmentByTag(FragmentB.TAG)
        startPopTransaction()
            .hide(fragmentB!!)
            .show(fragmentA!!)
            .commit()
    }

    fun removeFragmentB() {
        val fragmentA = supportFragmentManager.findFragmentByTag(FragmentA.TAG)
        val fragmentB = supportFragmentManager.findFragmentByTag(FragmentB.TAG)
        startPopTransaction()
            .remove(fragmentB!!)
            .show(fragmentA!!)
            .commit()
    }

    /**
     * Starts a transaction with the same animations for hide/remove
     */
    private fun startPopTransaction(): FragmentTransaction {
        return supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fragment_pop_enter, R.anim.fragment_pop_exit)
            .setReorderingAllowed(true)
    }

    private fun getContainerId(): Int {
        return if (useFragmentContainerViewContainer) {
            R.id.container
        } else {
            R.id.frameLayoutContainer
        }
    }
}

class FragmentA : Fragment(R.layout.fragment_a) {

    companion object {
        const val TAG = "A"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openButton.setOnClickListener {
            (activity as MainActivity).addFragmentB()
        }
    }

}

class FragmentB : Fragment(R.layout.fragment_b) {

    companion object {
        const val TAG = "B"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideButton.setOnClickListener {
            (activity as MainActivity).hideFragmentB()
        }
        removeButton.setOnClickListener {
            (activity as MainActivity).removeFragmentB()
        }
    }
}
