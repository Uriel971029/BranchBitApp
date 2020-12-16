package com.example.katia.examenandroidbranchbitapp.utils.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.katia.examenandroidbranchbitapp.R


class DialogFragmentLoader : DialogFragment() {

    companion object {
        fun newInstance(title: String?): DialogFragmentLoader {
            val frag = DialogFragmentLoader()
            val args = Bundle()
            args.putString("title", title)
            frag.setArguments(args)
            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loader, container)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = requireArguments().getString("title", "Default")
        dialog!!.setTitle(title)
    }
}