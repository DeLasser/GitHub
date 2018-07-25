package ru.mininn.github.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_build_info.view.*
import ru.mininn.github.R

class BuildInfoFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_build_info, container, false)
        view.build_version.text = activity!!.packageManager.getPackageInfo(activity!!.packageName, 0).versionName
        return view
    }
}