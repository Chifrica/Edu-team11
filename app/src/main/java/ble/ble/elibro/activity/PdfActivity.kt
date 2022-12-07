package ble.ble.elibro.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ble.ble.elibro.R
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

class PdfActivity : AppCompatActivity() {
    var filePath: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)

        val pdfView = findViewById<PDFView>(R.id.pdfView)

        filePath = intent.getStringExtra("path")

        val file = File(filePath)
        val path = Uri.fromFile(file)

        pdfView.fromUri(path).load()
    }
}