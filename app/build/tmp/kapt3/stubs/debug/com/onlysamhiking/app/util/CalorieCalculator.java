package com.onlysamhiking.app.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00042\b\b\u0002\u0010\r\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/onlysamhiking/app/util/CalorieCalculator;", "", "()V", "DEFAULT_WEIGHT_KG", "", "MET_HIKING_GENERAL", "MET_HIKING_STEEP", "calculateCalories", "", "durationMillis", "", "elevationGain", "distanceMeters", "weightKg", "app_debug"})
public final class CalorieCalculator {
    private static final double MET_HIKING_GENERAL = 6.0;
    private static final double MET_HIKING_STEEP = 8.0;
    private static final double DEFAULT_WEIGHT_KG = 70.0;
    @org.jetbrains.annotations.NotNull()
    public static final com.onlysamhiking.app.util.CalorieCalculator INSTANCE = null;
    
    private CalorieCalculator() {
        super();
    }
    
    public final int calculateCalories(long durationMillis, double elevationGain, double distanceMeters, double weightKg) {
        return 0;
    }
}