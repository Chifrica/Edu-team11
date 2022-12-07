package ble.ble.elibro

import android.Manifest
import android.annotation.SuppressLint
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
import android.view.*
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintSet.Layout
import ble.ble.elibro.service.OnPdfSelectListener
import ble.ble.elibro.adapter.PDFAdapter
import ble.ble.elibro.activity.PdfActivity
import com.github.barteksc.pdfviewer.PDFView
import com.karumi.dexter.listener.PermissionRequest
import java.io.File
import java.util.ArrayList

class MainActivity : AppCompatActivity(),
    OnPdfSelectListener {
    private lateinit var mAdapter: PDFAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var pdfList: MutableList<File>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pdfFile: LinearLayout = findViewById(R.id.linear)
        this.registerForContextMenu(pdfFile)

        runtimePermission()
    }

    //Creating option menu functionality
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       val inflater:  MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //handling options menu item click
        when (item.itemId){

            R.id.about -> Toast.makeText(this,"About Us button is clicked", Toast.LENGTH_LONG).show()
            R.id.rules -> Toast.makeText(this,"Rules button is clicked", Toast.LENGTH_LONG).show()
            R.id.settings -> Toast.makeText(this,"Settings button is clicked", Toast.LENGTH_LONG).show()

        }
        return super.onOptionsItemSelected(item)
    }

    //Calling floating menu functionality
    override fun onCreateContextMenu(menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.floating_context_menu, menu)
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