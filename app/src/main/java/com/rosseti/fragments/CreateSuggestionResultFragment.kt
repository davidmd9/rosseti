package com.rosseti.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import com.rosseti.R
import com.rosseti.base.BaseFragment
import com.rosseti.models.BaseModel
import com.rosseti.models.SuggestionRequest
import com.rosseti.network.ApiClient
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class CreateSuggestionResultFragment(private var model: SuggestionRequest): BaseFragment() {
    val PICK_IMAGE = 1
    override var title = "Создать"

    lateinit var etContent: EditText
    lateinit var btnNext: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_suggestion_result, container, false)
        bindView(view)

        return view
    }
    private fun bindView(view: View) {
        etContent = view.findViewById(R.id.etContent)
        btnNext = view.findViewById(R.id.btnNext)

        btnNext.setOnClickListener {
            showProgress()
            model.positiveEffect = etContent.text.toString()

            ApiClient.getApi().sendApplication(
                model.title,
                model.topicId,
                model.existingSolutionText,
                model.proposedSolutionText,
                model.positiveEffect,
                getPart(model.existingSolutionImage, "existing_solution_image"),
                getPart(model.proposedSolutionImage, "proposed_solution_image"),
                getPart(model.existingSolutionVideo, "existing_solution_video"),
                getPart(model.proposedSolutionVideo, "proposed_solution_video")
            ).enqueue(object : Callback<BaseModel> {
                override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                    hideProgress()
                    if (response.isSuccessful) {
                        for (i in 17..21) {
                            activity?.supportFragmentManager?.popBackStack()
                        }
                    }
                }

                override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                    hideProgress()
                    Log.e("error", "send application")
                }
            })
        }

    }

    private fun getPart(file: File?, fieldName: String): MultipartBody.Part? {
        if (file == null) return null

        val requestFile: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), file)


        return MultipartBody.Part.createFormData(fieldName, file?.name, requestFile)
    }

}