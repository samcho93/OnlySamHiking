package com.onlysamhiking.app.ui.history;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\fH\u0002J\u0012\u0010\u0018\u001a\u00020\u00162\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\b\u0010\u001b\u001a\u00020\u0016H\u0014J\u0010\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\nH\u0002J\b\u0010\u001e\u001a\u00020\u0016H\u0002J\u0010\u0010\u001f\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\nH\u0002J\u0016\u0010 \u001a\u00020\u00162\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0010\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u000f0\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/onlysamhiking/app/ui/history/HistoryActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/onlysamhiking/app/ui/history/HistoryAdapter;", "binding", "Lcom/onlysamhiking/app/databinding/ActivityHistoryBinding;", "currentObserver", "Landroidx/lifecycle/LiveData;", "", "Lcom/onlysamhiking/app/data/model/HikingRecord;", "currentTab", "", "importLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "repository", "Lcom/onlysamhiking/app/data/repository/HikingRepository;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "observeRecords", "", "tabIndex", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "openDetail", "record", "setupTabs", "showDeleteDialog", "updateStatsHeader", "records", "Companion", "app_debug"})
public final class HistoryActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.onlysamhiking.app.databinding.ActivityHistoryBinding binding;
    private com.onlysamhiking.app.ui.history.HistoryAdapter adapter;
    private com.onlysamhiking.app.data.repository.HikingRepository repository;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    private int currentTab = 0;
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.LiveData<java.util.List<com.onlysamhiking.app.data.model.HikingRecord>> currentObserver;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> importLauncher = null;
    private static final int TAB_APP_RECORDS = 0;
    private static final int TAB_USER_RECORDS = 1;
    @org.jetbrains.annotations.NotNull()
    public static final com.onlysamhiking.app.ui.history.HistoryActivity.Companion Companion = null;
    
    public HistoryActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupTabs() {
    }
    
    private final void observeRecords(int tabIndex) {
    }
    
    private final void updateStatsHeader(java.util.List<com.onlysamhiking.app.data.model.HikingRecord> records) {
    }
    
    private final void openDetail(com.onlysamhiking.app.data.model.HikingRecord record) {
    }
    
    private final void showDeleteDialog(com.onlysamhiking.app.data.model.HikingRecord record) {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/onlysamhiking/app/ui/history/HistoryActivity$Companion;", "", "()V", "TAB_APP_RECORDS", "", "TAB_USER_RECORDS", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}