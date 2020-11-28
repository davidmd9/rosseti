package com.rosseti.models

import com.google.gson.annotations.SerializedName
import java.io.File

class SuggestionRequest {
    var title: String? = ""

    @SerializedName("topic_id")
    var topicId: Int = 0
    @SerializedName("existing_solution_text")
    var existingSolutionText: String = ""
    @SerializedName("existing_solution_image")
    var existingSolutionImage: File? = null
    @SerializedName("existing_solution_video")
    var existingSolutionVideo: File? = null

    @SerializedName("proposed_solution_text")
    var proposedSolutionText: String = ""
    @SerializedName("proposed_solution_image")
    var proposedSolutionImage: File? = null
    @SerializedName("proposed_solution_video")
    var proposedSolutionVideo: File? = null

    @SerializedName("positive_effect")
    var positiveEffect: String = ""

}