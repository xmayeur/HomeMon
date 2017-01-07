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
public static anywheresoftware.b4a.objects.Timer _vvv5 = null;
public static String _vvvvvvv6 = "";
public anywheresoftware.b4a.objects.TabStripViewPager _tabstrip1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvv2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvv3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncancel = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnok = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtport = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtserver = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtuser = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnspam = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtresult = null;
public static String _vvvvvvv7 = "";
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
public static String _vvvvvvv5 = "";
public static String _vvvvvvv4 = "";
public anywheresoftware.b4a.objects.LabelWrapper _lblipaddress = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblssid = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txthomessid = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtlocalserverip = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _skbar = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _nas = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _sw_rpi1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _sw_dac = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _sw_spdif = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _sw_nas = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _rpi_1 = null;
public anywheresoftware.b4a.objects.collections.JSONParser _vvvvvvv0 = null;
public anywheresoftware.b4a.objects.collections.Map _vvvvvvvv1 = null;
public anywheresoftware.b4a.objects.collections.Map _vvvvvvvv2 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lstfav = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelfav = null;
public anywheresoftware.b4a.objects.collections.List _vvvvvvvv3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblplayingnow = null;
public be.mayeur.home.homemon.httputils2service _vvvvvv0 = null;
public be.mayeur.home.homemon.statemanager _vvvvvvv1 = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 85;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 87;BA.debugLine="Activity.LoadLayout(\"Main\")";
mostCurrent._activity.LoadLayout("Main",mostCurrent.activityBA);
 //BA.debugLineNum = 88;BA.debugLine="TabStrip1.LoadLayout(\"Page1\", \"Devices\")";
mostCurrent._tabstrip1.LoadLayout("Page1",(java.lang.CharSequence)("Devices"));
 //BA.debugLineNum = 89;BA.debugLine="TabStrip1.LoadLayout(\"Page2\", \"Spam Mon\")";
mostCurrent._tabstrip1.LoadLayout("Page2",(java.lang.CharSequence)("Spam Mon"));
 //BA.debugLineNum = 90;BA.debugLine="TabStrip1.LoadLayout(\"Page3\", \"Floor Cam\")";
mostCurrent._tabstrip1.LoadLayout("Page3",(java.lang.CharSequence)("Floor Cam"));
 //BA.debugLineNum = 92;BA.debugLine="p.Initialize(\"\")";
mostCurrent._vvvvvvv2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 93;BA.debugLine="Activity.AddView(p, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vvvvvvv2.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 94;BA.debugLine="p.LoadLayout(\"Param\")";
mostCurrent._vvvvvvv2.LoadLayout("Param",mostCurrent.activityBA);
 //BA.debugLineNum = 95;BA.debugLine="p.Visible=False";
mostCurrent._vvvvvvv2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 97;BA.debugLine="pf.Initialize(\"\")";
mostCurrent._vvvvvvv3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 98;BA.debugLine="Activity.AddView(pf, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vvvvvvv3.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 99;BA.debugLine="pf.LoadLayout(\"Favourites\")";
mostCurrent._vvvvvvv3.LoadLayout("Favourites",mostCurrent.activityBA);
 //BA.debugLineNum = 100;BA.debugLine="pf.Visible=False";
mostCurrent._vvvvvvv3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 105;BA.debugLine="Activity.AddMenuItem(\"Devices\", \"mnu1\")";
mostCurrent._activity.AddMenuItem((java.lang.CharSequence)("Devices"),"mnu1");
 //BA.debugLineNum = 106;BA.debugLine="Activity.AddMenuItem(\"Spam Monitor\", \"mnu2\")";
mostCurrent._activity.AddMenuItem((java.lang.CharSequence)("Spam Monitor"),"mnu2");
 //BA.debugLineNum = 107;BA.debugLine="Activity.AddMenuItem(\"Floor Cam\", \"mnu3\")";
mostCurrent._activity.AddMenuItem((java.lang.CharSequence)("Floor Cam"),"mnu3");
 //BA.debugLineNum = 108;BA.debugLine="Activity.AddMenuItem(\"Settings\", \"mnuSettings\")";
mostCurrent._activity.AddMenuItem((java.lang.CharSequence)("Settings"),"mnuSettings");
 //BA.debugLineNum = 113;BA.debugLine="MyLan.Initialize(0, \"\")";
_vvv2.Initialize(processBA,(int) (0),"");
 //BA.debugLineNum = 115;BA.debugLine="If MyWiFi.ABLoadWifi() Then";
if (_vvv3.ABLoadWifi(processBA)) { 
 //BA.debugLineNum = 116;BA.debugLine="MySSID=MyWiFi.ABGetCurrentWifiInfo.SSID";
mostCurrent._vvvvvvv4 = _vvv3.ABGetCurrentWifiInfo().SSID;
 }else {
 //BA.debugLineNum = 118;BA.debugLine="MySSID=\"\"";
mostCurrent._vvvvvvv4 = "";
 };
 //BA.debugLineNum = 120;BA.debugLine="lblSSID.Text=\"SSID: \" & MySSID";
mostCurrent._lblssid.setText((Object)("SSID: "+mostCurrent._vvvvvvv4));
 //BA.debugLineNum = 121;BA.debugLine="AtHome=MySSID.Contains(StateManager.GetSetting2(\"";
_vvv4 = mostCurrent._vvvvvvv4.contains(mostCurrent._vvvvvvv1._vv4(mostCurrent.activityBA,"HomeSSID","CC88"));
 //BA.debugLineNum = 123;BA.debugLine="lblIPAddress.Text=\"IP: \" & MyLan.GetMyIP()";
mostCurrent._lblipaddress.setText((Object)("IP: "+_vvv2.GetMyIP()));
 //BA.debugLineNum = 124;BA.debugLine="If AtHome Then";
if (_vvv4) { 
 //BA.debugLineNum = 125;BA.debugLine="txtLocalServerIP.text=StateManager.GetSetting2(\"";
mostCurrent._txtlocalserverip.setText((Object)(mostCurrent._vvvvvvv1._vv4(mostCurrent.activityBA,"LocIP","192.168.0.12")));
 //BA.debugLineNum = 126;BA.debugLine="MyHost=\"https://\" & txtLocalServerIP.text";
mostCurrent._vvvvvvv5 = "https://"+mostCurrent._txtlocalserverip.getText();
 }else {
 //BA.debugLineNum = 128;BA.debugLine="txtServer.Text=StateManager.GetSetting2(\"BaseURL";
mostCurrent._txtserver.setText((Object)(mostCurrent._vvvvvvv1._vv4(mostCurrent.activityBA,"BaseURL","https://home.mayeur.be")));
 //BA.debugLineNum = 129;BA.debugLine="MyHost=txtServer.Text";
mostCurrent._vvvvvvv5 = mostCurrent._txtserver.getText();
 };
 //BA.debugLineNum = 132;BA.debugLine="txtPort.Text=StateManager.GetSetting2(\"Port\", \"44";
mostCurrent._txtport.setText((Object)(mostCurrent._vvvvvvv1._vv4(mostCurrent.activityBA,"Port","443")));
 //BA.debugLineNum = 133;BA.debugLine="BaseURL=MyHost & \":\" & txtPort.Text";
mostCurrent._vvvvvvv6 = mostCurrent._vvvvvvv5+":"+mostCurrent._txtport.getText();
 //BA.debugLineNum = 136;BA.debugLine="txtUser.Text=StateManager.GetSetting2(\"User\", \"xa";
mostCurrent._txtuser.setText((Object)(mostCurrent._vvvvvvv1._vv4(mostCurrent.activityBA,"User","xavier")));
 //BA.debugLineNum = 137;BA.debugLine="txtPassword.Text=StateManager.GetSetting(\"Passwor";
mostCurrent._txtpassword.setText((Object)(mostCurrent._vvvvvvv1._vv3(mostCurrent.activityBA,"Password")));
 //BA.debugLineNum = 138;BA.debugLine="If txtPassword.Text=\"\" Then";
if ((mostCurrent._txtpassword.getText()).equals("")) { 
 //BA.debugLineNum = 139;BA.debugLine="TabStrip1.ScrollTo(2, True)";
mostCurrent._tabstrip1.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 143;BA.debugLine="sw_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"off.p";
mostCurrent._sw_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"off.png").getObject()));
 //BA.debugLineNum = 144;BA.debugLine="sw_SPDIF.Tag=\"off\"";
mostCurrent._sw_spdif.setTag((Object)("off"));
 //BA.debugLineNum = 145;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"kodi";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 //BA.debugLineNum = 147;BA.debugLine="sw_DAC.Bitmap=LoadBitmap(File.DirAssets, \"off.png";
mostCurrent._sw_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"off.png").getObject()));
 //BA.debugLineNum = 148;BA.debugLine="sw_DAC.Tag=\"off\"";
mostCurrent._sw_dac.setTag((Object)("off"));
 //BA.debugLineNum = 149;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"kodiGr";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 //BA.debugLineNum = 151;BA.debugLine="sw_NAS.Bitmap=LoadBitmap(File.DirAssets, \"off.png";
mostCurrent._sw_nas.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"off.png").getObject()));
 //BA.debugLineNum = 152;BA.debugLine="sw_NAS.Tag=\"Unknown\"";
mostCurrent._sw_nas.setTag((Object)("Unknown"));
 //BA.debugLineNum = 153;BA.debugLine="NAS.Bitmap=LoadBitmap(File.DirAssets, \"nasGrey.jp";
mostCurrent._nas.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"nasGrey.jpg").getObject()));
 //BA.debugLineNum = 155;BA.debugLine="sw_rpi1.Bitmap=LoadBitmap(File.DirAssets, \"off.pn";
mostCurrent._sw_rpi1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"off.png").getObject()));
 //BA.debugLineNum = 156;BA.debugLine="rpi_1.Bitmap=LoadBitmap(File.DirAssets,\"rpi.png\")";
mostCurrent._rpi_1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"rpi.png").getObject()));
 //BA.debugLineNum = 157;BA.debugLine="sw_rpi1.Tag=\"off\"";
mostCurrent._sw_rpi1.setTag((Object)("off"));
 //BA.debugLineNum = 160;BA.debugLine="txtCAM1port.text=StateManager.GetSetting2(\"CAM1po";
mostCurrent._txtcam1port.setText((Object)(mostCurrent._vvvvvvv1._vv4(mostCurrent.activityBA,"CAM1port","8084")));
 //BA.debugLineNum = 161;BA.debugLine="txtCAM2port.text=StateManager.GetSetting2(\"CAM2po";
mostCurrent._txtcam2port.setText((Object)(mostCurrent._vvvvvvv1._vv4(mostCurrent.activityBA,"CAM2port","8083")));
 //BA.debugLineNum = 164;BA.debugLine="Timer1.Initialize(\"Timer1\", 30000)";
_vvv5.Initialize(processBA,"Timer1",(long) (30000));
 //BA.debugLineNum = 165;BA.debugLine="Timer1.Enabled=True";
_vvv5.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 167;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 175;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 177;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 169;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 171;BA.debugLine="Probe_all";
_probe_all();
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return "";
}
public static String  _btncancel_click() throws Exception{
 //BA.debugLineNum = 461;BA.debugLine="Sub btnCancel_Click";
 //BA.debugLineNum = 462;BA.debugLine="p.Visible=False";
mostCurrent._vvvvvvv2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 463;BA.debugLine="TabStrip1.ScrollTo(0, True)";
mostCurrent._tabstrip1.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 464;BA.debugLine="End Sub";
return "";
}
public static String  _btnclear_click() throws Exception{
 //BA.debugLineNum = 511;BA.debugLine="Sub btnClear_Click";
 //BA.debugLineNum = 512;BA.debugLine="WebView1.LoadHtml(\"<html><body>Press a button</bo";
mostCurrent._webview1.LoadHtml("<html><body>Press a button</body></html>");
 //BA.debugLineNum = 513;BA.debugLine="End Sub";
return "";
}
public static String  _btnfavcancel_click() throws Exception{
 //BA.debugLineNum = 696;BA.debugLine="Sub btnFavCancel_Click";
 //BA.debugLineNum = 697;BA.debugLine="pf.Visible=False";
mostCurrent._vvvvvvv3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 698;BA.debugLine="TabStrip1.ScrollTo(0, True)";
mostCurrent._tabstrip1.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 699;BA.debugLine="End Sub";
return "";
}
public static String  _btnloadflcam_click() throws Exception{
 //BA.debugLineNum = 503;BA.debugLine="Sub btnLoadFlCam_Click";
 //BA.debugLineNum = 504;BA.debugLine="WebView1.LoadUrl(MyHost.Replace(\"https\", \"http\")";
mostCurrent._webview1.LoadUrl(mostCurrent._vvvvvvv5.replace("https","http")+":"+mostCurrent._txtcam2port.getText());
 //BA.debugLineNum = 505;BA.debugLine="End Sub";
return "";
}
public static String  _btnloadgfl_click() throws Exception{
 //BA.debugLineNum = 507;BA.debugLine="Sub btnLoadGFl_Click";
 //BA.debugLineNum = 508;BA.debugLine="WebView1.LoadUrl(MyHost.Replace(\"https\", \"http\")";
mostCurrent._webview1.LoadUrl(mostCurrent._vvvvvvv5.replace("https","http")+":"+mostCurrent._txtcam1port.getText());
 //BA.debugLineNum = 509;BA.debugLine="End Sub";
return "";
}
public static String  _btnok_click() throws Exception{
 //BA.debugLineNum = 440;BA.debugLine="Sub btnOK_Click";
 //BA.debugLineNum = 441;BA.debugLine="StateManager.SetSetting(\"BaseURL\", txtServer.Text";
mostCurrent._vvvvvvv1._vvv1(mostCurrent.activityBA,"BaseURL",mostCurrent._txtserver.getText());
 //BA.debugLineNum = 442;BA.debugLine="StateManager.SetSetting(\"Port\",txtPort.Text)";
mostCurrent._vvvvvvv1._vvv1(mostCurrent.activityBA,"Port",mostCurrent._txtport.getText());
 //BA.debugLineNum = 444;BA.debugLine="StateManager.SetSetting(\"User\", txtUser.Text)";
mostCurrent._vvvvvvv1._vvv1(mostCurrent.activityBA,"User",mostCurrent._txtuser.getText());
 //BA.debugLineNum = 445;BA.debugLine="StateManager.SetSetting(\"Password\", txtPassword.T";
mostCurrent._vvvvvvv1._vvv1(mostCurrent.activityBA,"Password",mostCurrent._txtpassword.getText());
 //BA.debugLineNum = 447;BA.debugLine="StateManager.SetSetting(\"CAM1port\", txtCAM1port.T";
mostCurrent._vvvvvvv1._vvv1(mostCurrent.activityBA,"CAM1port",mostCurrent._txtcam1port.getText());
 //BA.debugLineNum = 448;BA.debugLine="StateManager.SetSetting(\"CAM2port\", txtCAM2port.T";
mostCurrent._vvvvvvv1._vvv1(mostCurrent.activityBA,"CAM2port",mostCurrent._txtcam2port.getText());
 //BA.debugLineNum = 450;BA.debugLine="StateManager.SetSetting(\"HomeSSID\", txtHomeSSID.T";
mostCurrent._vvvvvvv1._vvv1(mostCurrent.activityBA,"HomeSSID",mostCurrent._txthomessid.getText());
 //BA.debugLineNum = 451;BA.debugLine="StateManager.SetSetting(\"LocIP\", txtLocalServerIP";
mostCurrent._vvvvvvv1._vvv1(mostCurrent.activityBA,"LocIP",mostCurrent._txtlocalserverip.getText());
 //BA.debugLineNum = 453;BA.debugLine="StateManager.SaveSettings";
mostCurrent._vvvvvvv1._vv7(mostCurrent.activityBA);
 //BA.debugLineNum = 454;BA.debugLine="If AtHome = False Then";
if (_vvv4==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 455;BA.debugLine="BaseURL=txtServer.Text & \":\" & txtPort.Text";
mostCurrent._vvvvvvv6 = mostCurrent._txtserver.getText()+":"+mostCurrent._txtport.getText();
 };
 //BA.debugLineNum = 457;BA.debugLine="p.Visible=False";
mostCurrent._vvvvvvv2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 458;BA.debugLine="TabStrip1.ScrollTo(0, True)";
mostCurrent._tabstrip1.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 459;BA.debugLine="End Sub";
return "";
}
public static String  _btnspam_click() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 466;BA.debugLine="Sub btnSpam_Click";
 //BA.debugLineNum = 467;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 468;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 470;BA.debugLine="url = BaseURL & \"/api?cmd=spam\"";
mostCurrent._vvvvvvv7 = mostCurrent._vvvvvvv6+"/api?cmd=spam";
 //BA.debugLineNum = 471;BA.debugLine="Job.Initialize(\"spam\", Me)";
_job._initialize(processBA,"spam",main.getObject());
 //BA.debugLineNum = 472;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv0 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 473;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvvv1 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 474;BA.debugLine="Job.Download( url)";
_job._vvv7(mostCurrent._vvvvvvv7);
 //BA.debugLineNum = 475;BA.debugLine="End Sub";
return "";
}
public static String  _btnstart_click() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 477;BA.debugLine="Sub btnStart_Click";
 //BA.debugLineNum = 478;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 479;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 481;BA.debugLine="url = BaseURL & \"/api?cmd=spam&param=start\"";
mostCurrent._vvvvvvv7 = mostCurrent._vvvvvvv6+"/api?cmd=spam&param=start";
 //BA.debugLineNum = 482;BA.debugLine="Job.Initialize(\"spamStart\", Me)";
_job._initialize(processBA,"spamStart",main.getObject());
 //BA.debugLineNum = 483;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv0 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 484;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvvv1 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 485;BA.debugLine="Job.Download( url)";
_job._vvv7(mostCurrent._vvvvvvv7);
 //BA.debugLineNum = 486;BA.debugLine="End Sub";
return "";
}
public static String  _btnstop_click() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 488;BA.debugLine="Sub btnSTop_Click";
 //BA.debugLineNum = 489;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 490;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 492;BA.debugLine="url = BaseURL & \"/api?cmd=spam&param=stop\"";
mostCurrent._vvvvvvv7 = mostCurrent._vvvvvvv6+"/api?cmd=spam&param=stop";
 //BA.debugLineNum = 493;BA.debugLine="Job.Initialize(\"spamStop\", Me)";
_job._initialize(processBA,"spamStop",main.getObject());
 //BA.debugLineNum = 494;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv0 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 495;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvvv1 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 496;BA.debugLine="Job.Download( url)";
_job._vvv7(mostCurrent._vvvvvvv7);
 //BA.debugLineNum = 497;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 28;BA.debugLine="Private BaseURL As String";
mostCurrent._vvvvvvv6 = "";
 //BA.debugLineNum = 29;BA.debugLine="Private TabStrip1 As TabStrip";
mostCurrent._tabstrip1 = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 30;BA.debugLine="Private p As Panel";
mostCurrent._vvvvvvv2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private pf As Panel";
mostCurrent._vvvvvvv3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private btnCancel As Button";
mostCurrent._btncancel = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private btnOK As Button";
mostCurrent._btnok = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private txtPassword As EditText";
mostCurrent._txtpassword = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private txtPort As EditText";
mostCurrent._txtport = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private txtServer As EditText";
mostCurrent._txtserver = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private txtUser As EditText";
mostCurrent._txtuser = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private btnSpam As Button";
mostCurrent._btnspam = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private txtResult As EditText";
mostCurrent._txtresult = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 44;BA.debugLine="Private btnStart As Button";
mostCurrent._btnstart = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private btnSTop As Button";
mostCurrent._btnstop = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private btnLoadFlCam As Button";
mostCurrent._btnloadflcam = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private btnLoadGFl As Button";
mostCurrent._btnloadgfl = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private txtCAM1port As EditText";
mostCurrent._txtcam1port = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private txtCAM2port As EditText";
mostCurrent._txtcam2port = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private btnClear As Button";
mostCurrent._btnclear = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private rPI_DAC As ImageView";
mostCurrent._rpi_dac = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private rPI_SPDIF As ImageView";
mostCurrent._rpi_spdif = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private MyHost As String";
mostCurrent._vvvvvvv5 = "";
 //BA.debugLineNum = 59;BA.debugLine="Private MySSID As String";
mostCurrent._vvvvvvv4 = "";
 //BA.debugLineNum = 61;BA.debugLine="Private lblIPAddress As Label";
mostCurrent._lblipaddress = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private lblSSID As Label";
mostCurrent._lblssid = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private txtHomeSSID As EditText";
mostCurrent._txthomessid = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private txtLocalServerIP As EditText";
mostCurrent._txtlocalserverip = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private skBar As SeekBar";
mostCurrent._skbar = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private NAS As ImageView";
mostCurrent._nas = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private sw_rpi1 As ImageView";
mostCurrent._sw_rpi1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private sw_DAC As ImageView";
mostCurrent._sw_dac = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Private sw_SPDIF As ImageView";
mostCurrent._sw_spdif = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Private sw_NAS As ImageView";
mostCurrent._sw_nas = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Private rpi_1 As ImageView";
mostCurrent._rpi_1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Dim JSON As JSONParser";
mostCurrent._vvvvvvv0 = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 76;BA.debugLine="Dim js As Map";
mostCurrent._vvvvvvvv1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 77;BA.debugLine="Dim media As Map";
mostCurrent._vvvvvvvv2 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 78;BA.debugLine="Private lstFav As ListView";
mostCurrent._lstfav = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Private PanelFav As Panel";
mostCurrent._panelfav = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Private fav As List";
mostCurrent._vvvvvvvv3 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 82;BA.debugLine="Private lblPLayingNow As Label";
mostCurrent._lblplayingnow = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(be.mayeur.home.homemon.httpjob _job) throws Exception{
int _i = 0;
 //BA.debugLineNum = 224;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 226;BA.debugLine="Log(\"JobName = \" & Job.JobName & \", Success = \" &";
anywheresoftware.b4a.keywords.Common.Log("JobName = "+_job._vvvvv6+", Success = "+BA.ObjectToString(_job._vvvvv7));
 //BA.debugLineNum = 227;BA.debugLine="If Job.Success = True Then";
if (_job._vvvvv7==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 228;BA.debugLine="Log(Job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._vvvv5());
 //BA.debugLineNum = 230;BA.debugLine="Select Job.JobName";
switch (BA.switchObjectToInt(_job._vvvvv6,"Login","rPIDAC","rPISPDIF","pkrPI_SPDIF","pkrPI_DAC","spam","spamStart","spamStop","pdSAM","SAM","pdNAS","plNAS","plrPI1","Fav","PlayFav","pkprPI_SPDIF")) {
case 0: {
 //BA.debugLineNum = 232;BA.debugLine="Log(\"Login: \" & Job.GetString)";
anywheresoftware.b4a.keywords.Common.Log("Login: "+_job._vvvv5());
 break; }
case 1: {
 //BA.debugLineNum = 235;BA.debugLine="If Job.GetString = \"Kodi On\" Then";
if ((_job._vvvv5()).equals("Kodi On")) { 
 //BA.debugLineNum = 236;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"ko";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiBlue.png").getObject()));
 }else if((_job._vvvv5()).equals("OFF")) { 
 //BA.debugLineNum = 238;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"ko";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 }else if((_job._vvvv5()).equals("POWERED")) { 
 //BA.debugLineNum = 240;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"ko";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiYellow.png").getObject()));
 }else {
 //BA.debugLineNum = 242;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"ko";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiRed.png").getObject()));
 };
 break; }
case 2: {
 //BA.debugLineNum = 246;BA.debugLine="If Job.GetString = \"Kodi On\" Then";
if ((_job._vvvv5()).equals("Kodi On")) { 
 //BA.debugLineNum = 247;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"k";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiBlue.png").getObject()));
 }else if((_job._vvvv5()).equals("OFF")) { 
 //BA.debugLineNum = 249;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"k";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 }else if((_job._vvvv5()).equals("POWERED")) { 
 //BA.debugLineNum = 251;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiYellow.png").getObject()));
 }else {
 //BA.debugLineNum = 253;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"k";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiRed.png").getObject()));
 };
 break; }
case 3: {
 //BA.debugLineNum = 257;BA.debugLine="If Job.GetString = \"OFF\"  Or Job.GetString = \"";
if ((_job._vvvv5()).equals("OFF") || (_job._vvvv5()).equals("success")) { 
 //BA.debugLineNum = 258;BA.debugLine="sw_SPDIF.Tag=\"off\"";
mostCurrent._sw_spdif.setTag((Object)("off"));
 //BA.debugLineNum = 259;BA.debugLine="sw_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"of";
mostCurrent._sw_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"off.png").getObject()));
 //BA.debugLineNum = 260;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"k";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 }else if((_job._vvvv5()).equals("Kodi On")) { 
 //BA.debugLineNum = 262;BA.debugLine="sw_SPDIF.Tag=\"on\"";
mostCurrent._sw_spdif.setTag((Object)("on"));
 //BA.debugLineNum = 263;BA.debugLine="sw_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"on";
mostCurrent._sw_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"on.png").getObject()));
 //BA.debugLineNum = 264;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"k";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiBlue.png").getObject()));
 }else {
 //BA.debugLineNum = 266;BA.debugLine="sw_SPDIF.Tag=\"on\"";
mostCurrent._sw_spdif.setTag((Object)("on"));
 //BA.debugLineNum = 267;BA.debugLine="sw_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"on";
mostCurrent._sw_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"on.png").getObject()));
 //BA.debugLineNum = 268;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"k";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiYellow.png").getObject()));
 };
 break; }
case 4: {
 //BA.debugLineNum = 272;BA.debugLine="If Job.GetString = \"OFF\" Or Job.GetString = \"su";
if ((_job._vvvv5()).equals("OFF") || (_job._vvvv5()).equals("success")) { 
 //BA.debugLineNum = 273;BA.debugLine="sw_DAC.Tag=\"off\"";
mostCurrent._sw_dac.setTag((Object)("off"));
 //BA.debugLineNum = 274;BA.debugLine="sw_DAC.Bitmap=LoadBitmap(File.DirAssets, \"off.";
mostCurrent._sw_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"off.png").getObject()));
 //BA.debugLineNum = 275;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"kod";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 }else if((_job._vvvv5()).equals("Kodi On")) { 
 //BA.debugLineNum = 277;BA.debugLine="sw_DAC.Tag=\"on\"";
mostCurrent._sw_dac.setTag((Object)("on"));
 //BA.debugLineNum = 278;BA.debugLine="sw_DAC.Bitmap=LoadBitmap(File.DirAssets, \"on.p";
mostCurrent._sw_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"on.png").getObject()));
 //BA.debugLineNum = 279;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"kod";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiBlue.png").getObject()));
 }else {
 //BA.debugLineNum = 281;BA.debugLine="sw_DAC.Tag=\"on\"";
mostCurrent._sw_dac.setTag((Object)("on"));
 //BA.debugLineNum = 282;BA.debugLine="sw_DAC.Bitmap=LoadBitmap(File.DirAssets, \"on.p";
mostCurrent._sw_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"on.png").getObject()));
 //BA.debugLineNum = 283;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"kod";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiYellow.png").getObject()));
 };
 break; }
case 5: 
case 6: 
case 7: {
 //BA.debugLineNum = 287;BA.debugLine="txtResult.Text= Job.GetString";
mostCurrent._txtresult.setText((Object)(_job._vvvv5()));
 break; }
case 8: {
 //BA.debugLineNum = 290;BA.debugLine="If Job.GetString=\"OFF\" Or Job.GetString.Contain";
if ((_job._vvvv5()).equals("OFF") || _job._vvvv5().contains("Unknown")) { 
 //BA.debugLineNum = 291;BA.debugLine="skBar.Value=0";
mostCurrent._skbar.setValue((int) (0));
 }else if((_job._vvvv5()).equals("ON")) { 
 //BA.debugLineNum = 293;BA.debugLine="skBar.Value=255";
mostCurrent._skbar.setValue((int) (255));
 }else {
 //BA.debugLineNum = 295;BA.debugLine="skBar.Value=Job.GetString";
mostCurrent._skbar.setValue((int)(Double.parseDouble(_job._vvvv5())));
 };
 break; }
case 9: {
 //BA.debugLineNum = 299;BA.debugLine="skBar.Enabled=True";
mostCurrent._skbar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 break; }
case 10: {
 //BA.debugLineNum = 302;BA.debugLine="If Job.GetString=\"OFF\" Or Job.GetString.Contain";
if ((_job._vvvv5()).equals("OFF") || _job._vvvv5().contains("Unknown")) { 
 //BA.debugLineNum = 303;BA.debugLine="sw_NAS.Bitmap=LoadBitmap(File.DirAssets, \"off.";
mostCurrent._sw_nas.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"off.png").getObject()));
 //BA.debugLineNum = 304;BA.debugLine="sw_NAS.Tag=\"off\"";
mostCurrent._sw_nas.setTag((Object)("off"));
 //BA.debugLineNum = 305;BA.debugLine="NAS.Bitmap=LoadBitmap(File.DirAssets, \"nasGrey";
mostCurrent._nas.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"nasGrey.jpg").getObject()));
 }else if((_job._vvvv5()).equals("ON")) { 
 //BA.debugLineNum = 307;BA.debugLine="sw_NAS.Bitmap=LoadBitmap(File.DirAssets, \"on.p";
mostCurrent._sw_nas.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"on.png").getObject()));
 //BA.debugLineNum = 308;BA.debugLine="sw_NAS.Tag=\"on\"";
mostCurrent._sw_nas.setTag((Object)("on"));
 //BA.debugLineNum = 309;BA.debugLine="NAS.Bitmap=LoadBitmap(File.DirAssets,\"nasyello";
mostCurrent._nas.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"nasyellow.jpg").getObject()));
 }else {
 //BA.debugLineNum = 311;BA.debugLine="sw_NAS.Bitmap=LoadBitmap(File.DirAssets, \"off.";
mostCurrent._sw_nas.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"off.png").getObject()));
 //BA.debugLineNum = 312;BA.debugLine="sw_NAS.Tag=\"off\"";
mostCurrent._sw_nas.setTag((Object)("off"));
 //BA.debugLineNum = 313;BA.debugLine="NAS.Bitmap=LoadBitmap(File.DirAssets,\"nasRed.j";
mostCurrent._nas.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"nasRed.jpg").getObject()));
 };
 break; }
case 11: {
 //BA.debugLineNum = 317;BA.debugLine="If (Job.GetString=\"Alive\") Then";
if (((_job._vvvv5()).equals("Alive"))) { 
 //BA.debugLineNum = 318;BA.debugLine="NAS.Bitmap=LoadBitmap(File.DirAssets, \"nasBlue";
mostCurrent._nas.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"nasBlue.jpg").getObject()));
 //BA.debugLineNum = 319;BA.debugLine="sw_NAS.Tag=\"Alive\"";
mostCurrent._sw_nas.setTag((Object)("Alive"));
 //BA.debugLineNum = 320;BA.debugLine="sw_NAS.Bitmap=LoadBitmap(File.DirAssets,\"on.pn";
mostCurrent._sw_nas.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"on.png").getObject()));
 }else {
 //BA.debugLineNum = 322;BA.debugLine="sw_NAS.Tag=\"Unknown\"";
mostCurrent._sw_nas.setTag((Object)("Unknown"));
 };
 break; }
case 12: {
 //BA.debugLineNum = 326;BA.debugLine="If (Job.GetString=\"Alive\") Then";
if (((_job._vvvv5()).equals("Alive"))) { 
 //BA.debugLineNum = 327;BA.debugLine="rpi_1.Bitmap=LoadBitmap(File.DirAssets, \"rpi.p";
mostCurrent._rpi_1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"rpi.png").getObject()));
 //BA.debugLineNum = 328;BA.debugLine="sw_rpi1.Bitmap=LoadBitmap(File.DirAssets, \"on.";
mostCurrent._sw_rpi1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"on.png").getObject()));
 //BA.debugLineNum = 329;BA.debugLine="sw_rpi1.Tag=\"Alive\"";
mostCurrent._sw_rpi1.setTag((Object)("Alive"));
 }else {
 //BA.debugLineNum = 331;BA.debugLine="rpi_1.Bitmap=LoadBitmap(File.DirAssets, \"rpiGr";
mostCurrent._rpi_1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"rpiGrey.png").getObject()));
 //BA.debugLineNum = 332;BA.debugLine="sw_rpi1.Bitmap=LoadBitmap(File.DirAssets, \"off";
mostCurrent._sw_rpi1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"off.png").getObject()));
 //BA.debugLineNum = 333;BA.debugLine="sw_rpi1.Tag=\"off\"";
mostCurrent._sw_rpi1.setTag((Object)("off"));
 };
 break; }
case 13: {
 //BA.debugLineNum = 337;BA.debugLine="If Job.GetString.Contains(\"failed\") = False The";
if (_job._vvvv5().contains("failed")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 338;BA.debugLine="JSON.Initialize(Job.GetString)";
mostCurrent._vvvvvvv0.Initialize(_job._vvvv5());
 //BA.debugLineNum = 339;BA.debugLine="lstFav.SingleLineLayout.Label.TextColor=Colors";
mostCurrent._lstfav.getSingleLineLayout().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 340;BA.debugLine="lstFav.SingleLineLayout.Label.TextSize = 12";
mostCurrent._lstfav.getSingleLineLayout().Label.setTextSize((float) (12));
 //BA.debugLineNum = 341;BA.debugLine="lstFav.SingleLineLayout.Label.Gravity = Gravit";
mostCurrent._lstfav.getSingleLineLayout().Label.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.LEFT);
 //BA.debugLineNum = 347;BA.debugLine="fav=JSON.NextArray";
mostCurrent._vvvvvvvv3 = mostCurrent._vvvvvvv0.NextArray();
 //BA.debugLineNum = 348;BA.debugLine="lstFav.Clear";
mostCurrent._lstfav.Clear();
 //BA.debugLineNum = 350;BA.debugLine="For i = 0 To fav.Size-1";
{
final int step107 = 1;
final int limit107 = (int) (mostCurrent._vvvvvvvv3.getSize()-1);
for (_i = (int) (0) ; (step107 > 0 && _i <= limit107) || (step107 < 0 && _i >= limit107); _i = ((int)(0 + _i + step107)) ) {
 //BA.debugLineNum = 351;BA.debugLine="media = fav.Get(i)";
mostCurrent._vvvvvvvv2.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(mostCurrent._vvvvvvvv3.Get(_i)));
 //BA.debugLineNum = 352;BA.debugLine="lstFav.AddSingleLine( \"#\" & i & \": \" & media.";
mostCurrent._lstfav.AddSingleLine("#"+BA.NumberToString(_i)+": "+BA.ObjectToString(mostCurrent._vvvvvvvv2.Get((Object)("title"))));
 }
};
 //BA.debugLineNum = 354;BA.debugLine="pf.Visible=True";
mostCurrent._vvvvvvv3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 355;BA.debugLine="lblPLayingNow.Visible=False";
mostCurrent._lblplayingnow.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 break; }
case 14: {
 //BA.debugLineNum = 359;BA.debugLine="If Job.GetString.Contains(\"failed\") = False Th";
if (_job._vvvv5().contains("failed")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 360;BA.debugLine="JSON.Initialize(Job.GetString)";
mostCurrent._vvvvvvv0.Initialize(_job._vvvv5());
 //BA.debugLineNum = 361;BA.debugLine="js=JSON.NextObject";
mostCurrent._vvvvvvvv1 = mostCurrent._vvvvvvv0.NextObject();
 //BA.debugLineNum = 362;BA.debugLine="If js.Get(\"result\") = \"OK\" Then";
if ((mostCurrent._vvvvvvvv1.Get((Object)("result"))).equals((Object)("OK"))) { 
 //BA.debugLineNum = 363;BA.debugLine="lblPLayingNow.Visible=True";
mostCurrent._lblplayingnow.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 break; }
case 15: {
 //BA.debugLineNum = 368;BA.debugLine="lblPLayingNow.Text=Job.GetString";
mostCurrent._lblplayingnow.setText((Object)(_job._vvvv5()));
 break; }
}
;
 }else {
 //BA.debugLineNum = 374;BA.debugLine="Log(\"error\" & Job.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.Log("error"+_job._vvvvvv2);
 //BA.debugLineNum = 375;BA.debugLine="If Job.JobName.Contains(\"SPIF\") Then";
if (_job._vvvvv6.contains("SPIF")) { 
 //BA.debugLineNum = 376;BA.debugLine="rPI_SPDIF.Enabled=True";
mostCurrent._rpi_spdif.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else if(_job._vvvvv6.contains("DAC")) { 
 //BA.debugLineNum = 378;BA.debugLine="rPI_DAC.Enabled=True";
mostCurrent._rpi_dac.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else if(_job._vvvvv6.contains("SAM")) { 
 //BA.debugLineNum = 380;BA.debugLine="skBar.Enabled=True";
mostCurrent._skbar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 384;BA.debugLine="Job.Release";
_job._vvvvv5();
 //BA.debugLineNum = 387;BA.debugLine="End Sub";
return "";
}
public static String  _lstfav_itemclick(int _position,Object _value) throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 674;BA.debugLine="Sub lstFav_ItemClick (Position As Int, Value As Ob";
 //BA.debugLineNum = 675;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 676;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 678;BA.debugLine="url = BaseURL & \"/kodiPlay\"";
mostCurrent._vvvvvvv7 = mostCurrent._vvvvvvv6+"/kodiPlay";
 //BA.debugLineNum = 679;BA.debugLine="Job.Initialize(\"PlayFav\", Me)";
_job._initialize(processBA,"PlayFav",main.getObject());
 //BA.debugLineNum = 680;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv0 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 681;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvvv1 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 683;BA.debugLine="media=fav.Get(Position)";
mostCurrent._vvvvvvvv2.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(mostCurrent._vvvvvvvv3.Get(_position)));
 //BA.debugLineNum = 684;BA.debugLine="If media.Get(\"type\") = \"media\" Then";
if ((mostCurrent._vvvvvvvv2.Get((Object)("type"))).equals((Object)("media"))) { 
 //BA.debugLineNum = 685;BA.debugLine="Job.PostString(url,\"id=rPI_SPDIF&method=\" & medi";
_job._vvvvv2(mostCurrent._vvvvvvv7,"id=rPI_SPDIF&method="+BA.ObjectToString(mostCurrent._vvvvvvvv2.Get((Object)("path"))));
 //BA.debugLineNum = 686;BA.debugLine="lblPLayingNow.Text=media.Get(\"title\")";
mostCurrent._lblplayingnow.setText(mostCurrent._vvvvvvvv2.Get((Object)("title")));
 };
 //BA.debugLineNum = 688;BA.debugLine="pf.Visible=False";
mostCurrent._vvvvvvv3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 689;BA.debugLine="TabStrip1.ScrollTo(0, True)";
mostCurrent._tabstrip1.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 690;BA.debugLine="End Sub";
return "";
}
public static String  _lstfav_itemlongclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 692;BA.debugLine="Sub lstFav_ItemLongClick (Position As Int, Value A";
 //BA.debugLineNum = 694;BA.debugLine="End Sub";
return "";
}
public static String  _mnu1_click() throws Exception{
 //BA.debugLineNum = 197;BA.debugLine="Sub mnu1_Click";
 //BA.debugLineNum = 198;BA.debugLine="TabStrip1.ScrollTo(0, True)";
mostCurrent._tabstrip1.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 199;BA.debugLine="End Sub";
return "";
}
public static String  _mnu2_click() throws Exception{
 //BA.debugLineNum = 201;BA.debugLine="Sub mnu2_Click";
 //BA.debugLineNum = 202;BA.debugLine="TabStrip1.ScrollTo(1, True)";
mostCurrent._tabstrip1.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 203;BA.debugLine="End Sub";
return "";
}
public static String  _mnu3_click() throws Exception{
 //BA.debugLineNum = 205;BA.debugLine="Sub mnu3_Click";
 //BA.debugLineNum = 206;BA.debugLine="TabStrip1.ScrollTo(2, True)";
mostCurrent._tabstrip1.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 207;BA.debugLine="End Sub";
return "";
}
public static String  _mnusettings_click() throws Exception{
 //BA.debugLineNum = 209;BA.debugLine="Sub mnuSettings_Click";
 //BA.debugLineNum = 211;BA.debugLine="p.LoadLayout(\"Param\")";
mostCurrent._vvvvvvv2.LoadLayout("Param",mostCurrent.activityBA);
 //BA.debugLineNum = 212;BA.debugLine="p.Visible=True";
mostCurrent._vvvvvvv2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 213;BA.debugLine="End Sub";
return "";
}
public static String  _nas_click() throws Exception{
 //BA.debugLineNum = 530;BA.debugLine="Sub NAS_Click";
 //BA.debugLineNum = 532;BA.debugLine="End Sub";
return "";
}
public static String  _nas_longclick() throws Exception{
 //BA.debugLineNum = 534;BA.debugLine="Sub NAS_LongClick";
 //BA.debugLineNum = 536;BA.debugLine="End Sub";
return "";
}
public static String  _probe_all() throws Exception{
 //BA.debugLineNum = 183;BA.debugLine="Sub Probe_all";
 //BA.debugLineNum = 184;BA.debugLine="If sw_NAS.Tag = \"off\" Then";
if ((mostCurrent._sw_nas.getTag()).equals((Object)("off"))) { 
 //BA.debugLineNum = 185;BA.debugLine="Probe_device(\"NAS\")";
_probe_device("NAS");
 }else {
 //BA.debugLineNum = 187;BA.debugLine="Probe_linux(\"NAS\")";
_probe_linux("NAS");
 };
 //BA.debugLineNum = 189;BA.debugLine="Probe_device(\"SAM\")";
_probe_device("SAM");
 //BA.debugLineNum = 190;BA.debugLine="Probe_linux(\"rPI1\")";
_probe_linux("rPI1");
 //BA.debugLineNum = 191;BA.debugLine="Probe_kodi(\"rPI_SPDIF\")";
_probe_kodi("rPI_SPDIF");
 //BA.debugLineNum = 192;BA.debugLine="Probe_kodi(\"rPI_DAC\")";
_probe_kodi("rPI_DAC");
 //BA.debugLineNum = 194;BA.debugLine="Probe_kodiPlayer(\"rPI_SPDIF\")";
_probe_kodiplayer("rPI_SPDIF");
 //BA.debugLineNum = 195;BA.debugLine="End Sub";
return "";
}
public static String  _probe_device(String _id) throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 414;BA.debugLine="Sub Probe_device(id As String)";
 //BA.debugLineNum = 415;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 416;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 418;BA.debugLine="url = BaseURL & \"/tdstate\"";
mostCurrent._vvvvvvv7 = mostCurrent._vvvvvvv6+"/tdstate";
 //BA.debugLineNum = 419;BA.debugLine="Job.Initialize(\"pd\" & id, Me)";
_job._initialize(processBA,"pd"+_id,main.getObject());
 //BA.debugLineNum = 420;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv0 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 421;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvvv1 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 423;BA.debugLine="Job.Download(url & \"?id=\" & id)";
_job._vvv7(mostCurrent._vvvvvvv7+"?id="+_id);
 //BA.debugLineNum = 425;BA.debugLine="End Sub";
return "";
}
public static String  _probe_kodi(String _id) throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 389;BA.debugLine="Sub Probe_kodi(id As String)";
 //BA.debugLineNum = 390;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 391;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 393;BA.debugLine="url = BaseURL & \"/kodistate\"";
mostCurrent._vvvvvvv7 = mostCurrent._vvvvvvv6+"/kodistate";
 //BA.debugLineNum = 394;BA.debugLine="Job.Initialize(\"pk\" & id, Me)";
_job._initialize(processBA,"pk"+_id,main.getObject());
 //BA.debugLineNum = 395;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv0 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 396;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvvv1 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 398;BA.debugLine="Job.Download(url & \"?id=\" & id)";
_job._vvv7(mostCurrent._vvvvvvv7+"?id="+_id);
 //BA.debugLineNum = 400;BA.debugLine="End Sub";
return "";
}
public static String  _probe_kodiplayer(String _id) throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 402;BA.debugLine="Sub Probe_kodiPlayer(id As String)";
 //BA.debugLineNum = 403;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 404;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 406;BA.debugLine="url = BaseURL & \"/kodiPlay\"";
mostCurrent._vvvvvvv7 = mostCurrent._vvvvvvv6+"/kodiPlay";
 //BA.debugLineNum = 407;BA.debugLine="Job.Initialize(\"pkp\" & id, Me)";
_job._initialize(processBA,"pkp"+_id,main.getObject());
 //BA.debugLineNum = 408;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv0 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 409;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvvv1 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 411;BA.debugLine="Job.PostString(url,\"id=rPI_SPDIF&method=getcurren";
_job._vvvvv2(mostCurrent._vvvvvvv7,"id=rPI_SPDIF&method=getcurrentmedia");
 //BA.debugLineNum = 412;BA.debugLine="End Sub";
return "";
}
public static String  _probe_linux(String _id) throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 427;BA.debugLine="Sub Probe_linux(id As String)";
 //BA.debugLineNum = 428;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 429;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 431;BA.debugLine="url = BaseURL & \"/ping\"";
mostCurrent._vvvvvvv7 = mostCurrent._vvvvvvv6+"/ping";
 //BA.debugLineNum = 432;BA.debugLine="Job.Initialize(\"pl\" & id, Me)";
_job._initialize(processBA,"pl"+_id,main.getObject());
 //BA.debugLineNum = 433;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv0 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 434;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvvv1 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 436;BA.debugLine="Job.PostString(url, \"id=\" & id)";
_job._vvvvv2(mostCurrent._vvvvvvv7,"id="+_id);
 //BA.debugLineNum = 438;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 21;BA.debugLine="Dim Timer1 As Timer";
_vvv5 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public static String  _rpi_1_click() throws Exception{
 //BA.debugLineNum = 666;BA.debugLine="Sub rpi_1_Click";
 //BA.debugLineNum = 668;BA.debugLine="End Sub";
return "";
}
public static String  _rpi_1_longclick() throws Exception{
 //BA.debugLineNum = 670;BA.debugLine="Sub rpi_1_LongClick";
 //BA.debugLineNum = 672;BA.debugLine="End Sub";
return "";
}
public static String  _rpi_dac_click() throws Exception{
 //BA.debugLineNum = 635;BA.debugLine="Sub rPI_DAC_Click";
 //BA.debugLineNum = 637;BA.debugLine="End Sub";
return "";
}
public static String  _rpi_dac_longclick() throws Exception{
 //BA.debugLineNum = 639;BA.debugLine="Sub rPI_DAC_LongClick";
 //BA.debugLineNum = 642;BA.debugLine="End Sub";
return "";
}
public static String  _rpi_spdif_click() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 561;BA.debugLine="Sub rPI_SPDIF_Click";
 //BA.debugLineNum = 562;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 563;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 565;BA.debugLine="url = BaseURL & \"/kodiPlay\"";
mostCurrent._vvvvvvv7 = mostCurrent._vvvvvvv6+"/kodiPlay";
 //BA.debugLineNum = 566;BA.debugLine="Job.Initialize(\"Fav\", Me)";
_job._initialize(processBA,"Fav",main.getObject());
 //BA.debugLineNum = 567;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv0 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 568;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvvv1 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 570;BA.debugLine="Job.PostString(url,\"id=rPI_SPDIF&method=favourite";
_job._vvvvv2(mostCurrent._vvvvvvv7,"id=rPI_SPDIF&method=favourites");
 //BA.debugLineNum = 571;BA.debugLine="End Sub";
return "";
}
public static String  _rpi_spdif_longclick() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 573;BA.debugLine="Sub rPI_SPDIF_LongClick";
 //BA.debugLineNum = 574;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 575;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 577;BA.debugLine="url = BaseURL & \"/kodiPlay\"";
mostCurrent._vvvvvvv7 = mostCurrent._vvvvvvv6+"/kodiPlay";
 //BA.debugLineNum = 578;BA.debugLine="Job.Initialize(\"\", Me)";
_job._initialize(processBA,"",main.getObject());
 //BA.debugLineNum = 579;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv0 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 580;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvvv1 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 581;BA.debugLine="Job.PostString(url,\"id=rPI_SPDIF&method=stop\")";
_job._vvvvv2(mostCurrent._vvvvvvv7,"id=rPI_SPDIF&method=stop");
 //BA.debugLineNum = 582;BA.debugLine="lblPLayingNow.Text = \"\"";
mostCurrent._lblplayingnow.setText((Object)(""));
 //BA.debugLineNum = 583;BA.debugLine="End Sub";
return "";
}
public static String  _skbar_valuechanged(int _value,boolean _userchanged) throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 515;BA.debugLine="Sub skBar_ValueChanged (Value As Int, UserChanged";
 //BA.debugLineNum = 516;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 517;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 519;BA.debugLine="If Value < 10 Then Value = 0";
if (_value<10) { 
_value = (int) (0);};
 //BA.debugLineNum = 521;BA.debugLine="url = BaseURL & \"/tdcmd\"";
mostCurrent._vvvvvvv7 = mostCurrent._vvvvvvv6+"/tdcmd";
 //BA.debugLineNum = 522;BA.debugLine="Job.Initialize(\"SAM\", Me)";
_job._initialize(processBA,"SAM",main.getObject());
 //BA.debugLineNum = 523;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv0 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 524;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvvv1 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 525;BA.debugLine="Job.PostString(url,\"id=SAM&method=DIM&value=\" & V";
_job._vvvvv2(mostCurrent._vvvvvvv7,"id=SAM&method=DIM&value="+BA.NumberToString(_value));
 //BA.debugLineNum = 526;BA.debugLine="skBar.Enabled=False";
mostCurrent._skbar.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 528;BA.debugLine="End Sub";
return "";
}
public static String  _sw_dac_click() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 599;BA.debugLine="Sub sw_DAC_Click";
 //BA.debugLineNum = 600;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 601;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 603;BA.debugLine="url = BaseURL & \"/kodi\"";
mostCurrent._vvvvvvv7 = mostCurrent._vvvvvvv6+"/kodi";
 //BA.debugLineNum = 604;BA.debugLine="Job.Initialize(\"rPIDAC\", Me)";
_job._initialize(processBA,"rPIDAC",main.getObject());
 //BA.debugLineNum = 605;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv0 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 606;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvvv1 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 607;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"kodiYe";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiYellow.png").getObject()));
 //BA.debugLineNum = 608;BA.debugLine="If sw_DAC.Tag=\"on\" Then";
if ((mostCurrent._sw_dac.getTag()).equals((Object)("on"))) { 
 //BA.debugLineNum = 609;BA.debugLine="Job.PostString(url,\"id=rPI_DAC&method=OFF\")";
_job._vvvvv2(mostCurrent._vvvvvvv7,"id=rPI_DAC&method=OFF");
 //BA.debugLineNum = 610;BA.debugLine="sw_DAC.Bitmap=LoadBitmap(File.DirAssets, \"off.pn";
mostCurrent._sw_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"off.png").getObject()));
 //BA.debugLineNum = 611;BA.debugLine="sw_DAC.Tag=\"off\"";
mostCurrent._sw_dac.setTag((Object)("off"));
 }else {
 //BA.debugLineNum = 613;BA.debugLine="Job.PostString(url,\"id=rPI_DAC&method=ON\")";
_job._vvvvv2(mostCurrent._vvvvvvv7,"id=rPI_DAC&method=ON");
 //BA.debugLineNum = 614;BA.debugLine="sw_DAC.Bitmap=LoadBitmap(File.DirAssets, \"on.png";
mostCurrent._sw_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"on.png").getObject()));
 //BA.debugLineNum = 615;BA.debugLine="sw_DAC.Tag=\"on\"";
mostCurrent._sw_dac.setTag((Object)("on"));
 };
 //BA.debugLineNum = 617;BA.debugLine="Job.GetRequest.Timeout=100000";
_job._vvvv4().setTimeout((int) (100000));
 //BA.debugLineNum = 619;BA.debugLine="End Sub";
return "";
}
public static String  _sw_dac_longclick() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 621;BA.debugLine="Sub sw_DAC_LongClick";
 //BA.debugLineNum = 622;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 623;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 625;BA.debugLine="url = BaseURL & \"/tdcmd\"";
mostCurrent._vvvvvvv7 = mostCurrent._vvvvvvv6+"/tdcmd";
 //BA.debugLineNum = 626;BA.debugLine="Job.Initialize(\"prPI_DAC\", Me)";
_job._initialize(processBA,"prPI_DAC",main.getObject());
 //BA.debugLineNum = 627;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv0 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 628;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvvv1 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 629;BA.debugLine="rPI_DAC.Bitmap=LoadBitmap(File.DirAssets, \"kodiGr";
mostCurrent._rpi_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 //BA.debugLineNum = 630;BA.debugLine="sw_DAC.Bitmap=LoadBitmap(File.DirAssets,\"off.png\"";
mostCurrent._sw_dac.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"off.png").getObject()));
 //BA.debugLineNum = 631;BA.debugLine="sw_DAC.Tag=\"off\"";
mostCurrent._sw_dac.setTag((Object)("off"));
 //BA.debugLineNum = 632;BA.debugLine="Job.PostString(url,\"id=rPI_DAC&method=OFF\")";
_job._vvvvv2(mostCurrent._vvvvvvv7,"id=rPI_DAC&method=OFF");
 //BA.debugLineNum = 633;BA.debugLine="End Sub";
return "";
}
public static String  _sw_nas_click() throws Exception{
 //BA.debugLineNum = 644;BA.debugLine="Sub sw_NAS_Click";
 //BA.debugLineNum = 646;BA.debugLine="End Sub";
return "";
}
public static String  _sw_nas_longclick() throws Exception{
 //BA.debugLineNum = 648;BA.debugLine="Sub sw_NAS_LongClick";
 //BA.debugLineNum = 650;BA.debugLine="End Sub";
return "";
}
public static String  _sw_rpi1_click() throws Exception{
 //BA.debugLineNum = 652;BA.debugLine="Sub sw_rpi1_Click";
 //BA.debugLineNum = 653;BA.debugLine="If sw_rpi1.Tag = \"off\" Then";
if ((mostCurrent._sw_rpi1.getTag()).equals((Object)("off"))) { 
 //BA.debugLineNum = 654;BA.debugLine="sw_rpi1.Tag=\"on\"";
mostCurrent._sw_rpi1.setTag((Object)("on"));
 //BA.debugLineNum = 655;BA.debugLine="sw_rpi1.Bitmap=LoadBitmap(File.DirAssets, \"on.pn";
mostCurrent._sw_rpi1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"on.png").getObject()));
 }else {
 //BA.debugLineNum = 657;BA.debugLine="sw_rpi1.Tag=\"off\"";
mostCurrent._sw_rpi1.setTag((Object)("off"));
 //BA.debugLineNum = 658;BA.debugLine="sw_rpi1.Bitmap=LoadBitmap(File.DirAssets, \"off.p";
mostCurrent._sw_rpi1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"off.png").getObject()));
 };
 //BA.debugLineNum = 660;BA.debugLine="End Sub";
return "";
}
public static String  _sw_rpi1_longclick() throws Exception{
 //BA.debugLineNum = 662;BA.debugLine="Sub sw_rpi1_LongClick";
 //BA.debugLineNum = 664;BA.debugLine="End Sub";
return "";
}
public static String  _sw_spdif_click() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 538;BA.debugLine="Sub sw_SPDIF_Click";
 //BA.debugLineNum = 539;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 540;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 542;BA.debugLine="url = BaseURL & \"/kodi\"";
mostCurrent._vvvvvvv7 = mostCurrent._vvvvvvv6+"/kodi";
 //BA.debugLineNum = 543;BA.debugLine="Job.Initialize(\"rPISPDIF\", Me)";
_job._initialize(processBA,"rPISPDIF",main.getObject());
 //BA.debugLineNum = 545;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv0 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 546;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvvv1 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 547;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"kodi";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiYellow.png").getObject()));
 //BA.debugLineNum = 548;BA.debugLine="If sw_SPDIF.Tag=\"on\" Then";
if ((mostCurrent._sw_spdif.getTag()).equals((Object)("on"))) { 
 //BA.debugLineNum = 549;BA.debugLine="Job.PostString(url,\"id=rPI_SPDIF&method=OFF\")";
_job._vvvvv2(mostCurrent._vvvvvvv7,"id=rPI_SPDIF&method=OFF");
 //BA.debugLineNum = 550;BA.debugLine="sw_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"off.";
mostCurrent._sw_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"off.png").getObject()));
 //BA.debugLineNum = 551;BA.debugLine="sw_SPDIF.Tag=\"off\"";
mostCurrent._sw_spdif.setTag((Object)("off"));
 }else {
 //BA.debugLineNum = 553;BA.debugLine="Job.PostString(url,\"id=rPI_SPDIF&method=ON\")";
_job._vvvvv2(mostCurrent._vvvvvvv7,"id=rPI_SPDIF&method=ON");
 //BA.debugLineNum = 554;BA.debugLine="sw_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"on.p";
mostCurrent._sw_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"on.png").getObject()));
 //BA.debugLineNum = 555;BA.debugLine="sw_SPDIF.Tag=\"on\"";
mostCurrent._sw_spdif.setTag((Object)("on"));
 };
 //BA.debugLineNum = 557;BA.debugLine="Job.GetRequest.Timeout=100000";
_job._vvvv4().setTimeout((int) (100000));
 //BA.debugLineNum = 559;BA.debugLine="End Sub";
return "";
}
public static String  _sw_spdif_longclick() throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 585;BA.debugLine="Sub sw_SPDIF_LongClick";
 //BA.debugLineNum = 586;BA.debugLine="Dim Job As HttpJob";
_job = new be.mayeur.home.homemon.httpjob();
 //BA.debugLineNum = 587;BA.debugLine="Dim url As String";
mostCurrent._vvvvvvv7 = "";
 //BA.debugLineNum = 589;BA.debugLine="url = BaseURL & \"/tdcmd\"";
mostCurrent._vvvvvvv7 = mostCurrent._vvvvvvv6+"/tdcmd";
 //BA.debugLineNum = 590;BA.debugLine="Job.Initialize(\"pkrPISPDIF\", Me)";
_job._initialize(processBA,"pkrPISPDIF",main.getObject());
 //BA.debugLineNum = 591;BA.debugLine="Job.Username =txtUser.Text";
_job._vvvvv0 = mostCurrent._txtuser.getText();
 //BA.debugLineNum = 592;BA.debugLine="Job.Password =txtPassword.Text";
_job._vvvvvv1 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 593;BA.debugLine="rPI_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"kodi";
mostCurrent._rpi_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kodiGrey.png").getObject()));
 //BA.debugLineNum = 594;BA.debugLine="sw_SPDIF.Bitmap=LoadBitmap(File.DirAssets, \"off.p";
mostCurrent._sw_spdif.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"off.png").getObject()));
 //BA.debugLineNum = 595;BA.debugLine="sw_SPDIF.Tag=\"off\"";
mostCurrent._sw_spdif.setTag((Object)("off"));
 //BA.debugLineNum = 596;BA.debugLine="Job.PostString(url,\"id=rPI_SPDIF&method=OFF\")";
_job._vvvvv2(mostCurrent._vvvvvvv7,"id=rPI_SPDIF&method=OFF");
 //BA.debugLineNum = 597;BA.debugLine="End Sub";
return "";
}
public static String  _tabstrip1_pageselected(int _position) throws Exception{
 //BA.debugLineNum = 215;BA.debugLine="Sub TabStrip1_PageSelected (Position As Int)";
 //BA.debugLineNum = 216;BA.debugLine="Log($\"Current page: ${Position}\"$)";
anywheresoftware.b4a.keywords.Common.Log(("Current page: "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_position))+""));
 //BA.debugLineNum = 217;BA.debugLine="If Position=1 Then";
if (_position==1) { 
 //BA.debugLineNum = 218;BA.debugLine="btnSpam_Click";
_btnspam_click();
 }else {
 //BA.debugLineNum = 220;BA.debugLine="txtResult.Text=\"\"";
mostCurrent._txtresult.setText((Object)(""));
 };
 //BA.debugLineNum = 222;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 179;BA.debugLine="Sub Timer1_Tick";
 //BA.debugLineNum = 180;BA.debugLine="Probe_all";
_probe_all();
 //BA.debugLineNum = 181;BA.debugLine="End Sub";
return "";
}
public static String[]  _webview1_userandpasswordrequired(String _host,String _realm) throws Exception{
 //BA.debugLineNum = 499;BA.debugLine="Sub WebView1_UserAndPasswordRequired (Host As Stri";
 //BA.debugLineNum = 500;BA.debugLine="Return Array As String(txtUser.Text, txtPassword.";
if (true) return new String[]{mostCurrent._txtuser.getText(),mostCurrent._txtpassword.getText()};
 //BA.debugLineNum = 501;BA.debugLine="End Sub";
return null;
}
}
