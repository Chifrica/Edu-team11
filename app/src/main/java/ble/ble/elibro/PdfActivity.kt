package ble.ble.elibro

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

class PdfActivity : AppCompatActivity() {
    var filePath: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)
        val pdfView = findViewById<PDFView>(R.id.pdfView)
        this.filePath = intent.getStringExtra("path")
        val file = filePath?.let { File(it) }
        val path = Uri.fromFile(file)
        pdfView.fromUri(path).load()
    }
}