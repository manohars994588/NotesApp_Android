package com.example.notesapp

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import com.example.notesapp.data.Note
import com.example.notesapp.data.NotesDatabase
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val executor = Executors.newSingleThreadExecutor()
    private val palette = listOf(0xFFE5F3FF.toInt(), 0xFFFFF0D8.toInt(), 0xFFE8F5E9.toInt(), 0xFFFCE4EC.toInt(), 0xFFF0E8FF.toInt())
    private lateinit var root: LinearLayout
    private var notes: List<Note> = emptyList()
    private var editing: Note? = null
    private var selectedColor = 0
    private var darkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showWelcome()
    }

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
    private fun rounded(color: Int, radius: Int): GradientDrawable =
        GradientDrawable().apply { setColor(color); cornerRadius = dp(radius).toFloat() }

    private fun text(value: String, size: Float, color: Int = Color.BLACK, bold: Boolean = false): TextView =
        TextView(this).apply {
            text = value; textSize = size; setTextColor(color)
            if (bold) setTypeface(typeface, Typeface.BOLD)
        }

    private fun button(label: String, onClick: () -> Unit): TextView =
        text(label, 16f, Color.WHITE, true).apply {
            gravity = Gravity.CENTER
            background = rounded(Color.BLACK, 28)
            setPadding(dp(18), dp(12), dp(18), dp(12))
            setOnClickListener { onClick() }
        }

    private fun setupRoot(gradient: Boolean = false) {
        root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22))
            background = if (gradient) getDrawable(R.drawable.bg_gradient) else rounded(if (darkMode) 0xFF17191D.toInt() else Color.WHITE, 0)
        }
        setContentView(root)
    }

    private fun showWelcome() {
        setupRoot(true)
        root.gravity = Gravity.CENTER
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            background = getDrawable(R.drawable.bg_white_card)
            setPadding(dp(24))
        }
        val art = text("📝", 76f)
        val heading = text("Make your best notes", 23f, 0xFF17191D.toInt(), true)
        val sub = text("Create and keep all records of your daily activities with us", 14f, 0xFF777777.toInt()).apply { gravity = Gravity.CENTER }
        val create = text("Create notes", 16f, Color.WHITE, true).apply {
            gravity = Gravity.CENTER
            background = getDrawable(R.drawable.bg_primary_button)
            setPadding(dp(34), dp(14), dp(34), dp(14))
            setOnClickListener { loadNotes(); showDashboard() }
        }
        card.addView(art, lp(-1, dp(190)))
        card.addView(heading, lp(-1, dp(48)))
        card.addView(sub, lp(-1, dp(64)))
        card.addView(create, lp(-1, dp(52)))
        root.addView(card, LinearLayout.LayoutParams(-1, dp(500)))
    }

    private fun loadNotes() {
        executor.execute {
            notes = NotesDatabase.get(this).noteDao().getAll()
            runOnUiThread { if (root.childCount > 0) showDashboard() }
        }
    }

    private fun showDashboard(query: String = "") {
        setupRoot(true)
        val panel = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = getDrawable(R.drawable.bg_white_card)
            setPadding(dp(16))
        }
        val top = LinearLayout(this).apply { gravity = Gravity.CENTER_VERTICAL; orientation = LinearLayout.HORIZONTAL }
        top.addView(text("Notes", 25f, 0xFF17191D.toInt(), true), LinearLayout.LayoutParams(0, dp(48), 1f))
        top.addView(text("●", 22f, 0xFF42A5B5.toInt(), true))
        panel.addView(top)
        val search = EditText(this).apply {
            hint = "⌕  Search notes"
            textSize = 14f
            setSingleLine(true)
            background = getDrawable(R.drawable.bg_search)
            setPadding(dp(16), dp(4), dp(16), dp(4))
            setText(query)
        }
        panel.addView(search, lp(-1, dp(46)))
        val scroll = ScrollView(this)
        val list = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        scroll.addView(list)
        panel.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        val actions = LinearLayout(this).apply { gravity = Gravity.CENTER_VERTICAL; orientation = LinearLayout.HORIZONTAL }
        actions.addView(button("＋", { editing = null; selectedColor = 0; showEditor() }), LinearLayout.LayoutParams(0, dp(50), 1f))
        actions.addView(space(10))
        actions.addView(button("☾", { darkMode = !darkMode; showDashboard(query) }), LinearLayout.LayoutParams(0, dp(50), 1f))
        panel.addView(actions)
        root.addView(panel, LinearLayout.LayoutParams(-1, -1))

        fun render(filter: String) {
            list.removeAllViews()
            val filtered = notes.filter { it.title.contains(filter, true) || it.body.contains(filter, true) }
            if (filtered.isEmpty()) list.addView(text(if (filter.isBlank()) "No notes yet. Tap + to create your first note." else "No matching notes found.", 14f, 0xFF777777.toInt()).apply { setPadding(8) })
            filtered.forEach { note ->
                val card = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    background = rounded(palette[note.color.coerceIn(palette.indices)], 18)
                    setPadding(dp(14))
                    setOnClickListener { editing = note; selectedColor = note.color; showEditor() }
                }
                val titleRow = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
                titleRow.addView(text(note.title.ifBlank { "Untitled note" }, 16f, 0xFF202124.toInt(), true), LinearLayout.LayoutParams(0, dp(30), 1f))
                titleRow.addView(text(if (note.pinned) "📌" else "", 14f))
                card.addView(titleRow)
                card.addView(text(note.body, 13f, 0xFF454545.toInt()).apply { maxLines = 4 })
                card.addView(text(SimpleDateFormat("dd/MM/yyyy  •  hh:mm a", Locale.getDefault()).format(Date(note.updatedAt)), 10f, 0xFF777777.toInt()))
                list.addView(card, lp(-1, -2).apply { bottomMargin = dp(10) })
            }
        }
        render(query)
        search.addTextChangedListener(SimpleWatcher { render(it) })
    }

    private fun showEditor() {
        setupRoot(true)
        val panel = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = getDrawable(R.drawable.bg_white_card)
            setPadding(dp(18))
        }
        val bar = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        bar.addView(text("‹", 32f, 0xFF555555.toInt()).apply { setOnClickListener { loadNotes(); showDashboard() } }, LinearLayout.LayoutParams(dp(42), dp(48)))
        bar.addView(text(if (editing == null) "New note" else "Edit note", 18f, 0xFF222222.toInt(), true), LinearLayout.LayoutParams(0, dp(48), 1f))
        val pin = CheckBox(this).apply { text = "Pin"; isChecked = editing?.pinned ?: false }
        bar.addView(pin)
        panel.addView(bar)
        val title = EditText(this).apply {
            hint = "Title"; textSize = 21f; setTypeface(typeface, Typeface.BOLD)
            setText(editing?.title ?: ""); setSingleLine(true); background = null
        }
        panel.addView(title, lp(-1, dp(58)))
        val body = EditText(this).apply {
            hint = "Start writing your note..."; textSize = 15f
            gravity = Gravity.TOP; setText(editing?.body ?: ""); background = null
        }
        panel.addView(body, LinearLayout.LayoutParams(-1, 0, 1f))
        panel.addView(text("Note color", 13f, 0xFF777777.toInt(), true))
        val colors = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        palette.forEachIndexed { index, color ->
            val swatch = View(this).apply {
                background = GradientDrawable().apply { setColor(color); cornerRadius = dp(24).toFloat(); setStroke(dp(if (selectedColor == index) 3 else 1), if (selectedColor == index) 0xFF168FC5.toInt() else 0xFFCCCCCC.toInt()) }
                setOnClickListener { selectedColor = index; showEditor() }
            }
            colors.addView(swatch, LinearLayout.LayoutParams(dp(34), dp(34)).apply { marginEnd = dp(12); topMargin = dp(8); bottomMargin = dp(12) })
        }
        panel.addView(colors)
        val bottom = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        bottom.addView(button("Delete", {
            editing?.let { old -> executor.execute { NotesDatabase.get(this@MainActivity).noteDao().delete(old); runOnUiThread { loadNotes(); showDashboard() } } } ?: run { loadNotes(); showDashboard() }
        }).apply { background = rounded(0xFF777777.toInt(), 24) }, LinearLayout.LayoutParams(0, dp(48), 1f))
        bottom.addView(space(10))
        bottom.addView(button("Save", {
            val old = editing
            val note = Note(id = old?.id ?: 0, title = title.text.toString().trim(), body = body.text.toString(), color = selectedColor, pinned = pin.isChecked, updatedAt = System.currentTimeMillis())
            executor.execute {
                val dao = NotesDatabase.get(this@MainActivity).noteDao()
                if (old == null) dao.insert(note) else dao.update(note)
                notes = dao.getAll()
                runOnUiThread { showDashboard() }
            }
        }).apply { background = getDrawable(R.drawable.bg_primary_button) }, LinearLayout.LayoutParams(0, dp(48), 1f))
        panel.addView(bottom)
        root.addView(panel, LinearLayout.LayoutParams(-1, -1))
    }

    private fun lp(w: Int, h: Int) = LinearLayout.LayoutParams(w, h)
    private fun space(width: Int) = View(this).apply { layoutParams = lp(dp(width), 1) }

    override fun onDestroy() {
        super.onDestroy()
        executor.shutdown()
    }
}

private class SimpleWatcher(val changed: (String) -> Unit) : android.text.TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { changed(s?.toString().orEmpty()) }
    override fun afterTextChanged(s: android.text.Editable?) {}
}
