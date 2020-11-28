package com.rosseti.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import com.rosseti.MainActivity
import com.rosseti.R
import com.rosseti.base.BaseFragment
import com.rosseti.models.SuggestionRequest
import java.io.*

class CreateSuggestionSolutionFragment(private var model: SuggestionRequest): BaseFragment() {
    private val PICK_IMAGE = 3
    private val PICK_VIDEO = 4
    override var title = "Создать"

    lateinit var etContent: EditText
    lateinit var ivVideo: ImageView
    lateinit var ivPhoto: ImageView
    lateinit var ivTakeVideo: ImageView
    lateinit var ivTakePhoto: ImageView
    lateinit var btnNext: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_suggestion_solution, container, false)
        bindView(view)

        return view
    }
    private fun bindView(view: View) {
        etContent = view.findViewById(R.id.etContent)
        ivVideo = view.findViewById(R.id.ivVideo)
        ivPhoto = view.findViewById(R.id.ivPhoto)
        ivTakeVideo = view.findViewById(R.id.ivTakeVideo)
        ivTakePhoto = view.findViewById(R.id.ivTakePhoto)
        btnNext = view.findViewById(R.id.btnNext)

        ivTakeVideo.setOnClickListener {
            val intent = Intent()
            intent.type = "video/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_VIDEO)
        }

        ivTakePhoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }

        btnNext.setOnClickListener {
            model.proposedSolutionText = etContent.text.toString()
            (activity as? MainActivity)?.pushFragment(CreateSuggestionResultFragment(model), true)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE) {
            try {
                data?.data?.let { imageUri ->
                    val imageStream: InputStream? = activity?.contentResolver?.openInputStream(
                        imageUri
                    )
                    val selectedImage = BitmapFactory.decodeStream(imageStream)

                    val file = getFile(selectedImage)
                    model.proposedSolutionImage = file

                    ivPhoto.setImageBitmap(selectedImage)
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        } else if (requestCode == PICK_VIDEO) {
            try {
                data?.data?.let { imageUri ->
                    val videoStream: InputStream? = activity?.contentResolver?.openInputStream(
                        imageUri
                    )

                    val file = File(context!!.filesDir, "video_" + Math.round(Math.random() * 1000) + ".mp4")
                    val fileUri = Uri.fromFile(file)
                    try {
                        val fOut = FileOutputStream(fileUri.path)
                        fOut.write(videoStream?.readBytes())


                        fOut.flush()
                        fOut.close()

                        model.proposedSolutionVideo = file
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }



                    imageUri.path?.let { noturipath ->
                        val bitmap = ThumbnailUtils.createVideoThumbnail(
                            noturipath,
                            MediaStore.Video.Thumbnails.MICRO_KIND
                        )

                        ivVideo.setImageBitmap(bitmap)
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getFile(imageBitmap: Bitmap?): File? {
        var outputFileUri: Uri? = null
        if (imageBitmap != null) {

            val img = File(context!!.filesDir, "image_" + Math.round(Math.random() * 1000) + ".jpg")
            outputFileUri = Uri.fromFile(img)
            try {
                val fOut = FileOutputStream(outputFileUri.getPath())
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 80, fOut)
                fOut.flush()
                fOut.close()

                return img
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return null
    }
}