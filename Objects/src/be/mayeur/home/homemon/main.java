package be.mayeur.home.homemon;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "be.mayeur.home.homemon", "be.mayeur.home.homemon.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, true))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "be.mayeur.home.homemon", "be.mayeur.home.homemon.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "be.mayeur.home.homemon.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        Object[] o;
        if (permissions.length > 0)
            o = new Object[] {permissions[0], grantResults[0] == 0};
        else
            o = new Object[] {"", false};
        processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.SocketWrapper.ServerSocketWrapper _vvv2 = null;
public static com.AB.ABWifi.ABWifi _vvv3 = null;
public static boolean _vvv4 = false;
public static String _vvvvvvv3 = "";
public anywheresoftware.b4a.objects.TabStripViewPager _tabstrip1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncancel = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnok = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtport = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtserver = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtuser = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnspam = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtresult = null;
public static String _vvvvvvv4 = "";
public anywheresoftware.b4a.objects.ButtonWrapper _btnstart = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnstop = null;
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnloadflcam = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnloadgfl = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtcam1port = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtcam2port = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnclear = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _rpi_dac = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _rpi_spdif = null;
public static boolean _vvvvvvv5 = false;
public static boolean _vvvvvvv6 = false;
public static String _vvvvvvv2 = "";
public static String _vvvvvvv1 = "";
public anywheresoftware.b4a.objects.LabelWrapper _lblipaddress = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblssid = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txthomessid = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtlocalserverip = null;
public be.mayeur.home.homemon.httputils2service _vvvvvv7 = null;
public be.mayeur.home.homemon.statemanager _vvvvvv0 = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 70;BA.debugLine="Activity.LoadLayout(\"Main\")";
mostCurrent._activity.LoadLayout("Main",mostCurrent.activityBA);
 //BA.debugLineNum = 71;BA.debugLine="TabStrip1.LoadLayout(\"Page1\", \"Devices\")";
mostCurrent._tabstrip1.LoadLayout("Page1",(java.lang.CharSequence)("Devices"));
 //BA.debugLineNum = 72;BA.debugLine="TabStrip1.LoadLayout(\"Page2\", \"Spam Mon\")";
mostCurrent._tabstrip1.LoadLayout("Page2",(java.lang.CharSequence)("Spam Mon"));
 //BA.debugLineNum = 73;BA.debugLine="TabStrip1.LoadLayout(\"Page3\", \"Floor Cam\")";
mostCurrent._tabstrip1.LoadLayout("Page3",(java.lang.CharSequence)("Floor Cam"));
 //BA.debugLineNum = 74;BA.debugLine="TabStrip1.LoadLayout(\"Param\", \"Settings\")";
mostCurrent._tabstrip1.LoadLayout("Param",(java.lang.CharSequence)("Settings"));
 //BA.debugLineNum = 76;BA.debugLine="Activity.AddMenuItem(\"Devices\", \"mnu1\")";
mostCurrent._activity.AddMenuItem((java.lang.CharSequence)("Devices"),"mnu1");
 //BA.debugLineNum = 77;BA.debugLine="Activity.AddMenuItem(\"Spam Monitor\", \"mnu2\")";
mostCurrent._activity.AddMenuItem((java.lang.CharSequence)("Spam Monitor"),"mnu2");
 //BA.debugLineNum = 78;BA.debugLine="Activity.AddMenuItem(\"Floor Cam\", \"mnu3\")";
mostCurrent._activity.AddMenuItem((java.lang.CharSequence)("Floor Cam"),"mnu3");
 //BA.debugLineNum = 79;BA.debugLine="Activity.AddMenuItem(\"Settings\", \"mnuSettings\")";
mostCurrent._activity.AddMenuItem((java.lang.CharSequence)("Settings"),"mnuSettings");
 //BA.debugLineNum = 84;BA.debugLine="MyLan.Initialize(0, \"\")";
_vvv2.Initialize(processBA,(int) (0),"");
 //BA.debugLineNum = 86;BA.debugLine="If MyWiFi.ABLoadWifi() Then";
if (_vvv3.ABLoadWifi(processBA)) { 
 //BA.debugLineNum = 87;BA.debugLine="MySSID=MyWiFi.ABGetCurrentWifiInfo.SSID";
mostCurrent._vvvvvvv1 = _vvv3.ABGetCurrentWifiInfo().SSID;
 }else {
 //BA.debugLineNum = 89;BA.debugLine="MySSID=\"\"";
mostCurrent._vvvvvvv1 = "";
 };
 //BA.debugLineNum = 91;BA.debugLine="lblSSID.Text=\"SSID: \" & MySSID";
mostCurrent._lblssid.setText((Object)("SSID: "+mostCurrent._vvvvvvv1));
 //BA.debugLineNum = 92;BA.debugLine="AtHome=MySSID.Contains(StateManager.GetSetting2(\"";
_vvv4 = mostCurrent._vvvvvvv1.contains(mostCurrent._vvvvvv0._vv4(mostCurrent.activityBA,"HomeSSID","CC88"));
 //BA.debugLineNum = 94;BA.debugLine="lblIPAddress.Text=\"IP: \" & MyLan.GetMyIP()";
mostCurrent._lblipaddress.setText((Object)("IP: "+_vvv2.GetMyIP()));
 //BA.debugLineNum = 95;BA.debugLine="If AtHome Then";
if (_vvv4) { 
 //BA.debugLineNum = 96;BA.debugLine="txtLocalServerIP.text=StateManager.GetSetting2(\"";
mostCurrent._txtlocalserverip.setText((Object)(mostCurrent._vvvvvv0._vv4(mostCurrent.activityBA,"LocIP","192.168.0.12")));
 //BA.debugLineNum = 97;BA.debugLine="MyHost=\"https://\" & txtLocalServerIP.text";
mostCurrent._vvvvvvv2 = "https://"+mostCurrent._txtlocalserverip.getText();
 }else {
 //BA.debugLineNum = 99;BA.debugLine="txtServer.Text=StateManager.GetSetting2(\"BaseURL";
mostCurrent._txtserver.setText((Object)(mostCurrent._vvvvvv0._vv4(mostCurrent.activityBA,"BaseURL","https://home.mayeur.be")));
 //BA.debugLineNum = 100;BA.debugLine="MyHost=txtServer.Text";
mostCurrent._vvvvvvv2 = mostCurrent._txtserver.getText();
 };
 //BA.debugLineNum = 103;BA.debugLine="txtPort.Text=StateManager.GetSetting2(\"Port\", \"44";
mostCurrent._txtport.setText((Object)(mostCurrent._vvvvvv0._vv4(mostCurrent.activityBA,"Port","443")));
 //BA.debugLineNum = 104;BA.debugLine="BaseURL=MyHost & \":\" & txtPort.Text";
mostCurrent._vvvvvvv3 = mostCurrent._vvvvvvv2+":"+mostCurrent._txtport.getText();
 //BA.debugLineNum = 107;BA.debugLine="txtUser.Text=StateManager.GetSetting2(\"User\", \"xa";
mostCurrent._txtuser.setText((Object)(mostCurrent._vvvvvv0._vv4(mostCurrent.activityBA,"User","xavier")));
 //BA.debugLineNum = 108;BA.debugLine="txtPassword.Text=StateManager.GetSetting(\"Passwor";
mostCurrent._txtpassword.setText((Object)(mostCurrent._vvvvvv0._vv3(mostCurrent.activityBA,"Password")));
 //BA.debugLineNum = 109;BA.debugLine="If txtPassword.Text=\"\" Then";
if ((mostCurrent._txtpassword.getText()).equals("")) { 
 //BA.debugLineNum = 110;BA.debugLine="TabStrip1.ScrollTo(2, True)";
mostCurrent._tabstrip1.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 114;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"kodi";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 //BA.debugLineNum = 115;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"kodiGr";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 //BA.debugLineNum = 118;BA.debugLine="txtCAM1port.text=StateManager.GetSetting2(\"CAM1po";
mostCurrent._txtcam1port.setText((Object)(mostCurrent._vvvvvv0._vv4(mostCurrent.activityBA,"CAM1port","8084")));
 //BA.debugLineNum = 119;BA.debugLine="txtCAM2port.text=StateManager.GetSetting2(\"CAM2po";
mostCurrent._txtcam2port.setText((Object)(mostCurrent._vvvvvv0._vv4(mostCurrent.activityBA,"CAM2port","8083")));
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 128;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 123;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 124;BA.debugLine="Probe_rPI(\"rPI_SPDIF\")";
_probe_rpi("rPI_SPDIF");
 //BA.debugLineNum = 125;BA.debugLine="Probe_rPI(\"rPI_DAC\")";
_probe_rpi("rPI_DAC");
 //BA.debugLineNum = 126;BA.debugLine="End Sub";
return "";
}
public static String  _btncancel_click() throws Exception{
 //BA.debugLineNum = 258;BA.debugLine="Sub btnCancel_Click";
 //BA.debugLineNum = 260;BA.debugLine="End Sub";
return "";
}
public static String  _btnclear_click() throws Exception{
 //BA.debugLineNum = 308;BA.debugLine="Sub btnClear_Click";
 //BA.debugLineNum = 309;BA.debugLine="WebView1.LoadHtml(\"<html><body>Press a button</bo";
mostCurrent._webview1.LoadHtml("<html><body>Press a button</body></html>");
 //BA.debugLineNum = 310;BA.debugLine="End Sub";
return "";
}
public static String  _btnloadflcam_click() throws Exception{
 //BA.debugLineNum = 299;BA.debugLine="Sub btnLoadFlCam_Click";
 //BA.debugLineNum = 300;BA.debugLine="WebView1.LoadUrl(MyHost.Replace(\"https\", \"http\")";
mostCurrent._webview1.LoadUrl(mostCurrent._vvvvvvv2.replace("https","http")+":"+mostCurrent._txtcam2port.getText());
 //BA.debugLineNum = 301;BA.debugLine="End Sub";
return "";
}
public static String  _btnloadgfl_click() throws Exception{
 //BA.debugLineNum = 303;BA.debugLine="Sub btnLoadGFl_Click";
 //BA.debugLineNum = 304;BA.debugLine="WebView1.LoadUrl(MyHost.Replace(\"https\", \"http\")";
mostCurrent._webview1.LoadUrl(mostCurrent._vvvvvvv2.replace("https","http")+":"+mostCurrent._txtcam1port.getText());
 //BA.debugLineNum = 305;BA.debugLine="End Sub";
return "";
}
public static String  _btnok_click() throws Exception{
 //BA.debugLineNum = 238;BA.debugLine="Sub btnOK_Click";
 //BA.debugLineNum = 239;BA.debugLine="StateManager.SetSetting(\"BaseURL\", txtServer.Text";
mostCurrent._vvvvvv0._vvv1(mostCurrent.activityBA,"BaseURL",mostCurrent._txtserver.getText());
 //BA.debugLineNum = 240;BA.debugLine="StateManager.SetSetting(\"Port\",txtPort.Text)";
mostCurrent._vvvvvv0._vvv1(mostCurrent.activityBA,"Port",mostCurrent._txtport.getText());
 //BA.debugLineNum = 242;BA.debugLine="StateManager.SetSetting(\"User\", txtUser.Text)";
mostCurrent._vvvvvv0._vvv1(mostCurrent.activityBA,"User",mostCurrent._txtuser.getText());
 //BA.debugLineNum = 243;BA.debugLine="StateManager.SetSetting(\"Password\", txtPassword.T";
mostCurrent._vvvvvv0._vvv1(mostCurrent.activityBA,"Password",mostCurrent._txtpassword.getText());
 //BA.debugLineNum = 245;BA.debugLine="StateManager.SetSetting(\"CAM1port\", txtCAM1port.T";
mostCurrent._vvvvvv0._vvv1(mostCurrent.activityBA,"CAM1port",mostCurrent._txtcam1port.getText());
 //BA.debugLineNum = 246;BA.debugLine="StateManager.SetSetting(\"CAM2port\", txtCAM2port.T";
mostCurrent._vvvvvv0._vvv1(mostCurrent.activityBA,"CAM2port",mostCurrent._txtcam2port.getText());
 //BA.debugLineNum = 248;BA.debugLine="StateManager.SetSetting(\"HomeSSID\", txtHomeSSID.T";
mostCurrent._vvvvvv0._vvv1(mostCurrent.activityBA,"HomeSSID",mostCurrent._txthomessid.getText());
 //BA.debugLineNum = 249;BA.debugLine="StateManager.SetSetting(\"LocIP\", txtLocalServerIP";
mostCurrent._vvvvvv0._vvv1(mostCurrent.activityBA,"LocIP",mostCurrent._txtlocalserverip.getText());
 //BA.debugLineNum = 251;BA.debugLine="StateManager.SaveSettings";
mostCurrent._vvvvvv0._vv7(mostCurrent.activityBA);
 //BA.debugLineNum = 252;BA.debugLine="If AtHome = False Then";
if (_vvv4==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 253;BA.debugLine="BaseURL=txtServer.Text & \":\" & txtPort.Text";
mostCurrent._vvvvvvv3 = mostCurrent._txtserver.getText()+":"+mostCurrent._txtport.getText();
 };
 //BA.debugLineNum = 255;BA.debugLine="TabStrip1.ScrollTo(0, True)";
mostCurrent._tabstrip1.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
return "";
}
public static String  _btnspam_click() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 262;BA.debugLine="Sub btnSpam_Click";
 //BA.debugLineNum = 263;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 264;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv4 = "";
 //BA.debugLineNum = 266;BA.debugLine="url = BaseURL & \"/api?cmd=spam\"";
mostCurrent._vvvvvvv4 = mostCurrent._vvvvvvv3+"/api?cmd=spam";
 //BA.debugLineNum = 267;BA.debugLine="Job.Initialize(\"spam\", Me)";
_job._initialize(processBA,"spam",main.getObject());
 //BA.debugLineNum = 268;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv7 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 269;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvv0 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 270;BA.debugLine="Job.Download( url)";
_job._vvv6(mostCurrent._vvvvvvv4);
 //BA.debugLineNum = 271;BA.debugLine="End Sub";
return "";
}
public static String  _btnstart_click() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 273;BA.debugLine="Sub btnStart_Click";
 //BA.debugLineNum = 274;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 275;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv4 = "";
 //BA.debugLineNum = 277;BA.debugLine="url = BaseURL & \"/api?cmd=spam&param=start\"";
mostCurrent._vvvvvvv4 = mostCurrent._vvvvvvv3+"/api?cmd=spam&param=start";
 //BA.debugLineNum = 278;BA.debugLine="Job.Initialize(\"spamStart\", Me)";
_job._initialize(processBA,"spamStart",main.getObject());
 //BA.debugLineNum = 279;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv7 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 280;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvv0 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 281;BA.debugLine="Job.Download( url)";
_job._vvv6(mostCurrent._vvvvvvv4);
 //BA.debugLineNum = 282;BA.debugLine="End Sub";
return "";
}
public static String  _btnstop_click() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 284;BA.debugLine="Sub btnSTop_Click";
 //BA.debugLineNum = 285;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 286;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv4 = "";
 //BA.debugLineNum = 288;BA.debugLine="url = BaseURL & \"/api?cmd=spam&param=stop\"";
mostCurrent._vvvvvvv4 = mostCurrent._vvvvvvv3+"/api?cmd=spam&param=stop";
 //BA.debugLineNum = 289;BA.debugLine="Job.Initialize(\"spamStop\", Me)";
_job._initialize(processBA,"spamStop",main.getObject());
 //BA.debugLineNum = 290;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv7 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 291;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvv0 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 292;BA.debugLine="Job.Download( url)";
_job._vvv6(mostCurrent._vvvvvvv4);
 //BA.debugLineNum = 293;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 23;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 27;BA.debugLine="Private BaseURL As String";
mostCurrent._vvvvvvv3 = "";
 //BA.debugLineNum = 28;BA.debugLine="Private TabStrip1 As TabStrip";
mostCurrent._tabstrip1 = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 30;BA.debugLine="Private btnCancel As Button";
mostCurrent._btncancel = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private btnOK As Button";
mostCurrent._btnok = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private txtPassword As EditText";
mostCurrent._txtpassword = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private txtPort As EditText";
mostCurrent._txtport = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private txtServer As EditText";
mostCurrent._txtserver = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private txtUser As EditText";
mostCurrent._txtuser = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private btnSpam As Button";
mostCurrent._btnspam = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private txtResult As EditText";
mostCurrent._txtresult = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv4 = "";
 //BA.debugLineNum = 41;BA.debugLine="Private btnStart As Button";
mostCurrent._btnstart = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private btnSTop As Button";
mostCurrent._btnstop = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private btnLoadFlCam As Button";
mostCurrent._btnloadflcam = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private btnLoadGFl As Button";
mostCurrent._btnloadgfl = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private txtCAM1port As EditText";
mostCurrent._txtcam1port = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private txtCAM2port As EditText";
mostCurrent._txtcam2port = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private btnClear As Button";
mostCurrent._btnclear = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private rPI_DAC As ImageView";
mostCurrent._rpi_dac = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private rPI_SPDIF As ImageView";
mostCurrent._rpi_spdif = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private checkedSPDIF As Boolean";
_vvvvvvv5 = false;
 //BA.debugLineNum = 56;BA.debugLine="Private checkedDAC As Boolean";
_vvvvvvv6 = false;
 //BA.debugLineNum = 58;BA.debugLine="Private MyHost As String";
mostCurrent._vvvvvvv2 = "";
 //BA.debugLineNum = 59;BA.debugLine="Private MySSID As String";
mostCurrent._vvvvvvv1 = "";
 //BA.debugLineNum = 61;BA.debugLine="Private lblIPAddress As Label";
mostCurrent._lblipaddress = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private lblSSID As Label";
mostCurrent._lblssid = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private txtHomeSSID As EditText";
mostCurrent._txthomessid = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private txtLocalServerIP As EditText";
mostCurrent._txtlocalserverip = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(be.mayeur.home.homemon.httpjob _job) throws Exception{
 //BA.debugLineNum = 153;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 155;BA.debugLine="Log(\"JobName = \" & Job.JobName & \", Success = \" &";
anywheresoftware.b4a.keywords.Common.Log("JobName = "+_job._vvvvv5+", Success = "+BA.ObjectToString(_job._vvvvv6));
 //BA.debugLineNum = 156;BA.debugLine="If Job.Success = True Then";
if (_job._vvvvv6==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 157;BA.debugLine="Log(Job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._vvvv4());
 //BA.debugLineNum = 159;BA.debugLine="Select Job.JobName";
switch (BA.switchObjectToInt(_job._vvvvv5,"Login","rPIDAC","rPISPDIF","prPI_SPDIF","prPI_DAC","spam","spamStart","spamStop")) {
case 0: {
 //BA.debugLineNum = 161;BA.debugLine="Log(\"Login: \" & Job.GetString)";
anywheresoftware.b4a.keywords.Common.Log("Login: "+_job._vvvv4());
 break; }
case 1: {
 //BA.debugLineNum = 163;BA.debugLine="rPI_DAC.Enabled=True";
mostCurrent._rpi_dac.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 164;BA.debugLine="If Job.GetString = \"Kodi On\" Then";
if ((_job._vvvv4()).equals("Kodi On")) { 
 //BA.debugLineNum = 165;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"ko";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGreen.png").getObject()));
 }else if((_job._vvvv4()).equals("OFF")) { 
 //BA.debugLineNum = 167;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"ko";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 }else if((_job._vvvv4()).equals("POWERED")) { 
 //BA.debugLineNum = 169;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"ko";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiBlue.png").getObject()));
 }else {
 //BA.debugLineNum = 171;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"ko";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiRed.png").getObject()));
 };
 break; }
case 2: {
 //BA.debugLineNum = 174;BA.debugLine="If Job.GetString = \"Kodi On\" Then";
if ((_job._vvvv4()).equals("Kodi On")) { 
 //BA.debugLineNum = 175;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"k";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGreen.png").getObject()));
 }else if((_job._vvvv4()).equals("OFF")) { 
 //BA.debugLineNum = 177;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"k";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 }else if((_job._vvvv4()).equals("POWERED")) { 
 //BA.debugLineNum = 179;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiBlue.png").getObject()));
 }else {
 //BA.debugLineNum = 181;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"k";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiRed.png").getObject()));
 };
 break; }
case 3: {
 //BA.debugLineNum = 184;BA.debugLine="If Job.GetString = \"OFF\"  Or Job.GetString = \"";
if ((_job._vvvv4()).equals("OFF") || (_job._vvvv4()).equals("success")) { 
 //BA.debugLineNum = 185;BA.debugLine="checkedSPDIF = False";
_vvvvvvv5 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 186;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"k";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 }else if((_job._vvvv4()).equals("Kodi On")) { 
 //BA.debugLineNum = 188;BA.debugLine="checkedSPDIF = True";
_vvvvvvv5 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 189;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"k";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGreen.png").getObject()));
 }else {
 //BA.debugLineNum = 191;BA.debugLine="checkedSPDIF=False";
_vvvvvvv5 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 192;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"k";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiBlue.png").getObject()));
 };
 break; }
case 4: {
 //BA.debugLineNum = 195;BA.debugLine="rPI_DAC.Enabled=True";
mostCurrent._rpi_dac.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 196;BA.debugLine="If Job.GetString = \"OFF\" Or Job.GetString = \"su";
if ((_job._vvvv4()).equals("OFF") || (_job._vvvv4()).equals("success")) { 
 //BA.debugLineNum = 197;BA.debugLine="checkedDAC = False";
_vvvvvvv6 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 198;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"kod";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 }else if((_job._vvvv4()).equals("Kodi On")) { 
 //BA.debugLineNum = 200;BA.debugLine="checkedDAC = True";
_vvvvvvv6 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 201;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"kod";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGreen.png").getObject()));
 }else {
 //BA.debugLineNum = 203;BA.debugLine="checkedDAC=False";
_vvvvvvv6 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 204;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"kod";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiBlue.png").getObject()));
 };
 break; }
case 5: 
case 6: 
case 7: {
 //BA.debugLineNum = 207;BA.debugLine="txtResult.Text= Job.GetString";
mostCurrent._txtresult.setText((Object)(_job._vvvv4()));
 break; }
}
;
 }else {
 //BA.debugLineNum = 211;BA.debugLine="Log(\"error\" & Job.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.Log("error"+_job._vvvvvv1);
 //BA.debugLineNum = 212;BA.debugLine="If Job.JobName.Contains(\"SPIF\") Then";
if (_job._vvvvv5.contains("SPIF")) { 
 //BA.debugLineNum = 213;BA.debugLine="rPI_SPDIF.Enabled=True";
mostCurrent._rpi_spdif.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else if(_job._vvvvv5.contains("DAC")) { 
 //BA.debugLineNum = 215;BA.debugLine="rPI_DAC.Enabled=True";
mostCurrent._rpi_dac.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 220;BA.debugLine="Job.Release";
_job._vvvvv4();
 //BA.debugLineNum = 223;BA.debugLine="End Sub";
return "";
}
public static String  _mnu1_click() throws Exception{
 //BA.debugLineNum = 132;BA.debugLine="Sub mnu1_Click";
 //BA.debugLineNum = 133;BA.debugLine="TabStrip1.ScrollTo(0, True)";
mostCurrent._tabstrip1.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return "";
}
public static String  _mnu2_click() throws Exception{
 //BA.debugLineNum = 136;BA.debugLine="Sub mnu2_Click";
 //BA.debugLineNum = 137;BA.debugLine="TabStrip1.ScrollTo(1, True)";
mostCurrent._tabstrip1.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 138;BA.debugLine="End Sub";
return "";
}
public static String  _mnu3_click() throws Exception{
 //BA.debugLineNum = 140;BA.debugLine="Sub mnu3_Click";
 //BA.debugLineNum = 141;BA.debugLine="TabStrip1.ScrollTo(2, True)";
mostCurrent._tabstrip1.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
public static String  _mnusettings_click() throws Exception{
 //BA.debugLineNum = 144;BA.debugLine="Sub mnuSettings_Click";
 //BA.debugLineNum = 145;BA.debugLine="TabStrip1.ScrollTo(3, True)";
mostCurrent._tabstrip1.ScrollTo((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 146;BA.debugLine="End Sub";
return "";
}
public static String  _probe_rpi(String _id) throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 225;BA.debugLine="Sub Probe_rPI(id As String)";
 //BA.debugLineNum = 226;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 227;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv4 = "";
 //BA.debugLineNum = 229;BA.debugLine="url = BaseURL & \"/kodistate\"";
mostCurrent._vvvvvvv4 = mostCurrent._vvvvvvv3+"/kodistate";
 //BA.debugLineNum = 230;BA.debugLine="Job.Initialize(\"p\" & id, Me)";
_job._initialize(processBA,"p"+_id,main.getObject());
 //BA.debugLineNum = 231;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv7 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 232;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvv0 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 234;BA.debugLine="Job.Download(url & \"?id=\" & id)";
_job._vvv6(mostCurrent._vvvvvvv4+"?id="+_id);
 //BA.debugLineNum = 236;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
httputils2service._process_globals();
statemanager._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 18;BA.debugLine="Dim MyLan As ServerSocket";
_vvv2 = new anywheresoftware.b4a.objects.SocketWrapper.ServerSocketWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim MyWiFi As ABWifi";
_vvv3 = new com.AB.ABWifi.ABWifi();
 //BA.debugLineNum = 20;BA.debugLine="Dim AtHome As Boolean";
_vvv4 = false;
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public static String  _rpi_dac_click() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 342;BA.debugLine="Sub rPI_DAC_Click";
 //BA.debugLineNum = 343;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 344;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv4 = "";
 //BA.debugLineNum = 346;BA.debugLine="url = BaseURL & \"/kodi\"";
mostCurrent._vvvvvvv4 = mostCurrent._vvvvvvv3+"/kodi";
 //BA.debugLineNum = 347;BA.debugLine="Job.Initialize(\"rPIDAC\", Me)";
_job._initialize(processBA,"rPIDAC",main.getObject());
 //BA.debugLineNum = 348;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv7 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 349;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvv0 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 350;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"kodiYe";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiYellow.png").getObject()));
 //BA.debugLineNum = 351;BA.debugLine="If checkedDAC Then";
if (_vvvvvvv6) { 
 //BA.debugLineNum = 352;BA.debugLine="Job.PostString(url,\"id=rPI_DAC&method=OFF\")";
_job._vvvvv1(mostCurrent._vvvvvvv4,"id=rPI_DAC&method=OFF");
 }else {
 //BA.debugLineNum = 354;BA.debugLine="Job.PostString(url,\"id=rPI_DAC&method=ON\")";
_job._vvvvv1(mostCurrent._vvvvvvv4,"id=rPI_DAC&method=ON");
 };
 //BA.debugLineNum = 356;BA.debugLine="checkedDAC=Not(checkedDAC)";
_vvvvvvv6 = anywheresoftware.b4a.keywords.Common.Not(_vvvvvvv6);
 //BA.debugLineNum = 357;BA.debugLine="rPI_DAC.Enabled=False";
mostCurrent._rpi_dac.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 358;BA.debugLine="End Sub";
return "";
}
public static String  _rpi_dac_longclick() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 360;BA.debugLine="Sub rPI_DAC_LongClick";
 //BA.debugLineNum = 361;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 362;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv4 = "";
 //BA.debugLineNum = 364;BA.debugLine="url = BaseURL & \"/tdcmd\"";
mostCurrent._vvvvvvv4 = mostCurrent._vvvvvvv3+"/tdcmd";
 //BA.debugLineNum = 365;BA.debugLine="Job.Initialize(\"prPI_DAC\", Me)";
_job._initialize(processBA,"prPI_DAC",main.getObject());
 //BA.debugLineNum = 366;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv7 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 367;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvv0 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 368;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"kodiGr";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 //BA.debugLineNum = 369;BA.debugLine="checkedDAC = False";
_vvvvvvv6 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 370;BA.debugLine="Job.PostString(url,\"id=rPI_DAC&method=OFF\")";
_job._vvvvv1(mostCurrent._vvvvvvv4,"id=rPI_DAC&method=OFF");
 //BA.debugLineNum = 371;BA.debugLine="rPI_DAC.Enabled=False";
mostCurrent._rpi_dac.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 372;BA.debugLine="End Sub";
return "";
}
public static String  _rpi_spdif_click() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 312;BA.debugLine="Sub rPI_SPDIF_Click";
 //BA.debugLineNum = 313;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 314;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv4 = "";
 //BA.debugLineNum = 316;BA.debugLine="url = BaseURL & \"/kodi\"";
mostCurrent._vvvvvvv4 = mostCurrent._vvvvvvv3+"/kodi";
 //BA.debugLineNum = 317;BA.debugLine="Job.Initialize(\"rPISPDIF\", Me)";
_job._initialize(processBA,"rPISPDIF",main.getObject());
 //BA.debugLineNum = 318;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv7 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 319;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvv0 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 320;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"kodi";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiYellow.png").getObject()));
 //BA.debugLineNum = 321;BA.debugLine="If checkedSPDIF Then";
if (_vvvvvvv5) { 
 //BA.debugLineNum = 322;BA.debugLine="Job.PostString(url,\"id=rPI_SPDIF&method=OFF\")";
_job._vvvvv1(mostCurrent._vvvvvvv4,"id=rPI_SPDIF&method=OFF");
 }else {
 //BA.debugLineNum = 324;BA.debugLine="Job.PostString(url,\"id=rPI_SPDIF&method=ON\")";
_job._vvvvv1(mostCurrent._vvvvvvv4,"id=rPI_SPDIF&method=ON");
 };
 //BA.debugLineNum = 326;BA.debugLine="checkedSPDIF=Not(checkedSPDIF)";
_vvvvvvv5 = anywheresoftware.b4a.keywords.Common.Not(_vvvvvvv5);
 //BA.debugLineNum = 327;BA.debugLine="End Sub";
return "";
}
public static String  _rpi_spdif_longclick() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 329;BA.debugLine="Sub rPI_SPDIF_LongClick";
 //BA.debugLineNum = 330;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 331;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv4 = "";
 //BA.debugLineNum = 333;BA.debugLine="url = BaseURL & \"/tdcmd\"";
mostCurrent._vvvvvvv4 = mostCurrent._vvvvvvv3+"/tdcmd";
 //BA.debugLineNum = 334;BA.debugLine="Job.Initialize(\"prPISPDIF\", Me)";
_job._initialize(processBA,"prPISPDIF",main.getObject());
 //BA.debugLineNum = 335;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv7 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 336;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvv0 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 337;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"kodi";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 //BA.debugLineNum = 338;BA.debugLine="checkedSPDIF = False";
_vvvvvvv5 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 339;BA.debugLine="Job.PostString(url,\"id=rPI_SPDIF&method=OFF\")";
_job._vvvvv1(mostCurrent._vvvvvvv4,"id=rPI_SPDIF&method=OFF");
 //BA.debugLineNum = 340;BA.debugLine="End Sub";
return "";
}
public static String  _tabstrip1_pageselected(int _position) throws Exception{
 //BA.debugLineNum = 148;BA.debugLine="Sub TabStrip1_PageSelected (Position As Int)";
 //BA.debugLineNum = 150;BA.debugLine="End Sub";
return "";
}
public static String[]  _webview1_userandpasswordrequired(String _host,String _realm) throws Exception{
 //BA.debugLineNum = 295;BA.debugLine="Sub WebView1_UserAndPasswordRequired (Host As Stri";
 //BA.debugLineNum = 296;BA.debugLine="Return Array As String(txtUser.Text, txtPassword.";
if (true) return new String[]{mostCurrent._txtuser.getText(),mostCurrent._txtpassword.getText()};
 //BA.debugLineNum = 297;BA.debugLine="End Sub";
return null;
}
}
