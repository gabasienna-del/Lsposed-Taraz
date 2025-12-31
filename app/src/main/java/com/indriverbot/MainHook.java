package com.indriverbot;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {
    
    private static final String TAG = "InDriverBot";
    private static final String TARGET_PACKAGE = "sinet.startup.indriver";
    private static final String TARGET_PACKAGE_ALT = "sinet.startup.inDriver";
    
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        String packageName = lpparam.packageName;
        
        if (!packageName.equals(TARGET_PACKAGE) && !packageName.equals(TARGET_PACKAGE_ALT)) {
            return;
        }
        
        XposedBridge.log(TAG + ": 🎯 Hooking package: " + packageName);
        
        try {
            XposedHelpers.findAndHookMethod(
                "android.app.Application",
                lpparam.classLoader,
                "onCreate",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log(TAG + ": Application onCreate called");
                        setupHooks(lpparam);
                    }
                }
            );
            
        } catch (Throwable t) {
            XposedBridge.log(TAG + ": Initial hook error: " + t.getMessage());
        }
    }
    
    private void setupHooks(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            // 1. Bypass announcement payment (2030₸ -> 0₸)
            XposedBridge.hookAllMethods(
                Object.class,
                "isAnnouncementPaid",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(true);
                        XposedBridge.log(TAG + ": ✅ Announcement payment bypassed");
                    }
                }
            );
            
            // 2. Free calls (25 calls/24h)
            XposedBridge.hookAllMethods(
                Object.class,
                "getRemainingCalls",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(25);
                        XposedBridge.log(TAG + ": ✅ 25 free calls enabled");
                    }
                }
            );
            
            // 3. Auto-accept orders
            XposedBridge.hookAllMethods(
                Object.class,
                "onNewOrder",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log(TAG + ": 📦 New order detected");
                        autoAcceptOrder(param.args[0], lpparam.classLoader);
                    }
                }
            );
            
            // 4. Bypass security checks
            String[] securityChecks = {"isRooted", "isEmulator", "isXposedInstalled"};
            for (String check : securityChecks) {
                XposedBridge.hookAllMethods(
                    Object.class,
                    check,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            param.setResult(false);
                            XposedBridge.log(TAG + ": ✅ " + check + " bypassed");
                        }
                    }
                );
            }
            
            XposedBridge.log(TAG + ": ✅ All hooks set up successfully");
            
        } catch (Throwable t) {
            XposedBridge.log(TAG + ": ❌ Hook setup failed: " + t.getMessage());
        }
    }
    
    private void autoAcceptOrder(Object order, ClassLoader cl) {
        try {
            XposedBridge.log(TAG + ": 🤖 Auto-accepting order...");
            Thread.sleep(100);
            XposedBridge.log(TAG + ": ✅ Order accepted (simulated)");
        } catch (Exception e) {
            XposedBridge.log(TAG + ": Accept error: " + e.getMessage());
        }
    }
}
