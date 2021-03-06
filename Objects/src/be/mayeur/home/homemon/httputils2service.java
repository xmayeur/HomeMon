package be.mayeur.home.homemon;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class httputils2service extends  android.app.Service{
	public static class httputils2service_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, httputils2service.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static httputils2service mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return httputils2service.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "be.mayeur.home.homemon", "be.mayeur.home.homemon.httputils2service");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "be.mayeur.home.homemon.httputils2service", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!false && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, true) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("** Service (httputils2service) Create **");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (false) {
			if (ServiceHelper.StarterHelper.waitForLayout != null)
				BA.handler.post(ServiceHelper.StarterHelper.waitForLayout);
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA))
			handleStart(intent);
		else {
			ServiceHelper.StarterHelper.waitForLayout = new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (httputils2service) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
				}
			};
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (false)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (httputils2service) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = new anywheresoftware.b4a.objects.IntentWrapper();
    			if (intent != null) {
    				if (intent.hasExtra("b4a_internal_intent"))
    					iw.setObject((android.content.Intent) intent.getParcelableExtra("b4a_internal_intent"));
    				else
    					iw.setObject(intent);
    			}
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	
	@Override
	public void onDestroy() {
        super.onDestroy();
        BA.LogInfo("** Service (httputils2service) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
        processBA.runHook("ondestroy", this, null);
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4h.okhttp.OkHttpClientWrapper _vvvvvvvv6 = null;
public static anywheresoftware.b4a.objects.collections.Map _vvvvvvvv5 = null;
public static String _v5 = "";
public static int _vvvvvvvv7 = 0;
public be.mayeur.home.homemon.main _vvvvvv7 = null;
public be.mayeur.home.homemon.statemanager _vvvvvvv1 = null;
public static String  _vvvvvvvv4(int _taskid,boolean _success,String _errormessage) throws Exception{
be.mayeur.home.homemon.httpjob _job = null;
 //BA.debugLineNum = 69;BA.debugLine="Sub CompleteJob(TaskId As Int, success As Boolean,";
 //BA.debugLineNum = 70;BA.debugLine="Dim job As HttpJob = TaskIdToJob.Get(TaskId)";
_job = (be.mayeur.home.homemon.httpjob)(_vvvvvvvv5.Get((Object)(_taskid)));
 //BA.debugLineNum = 71;BA.debugLine="TaskIdToJob.Remove(TaskId)";
_vvvvvvvv5.Remove((Object)(_taskid));
 //BA.debugLineNum = 72;BA.debugLine="job.success = success";
_job._vvvvv7 = _success;
 //BA.debugLineNum = 73;BA.debugLine="job.errorMessage = errorMessage";
_job._vvvvvv2 = _errormessage;
 //BA.debugLineNum = 74;BA.debugLine="job.Complete(TaskId)";
_job._vvv6(_taskid);
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responseerror(anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,String _reason,int _statuscode,int _taskid) throws Exception{
 //BA.debugLineNum = 61;BA.debugLine="Sub hc_ResponseError (Response As OkHttpResponse,";
 //BA.debugLineNum = 62;BA.debugLine="If Response <> Null Then";
if (_response!= null) { 
 //BA.debugLineNum = 63;BA.debugLine="Log(Response.ErrorResponse)";
anywheresoftware.b4a.keywords.Common.Log(_response.getErrorResponse());
 //BA.debugLineNum = 64;BA.debugLine="Response.Release";
_response.Release();
 };
 //BA.debugLineNum = 66;BA.debugLine="CompleteJob(TaskId, False, Reason)";
_vvvvvvvv4(_taskid,anywheresoftware.b4a.keywords.Common.False,_reason);
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responsesuccess(anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpResponse _response,int _taskid) throws Exception{
 //BA.debugLineNum = 47;BA.debugLine="Sub hc_ResponseSuccess (Response As OkHttpResponse";
 //BA.debugLineNum = 48;BA.debugLine="Response.GetAsynchronously(\"response\", File.OpenO";
_response.GetAsynchronously(processBA,"response",(java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(_v5,BA.NumberToString(_taskid),anywheresoftware.b4a.keywords.Common.False).getObject()),anywheresoftware.b4a.keywords.Common.True,_taskid);
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Private hc As OkHttpClient";
_vvvvvvvv6 = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper();
 //BA.debugLineNum = 9;BA.debugLine="Private TaskIdToJob As Map";
_vvvvvvvv5 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 10;BA.debugLine="Public TempFolder As String";
_v5 = "";
 //BA.debugLineNum = 11;BA.debugLine="Private taskCounter As Int";
_vvvvvvvv7 = 0;
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public static String  _response_streamfinish(boolean _success,int _taskid) throws Exception{
 //BA.debugLineNum = 52;BA.debugLine="Sub Response_StreamFinish (Success As Boolean, Tas";
 //BA.debugLineNum = 53;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 54;BA.debugLine="CompleteJob(TaskId, Success, \"\")";
_vvvvvvvv4(_taskid,_success,"");
 }else {
 //BA.debugLineNum = 56;BA.debugLine="CompleteJob(TaskId, Success, LastException.Messa";
_vvvvvvvv4(_taskid,_success,anywheresoftware.b4a.keywords.Common.LastException(processBA).getMessage());
 };
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
anywheresoftware.b4a.objects.SocketWrapper.ServerSocketWrapper _mylan = null;
 //BA.debugLineNum = 15;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 16;BA.debugLine="Dim MyLan As ServerSocket";
_mylan = new anywheresoftware.b4a.objects.SocketWrapper.ServerSocketWrapper();
 //BA.debugLineNum = 17;BA.debugLine="TempFolder = File.DirInternalCache";
_v5 = anywheresoftware.b4a.keywords.Common.File.getDirInternalCache();
 //BA.debugLineNum = 18;BA.debugLine="MyLan.Initialize(0, \"\")";
_mylan.Initialize(processBA,(int) (0),"");
 //BA.debugLineNum = 19;BA.debugLine="If Main.AtHome Then";
if (mostCurrent._vvvvvv7._vvv4) { 
 //BA.debugLineNum = 20;BA.debugLine="hc.InitializeAcceptAll(\"hc\")";
_vvvvvvvv6.InitializeAcceptAll("hc");
 }else {
 //BA.debugLineNum = 22;BA.debugLine="hc.Initialize(\"hc\")";
_vvvvvvvv6.Initialize("hc");
 };
 //BA.debugLineNum = 26;BA.debugLine="TaskIdToJob.Initialize";
_vvvvvvvv5.Initialize();
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public static int  _submitjob(be.mayeur.home.homemon.httpjob _job) throws Exception{
 //BA.debugLineNum = 36;BA.debugLine="Public Sub SubmitJob(job As HttpJob) As Int";
 //BA.debugLineNum = 37;BA.debugLine="taskCounter = taskCounter + 1";
_vvvvvvvv7 = (int) (_vvvvvvvv7+1);
 //BA.debugLineNum = 38;BA.debugLine="TaskIdToJob.Put(taskCounter, job)";
_vvvvvvvv5.Put((Object)(_vvvvvvvv7),(Object)(_job));
 //BA.debugLineNum = 39;BA.debugLine="If job.Username <> \"\" And job.Password <> \"\" Then";
if ((_job._vvvvv0).equals("") == false && (_job._vvvvvv1).equals("") == false) { 
 //BA.debugLineNum = 40;BA.debugLine="hc.ExecuteCredentials(job.GetRequest, taskCounte";
_vvvvvvvv6.ExecuteCredentials(processBA,_job._vvvv4(),_vvvvvvvv7,_job._vvvvv0,_job._vvvvvv1);
 }else {
 //BA.debugLineNum = 42;BA.debugLine="hc.Execute(job.GetRequest, taskCounter)";
_vvvvvvvv6.Execute(processBA,_job._vvvv4(),_vvvvvvvv7);
 };
 //BA.debugLineNum = 44;BA.debugLine="Return taskCounter";
if (true) return _vvvvvvvv7;
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return 0;
}
}
