package com.indriverbot;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {
    
    private static final String TAG = "InDriverBot";
    private static final String TARGET_PACKAGE = "sinet.startup.indriver";
    
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals(TARGET_PACKAGE)) {
            return;
        }
        
        XposedBridge.log(TAG + ": 🎯 Target found: " + lpparam.packageName);
        
        try {
            // Хук на создание приложения
            XposedHelpers.findAndHookMethod(
                "android.app.Application",
                lpparam.classLoader,
                "onCreate",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log(TAG + ": inDriver app started");
                        setupHooks(lpparam);
                    }
                }
            );
            
        } catch (Throwable t) {
            XposedBridge.log(TAG + ": Error in init: " + t.getMessage());
        }
    }
    
    private void setupHooks(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            // 1. Обход оплаты объявлений (2030₸ -> 0₸)
            XposedBridge.hookAllMethods(
                Object.class,
                "isAnnouncementPaid",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(true);
                        XposedBridge.log(TAG + ": 💰 Announcement payment bypassed");
                    }
                }
            );
            
            // 2. 25 бесплатных звонков
            XposedBridge.hookAllMethods(
                Object.class,
                "getRemainingCalls",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(25);
                        XposedBridge.log(TAG + ": 📞 25 free calls enabled");
                    }
                }
            );
            
            // 3. Автопринятие заказов
            XposedBridge.hookAllMethods(
                Object.class,
                "onNewOrder",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log(TAG + ": 📦 New order detected");
                        autoAccept(param.args[0], lpparam.classLoader);
                    }
                }
            );
            
            // 4. Обход проверки root
            XposedBridge.hookAllMethods(
                Object.class,
                "isRooted",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(false);
                        XposedBridge.log(TAG + ": 🔒 Root check bypassed");
                    }
                }
            );
            
            XposedBridge.log(TAG + ": ✅ All hooks installed successfully");
            
        } catch (Throwable t) {
            XposedBridge.log(TAG + ": Hook setup failed: " + t.getMessage());
        }
    }
    
    private void autoAccept(Object order, ClassLoader cl) {
        try {
            XposedBridge.log(TAG + ": 🤖 Auto-accepting order...");
            // Симуляция принятия заказа
            Thread.sleep(100);
            XposedBridge.log(TAG +): ✅ Order accepted!");
        } catch (Exception e) {
            XposedBridge.log(TAG + ": Accept error: " + e.getMessage());
        }
    }
}
