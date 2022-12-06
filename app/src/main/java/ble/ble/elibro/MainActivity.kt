package ble.ble.elibro

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.single.PermissionListener
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.PermissionToken
import androidx.recyclerview.widget.GridLayoutManager
import android.os.Environment
import android.content.Intent
import ble.ble.elibro.service.OnPdfSelectListener
import ble.ble.elibro.adapter.PDFAdapter
import ble.ble.elibro.activity.PdfActivity
import com.karumi.dexter.listener.PermissionRequest
import java.io.File
import java.util.ArrayList

class MainActivity : AppCompatActivity(),
    OnPdfSelectListener {
    private lateinit var mAdapter: PDFAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var pdfList: MutableList<File>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runtimePermission()
    }

    private fun runtimePermission() {
        Dexter.withContext(this@MainActivity)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    display()
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {}
                override fun onPermissionRationaleShouldBeShown(
                    permissionRequest: PermissionRequest,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).check()
    }

    fun findPdf(file: File): ArrayList<File> {
        val arrayList = ArrayList<File>()
        val files = file.listFiles()

        for (singleFiles in files) {
            if (singleFiles.isDirectory && !singleFiles.isHidden) {
                arrayList.addAll(findPdf(singleFiles))
            } else {
                if (singleFiles.name.endsWith(".pdf")) {
                    arrayList.add(singleFiles)
                }
            }
        }
        return arrayList
    }

    fun display() {
        mRecyclerView = findViewById(R.id.rv)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(this, 1)

        pdfList = ArrayList()
        pdfList.addAll(findPdf(Environment.getExternalStorageDirectory()))

        mAdapter = PDFAdapter(this, pdfList, this)
        mRecyclerView.adapter = mAdapter
    }

    override fun onPdfSelected(file: File) {
        startActivity(
            Intent(this, PdfActivity::class.java)
                .putExtra("path", file.absolutePath)
        )
    }
}