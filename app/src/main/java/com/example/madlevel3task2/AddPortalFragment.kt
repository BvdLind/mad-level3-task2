package com.example.madlevel3task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_portal_create.*

const val REQ_PORTAL_KEY = "req_portal"
const val BUNDLE_PORTAL_KEY = "bundle_portal"

class AddPortalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_portal_create, container, false)
    }

    /**
     * If input is valid then finish activity, set result to ok and parse the created [Portal].
     */
    private fun addPortal() {
        if (validateInput()) {
            val portal = Portal(etTitle.text.toString(), etUrl.text.toString())
            setFragmentResult(REQ_PORTAL_KEY, bundleOf(Pair(BUNDLE_PORTAL_KEY, portal)))
            findNavController().popBackStack()
        }
    }

    /**
     * Check if the input fields are filled in and if the url is valid.
     * Return false and display toast message for invalid input. Else return true.
     * Note: It's important that the Url starts with http:// or https://
     */
    private fun validateInput(): Boolean {
        if (etTitle.text.toString().isBlank() && etUrl.text.toString().isBlank()) {
            Toast.makeText(requireContext(), "Please fill in the title and url.", Toast.LENGTH_LONG)
                .show()
            return false
        }

        if (!URLUtil.isValidUrl(etUrl.text.toString())) {
            Toast.makeText(requireContext(), "The uri is invalid.", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddPortal.setOnClickListener { addPortal() }
    }
}
