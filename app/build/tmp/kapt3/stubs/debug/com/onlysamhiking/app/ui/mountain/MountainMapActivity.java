package com.onlysamhiking.app.ui.mountain;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002:\u0001\u001bB\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\u0012\u0010\u0013\u001a\u00020\u00122\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0012H\u0014J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\fH\u0016J\b\u0010\u0019\u001a\u00020\u0012H\u0014J\b\u0010\u001a\u001a\u00020\u0012H\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/onlysamhiking/app/ui/mountain/MountainMapActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/naver/maps/map/OnMapReadyCallback;", "()V", "binding", "Lcom/onlysamhiking/app/databinding/ActivityMountainMapBinding;", "mapView", "Lcom/naver/maps/map/MapView;", "markers", "", "Lcom/naver/maps/map/overlay/Marker;", "naverMap", "Lcom/naver/maps/map/NaverMap;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "createMountainMarkerBitmap", "Landroid/graphics/Bitmap;", "loadVisitedMountains", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onMapReady", "map", "onPause", "onResume", "VisitedMountainAdapter", "app_debug"})
public final class MountainMapActivity extends androidx.appcompat.app.AppCompatActivity implements com.naver.maps.map.OnMapReadyCallback {
    private com.onlysamhiking.app.databinding.ActivityMountainMapBinding binding;
    @org.jetbrains.annotations.Nullable()
    private com.naver.maps.map.NaverMap naverMap;
    @org.jetbrains.annotations.Nullable()
    private com.naver.maps.map.MapView mapView;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.naver.maps.map.overlay.Marker> markers = null;
    
    public MountainMapActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onMapReady(@org.jetbrains.annotations.NotNull()
    com.naver.maps.map.NaverMap map) {
    }
    
    private final void loadVisitedMountains() {
    }
    
    private final android.graphics.Bitmap createMountainMarkerBitmap() {
        return null;
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u0010\u0012\f\u0012\n0\u0002R\u00060\u0000R\u00020\u00030\u0001:\u0001\u0014B\'\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016J \u0010\r\u001a\u00020\t2\u000e\u0010\u000e\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u000f\u001a\u00020\fH\u0016J \u0010\u0010\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\fH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/onlysamhiking/app/ui/mountain/MountainMapActivity$VisitedMountainAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/onlysamhiking/app/ui/mountain/MountainMapActivity$VisitedMountainAdapter$VH;", "Lcom/onlysamhiking/app/ui/mountain/MountainMapActivity;", "items", "", "Lcom/onlysamhiking/app/ui/mountain/VisitedMountain;", "onClick", "Lkotlin/Function1;", "", "(Lcom/onlysamhiking/app/ui/mountain/MountainMapActivity;Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "VH", "app_debug"})
    final class VisitedMountainAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.onlysamhiking.app.ui.mountain.MountainMapActivity.VisitedMountainAdapter.VH> {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.onlysamhiking.app.ui.mountain.VisitedMountain> items = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.jvm.functions.Function1<com.onlysamhiking.app.ui.mountain.VisitedMountain, kotlin.Unit> onClick = null;
        
        public VisitedMountainAdapter(@org.jetbrains.annotations.NotNull()
        java.util.List<com.onlysamhiking.app.ui.mountain.VisitedMountain> items, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.onlysamhiking.app.ui.mountain.VisitedMountain, kotlin.Unit> onClick) {
            super();
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.onlysamhiking.app.ui.mountain.MountainMapActivity.VisitedMountainAdapter.VH onCreateViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.ViewGroup parent, int viewType) {
            return null;
        }
        
        @java.lang.Override()
        public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
        com.onlysamhiking.app.ui.mountain.MountainMapActivity.VisitedMountainAdapter.VH holder, int position) {
        }
        
        @java.lang.Override()
        public int getItemCount() {
            return 0;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/onlysamhiking/app/ui/mountain/MountainMapActivity$VisitedMountainAdapter$VH;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/onlysamhiking/app/databinding/ItemVisitedMountainBinding;", "(Lcom/onlysamhiking/app/ui/mountain/MountainMapActivity$VisitedMountainAdapter;Lcom/onlysamhiking/app/databinding/ItemVisitedMountainBinding;)V", "getBinding", "()Lcom/onlysamhiking/app/databinding/ItemVisitedMountainBinding;", "app_debug"})
        public final class VH extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            @org.jetbrains.annotations.NotNull()
            private final com.onlysamhiking.app.databinding.ItemVisitedMountainBinding binding = null;
            
            public VH(@org.jetbrains.annotations.NotNull()
            com.onlysamhiking.app.databinding.ItemVisitedMountainBinding binding) {
                super(null);
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.onlysamhiking.app.databinding.ItemVisitedMountainBinding getBinding() {
                return null;
            }
        }
    }
}