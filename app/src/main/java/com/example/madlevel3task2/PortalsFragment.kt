package com.example.madlevel3task2

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_portals.*

class PortalsFragment : Fragment() {

    private val portals = arrayListOf<Portal>()
    private val portalAdapter = PortalAdapter(portals) { onPortalClick(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_portals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Recycler View.
        rvPortals.layoutManager = GridLayoutManager(activity, 2)
        rvPortals.adapter = portalAdapter

        // Some initial data.
        if (portals.isEmpty()) {
            portals.apply {
                add(Portal("Vlo", "http://vlo.informatica.hva.nl"))
                add(Portal("Roosters", "http://roosters.hva.nl"))
                add(Portal("Sis", "http://sis.hva.nl"))
                add(Portal("HvA", "http://hva.nl"))
            }
        }

        observeAddReminderResult()
    }

    /**
     * Use Custom Tabs to launch a web activity with the portal url.
     * Note: It's important that the Url starts with http:// or https://
     */
    private fun onPortalClick(portal: Portal) {
        val builder = CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();

        // Launch url if it's a valid url.
        if (URLUtil.isValidUrl(portal.url)) customTabsIntent.launchUrl(
            requireContext(),
            Uri.parse(portal.url)
        )
        else Toast.makeText(context, "Invalid Url: ${portal.url}", Toast.LENGTH_SHORT).show()
    }

    private fun observeAddReminderResult() {
        setFragmentResultListener(REQ_PORTAL_KEY) { _, bundle ->
            bundle.getParcelable<Portal>(BUNDLE_PORTAL_KEY)?.let {
                val portal = Portal(it.title, it.url)

                portals.add(portal)
                portalAdapter.notifyDataSetChanged()
            } ?: Log.e("PortalsFragment", "Request triggered, but empty portal text!")

        }
    }
}
