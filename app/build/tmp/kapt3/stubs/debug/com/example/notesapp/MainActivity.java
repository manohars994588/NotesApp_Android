package com.example.notesapp;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002J\u0010\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\rH\u0002J\b\u0010\u001a\u001a\u00020\u0017H\u0002J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\rH\u0002J\u0012\u0010\u001f\u001a\u00020\u00172\b\u0010 \u001a\u0004\u0018\u00010!H\u0014J\b\u0010\"\u001a\u00020\u0017H\u0014J\u0018\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\r2\u0006\u0010&\u001a\u00020\rH\u0002J\u0012\u0010\'\u001a\u00020\u00172\b\b\u0002\u0010(\u001a\u00020\u0004H\u0002J\u0012\u0010)\u001a\u00020\u00172\b\b\u0002\u0010*\u001a\u00020\u0014H\u0002J\b\u0010+\u001a\u00020\u0017H\u0002J\b\u0010,\u001a\u00020\u0017H\u0002J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\rH\u0002J,\u00100\u001a\u00020\u00122\u0006\u00101\u001a\u00020\u00142\u0006\u00102\u001a\u0002032\b\b\u0002\u0010%\u001a\u00020\r2\b\b\u0002\u00104\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00065"}, d2 = {"Lcom/example/notesapp/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "darkMode", "", "editing", "Lcom/example/notesapp/data/Note;", "executor", "Ljava/util/concurrent/ExecutorService;", "kotlin.jvm.PlatformType", "notes", "", "palette", "", "root", "Landroid/widget/LinearLayout;", "selectedColor", "button", "Landroid/widget/TextView;", "label", "", "onClick", "Lkotlin/Function0;", "", "dp", "v", "loadNotes", "lp", "Landroid/widget/LinearLayout$LayoutParams;", "w", "h", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "rounded", "Landroid/graphics/drawable/GradientDrawable;", "color", "radius", "setupRoot", "gradient", "showDashboard", "query", "showEditor", "showWelcome", "space", "Landroid/view/View;", "width", "text", "value", "size", "", "bold", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private final java.util.concurrent.ExecutorService executor = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Integer> palette = null;
    private android.widget.LinearLayout root;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.example.notesapp.data.Note> notes;
    @org.jetbrains.annotations.Nullable()
    private com.example.notesapp.data.Note editing;
    private int selectedColor = 0;
    private boolean darkMode = false;
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final int dp(int v) {
        return 0;
    }
    
    private final android.graphics.drawable.GradientDrawable rounded(int color, int radius) {
        return null;
    }
    
    private final android.widget.TextView text(java.lang.String value, float size, int color, boolean bold) {
        return null;
    }
    
    private final android.widget.TextView button(java.lang.String label, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
        return null;
    }
    
    private final void setupRoot(boolean gradient) {
    }
    
    private final void showWelcome() {
    }
    
    private final void loadNotes() {
    }
    
    private final void showDashboard(java.lang.String query) {
    }
    
    private final void showEditor() {
    }
    
    private final android.widget.LinearLayout.LayoutParams lp(int w, int h) {
        return null;
    }
    
    private final android.view.View space(int width) {
        return null;
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
}