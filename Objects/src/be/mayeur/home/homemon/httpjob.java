package be.mayeur.home.homemon;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class httpjob extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "be.mayeur.home.homemon.httpjob");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", be.mayeur.home.homemon.httpjob.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public String _vvvvv5 = "";
public boolean _vvvvv6 = false;
public String _vvvvv7 = "";
public String _vvvvv0 = "";
public String _vvvvvv1 = "";
public Object _vvvvvv2 = null;
public String _vvvvvv3 = "";
public anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest _vvvvvv4 = null;
public Object _vvvvvv5 = null;
public be.mayeur.home.homemon.main _vvvvvv6 = null;
public be.mayeur.home.homemon.httputils2service _vvvvvv7 = null;
public be.mayeur.home.homemon.statemanager _vvvvvv0 = null;
public static class _multipartfiledata{
public boolean IsInitialized;
public String Dir;
public String FileName;
public String KeyName;
public String ContentType;
public void Initialize() {
IsInitialized = true;
Dir = "";
FileName = "";
KeyName = "";
ContentType = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 4;BA.debugLine="Public JobName As String";
_vvvvv5 = "";
 //BA.debugLineNum = 5;BA.debugLine="Public Success As Boolean";
_vvvvv6 = false;
 //BA.debugLineNum = 6;BA.debugLine="Public Username, Password As String";
_vvvvv7 = "";
_vvvvv0 = "";
 //BA.debugLineNum = 7;BA.debugLine="Public ErrorMessage As String";
_vvvvvv1 = "";
 //BA.debugLineNum = 8;BA.debugLine="Private target As Object";
_vvvvvv2 = new Object();
 //BA.debugLineNum = 9;BA.debugLine="Private taskId As String";
_vvvvvv3 = "";
 //BA.debugLineNum = 10;BA.debugLine="Private req As OkHttpRequest";
_vvvvvv4 = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest();
 //BA.debugLineNum = 11;BA.debugLine="Public Tag As Object";
_vvvvvv5 = new Object();
 //BA.debugLineNum = 12;BA.debugLine="Type MultipartFileData (Dir As String, FileName A";
;
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public String  _vvv5(int _id) throws Exception{
 //BA.debugLineNum = 150;BA.debugLine="Public Sub Complete (id As Int)";
 //BA.debugLineNum = 151;BA.debugLine="taskId = id";
_vvvvvv3 = BA.NumberToString(_id);
 //BA.debugLineNum = 152;BA.debugLine="CallSubDelayed2(target, \"JobDone\", Me)";
__c.CallSubDelayed2(getActivityBA(),_vvvvvv2,"JobDone",this);
 //BA.debugLineNum = 153;BA.debugLine="End Sub";
return "";
}
public String  _vvv6(String _link) throws Exception{
 //BA.debugLineNum = 119;BA.debugLine="Public Sub Download(Link As String)";
 //BA.debugLineNum = 120;BA.debugLine="req.InitializeGet(Link)";
_vvvvvv4.InitializeGet(_link);
 //BA.debugLineNum = 121;BA.debugLine="CallSubDelayed2(HttpUtils2Service, \"SubmitJob\", M";
__c.CallSubDelayed2(getActivityBA(),(Object)(_vvvvvv7.getObject()),"SubmitJob",this);
 //BA.debugLineNum = 122;BA.debugLine="End Sub";
return "";
}
public String  _vvv7(String _link,String[] _parameters) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.objects.StringUtils _su = null;
int _i = 0;
 //BA.debugLineNum = 129;BA.debugLine="Public Sub Download2(Link As String, Parameters()";
 //BA.debugLineNum = 130;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 131;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 132;BA.debugLine="sb.Append(Link)";
_sb.Append(_link);
 //BA.debugLineNum = 133;BA.debugLine="If Parameters.Length > 0 Then sb.Append(\"?\")";
if (_parameters.length>0) { 
_sb.Append("?");};
 //BA.debugLineNum = 134;BA.debugLine="Dim su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 135;BA.debugLine="For i = 0 To Parameters.Length - 1 Step 2";
{
final int step6 = (int) (2);
final int limit6 = (int) (_parameters.length-1);
for (_i = (int) (0) ; (step6 > 0 && _i <= limit6) || (step6 < 0 && _i >= limit6); _i = ((int)(0 + _i + step6)) ) {
 //BA.debugLineNum = 136;BA.debugLine="If i > 0 Then sb.Append(\"&\")";
if (_i>0) { 
_sb.Append("&");};
 //BA.debugLineNum = 137;BA.debugLine="sb.Append(su.EncodeUrl(Parameters(i), \"UTF8\")).A";
_sb.Append(_su.EncodeUrl(_parameters[_i],"UTF8")).Append("=");
 //BA.debugLineNum = 138;BA.debugLine="sb.Append(su.EncodeUrl(Parameters(i + 1), \"UTF8\"";
_sb.Append(_su.EncodeUrl(_parameters[(int) (_i+1)],"UTF8"));
 }
};
 //BA.debugLineNum = 140;BA.debugLine="req.InitializeGet(sb.ToString)";
_vvvvvv4.InitializeGet(_sb.ToString());
 //BA.debugLineNum = 141;BA.debugLine="CallSubDelayed2(HttpUtils2Service, \"SubmitJob\", M";
__c.CallSubDelayed2(getActivityBA(),(Object)(_vvvvvv7.getObject()),"SubmitJob",this);
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _vvv0() throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _b = null;
 //BA.debugLineNum = 176;BA.debugLine="Public Sub GetBitmap As Bitmap";
 //BA.debugLineNum = 177;BA.debugLine="Dim b As Bitmap";
_b = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 178;BA.debugLine="b = LoadBitmap(HttpUtils2Service.TempFolder, task";
_b = __c.LoadBitmap(_vvvvvv7._v5,_vvvvvv3);
 //BA.debugLineNum = 179;BA.debugLine="Return b";
if (true) return _b;
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _vvvv1(int _width,int _height) throws Exception{
 //BA.debugLineNum = 182;BA.debugLine="Public Sub GetBitmapSample(Width As Int, Height As";
 //BA.debugLineNum = 183;BA.debugLine="Return LoadBitmapSample(HttpUtils2Service.TempFol";
if (true) return __c.LoadBitmapSample(_vvvvvv7._v5,_vvvvvv3,_width,_height);
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.streams.File.InputStreamWrapper  _vvvv2() throws Exception{
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _in = null;
 //BA.debugLineNum = 186;BA.debugLine="Public Sub GetInputStream As InputStream";
 //BA.debugLineNum = 187;BA.debugLine="Dim In As InputStream";
_in = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();
 //BA.debugLineNum = 188;BA.debugLine="In = File.OpenInput(HttpUtils2Service.TempFolder,";
_in = __c.File.OpenInput(_vvvvvv7._v5,_vvvvvv3);
 //BA.debugLineNum = 189;BA.debugLine="Return In";
if (true) return _in;
 //BA.debugLineNum = 190;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest  _vvvv3() throws Exception{
 //BA.debugLineNum = 145;BA.debugLine="Public Sub GetRequest As OkHttpRequest";
 //BA.debugLineNum = 146;BA.debugLine="Return req";
if (true) return _vvvvvv4;
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return null;
}
public String  _vvvv4() throws Exception{
 //BA.debugLineNum = 161;BA.debugLine="Public Sub GetString As String";
 //BA.debugLineNum = 162;BA.debugLine="Return GetString2(\"UTF8\")";
if (true) return _vvvv5("UTF8");
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public String  _vvvv5(String _encoding) throws Exception{
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _tr = null;
String _res = "";
 //BA.debugLineNum = 166;BA.debugLine="Public Sub GetString2(Encoding As String) As Strin";
 //BA.debugLineNum = 167;BA.debugLine="Dim tr As TextReader";
_tr = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 168;BA.debugLine="tr.Initialize2(File.OpenInput(HttpUtils2Service.T";
_tr.Initialize2((java.io.InputStream)(__c.File.OpenInput(_vvvvvv7._v5,_vvvvvv3).getObject()),_encoding);
 //BA.debugLineNum = 169;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 170;BA.debugLine="res = tr.ReadAll";
_res = _tr.ReadAll();
 //BA.debugLineNum = 171;BA.debugLine="tr.Close";
_tr.Close();
 //BA.debugLineNum = 172;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,String _name,Object _targetmodule) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 18;BA.debugLine="Public Sub Initialize (Name As String, TargetModul";
 //BA.debugLineNum = 19;BA.debugLine="JobName = Name";
_vvvvv5 = _name;
 //BA.debugLineNum = 20;BA.debugLine="target = TargetModule";
_vvvvvv2 = _targetmodule;
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public String  _vvvv6(String _link,byte[] _data) throws Exception{
 //BA.debugLineNum = 28;BA.debugLine="Public Sub PostBytes(Link As String, Data() As Byt";
 //BA.debugLineNum = 29;BA.debugLine="req.InitializePost2(Link, Data)";
_vvvvvv4.InitializePost2(_link,_data);
 //BA.debugLineNum = 30;BA.debugLine="CallSubDelayed2(HttpUtils2Service, \"SubmitJob\", M";
__c.CallSubDelayed2(getActivityBA(),(Object)(_vvvvvv7.getObject()),"SubmitJob",this);
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public String  _vvvv7(String _link,String _dir,String _filename) throws Exception{
int _length = 0;
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _in = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 96;BA.debugLine="Public Sub PostFile(Link As String, Dir As String,";
 //BA.debugLineNum = 97;BA.debugLine="Dim length As Int";
_length = 0;
 //BA.debugLineNum = 98;BA.debugLine="If Dir = File.DirAssets Then";
if ((_dir).equals(__c.File.getDirAssets())) { 
 //BA.debugLineNum = 99;BA.debugLine="Log(\"Cannot send files from the assets folder.\")";
__c.Log("Cannot send files from the assets folder.");
 //BA.debugLineNum = 100;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 102;BA.debugLine="length = File.Size(Dir, FileName)";
_length = (int) (__c.File.Size(_dir,_filename));
 //BA.debugLineNum = 103;BA.debugLine="Dim In As InputStream";
_in = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();
 //BA.debugLineNum = 104;BA.debugLine="In = File.OpenInput(Dir, FileName)";
_in = __c.File.OpenInput(_dir,_filename);
 //BA.debugLineNum = 105;BA.debugLine="If length < 1000000 Then '1mb";
if (_length<1000000) { 
 //BA.debugLineNum = 108;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 109;BA.debugLine="out.InitializeToBytesArray(length)";
_out.InitializeToBytesArray(_length);
 //BA.debugLineNum = 110;BA.debugLine="File.Copy2(In, out)";
__c.File.Copy2((java.io.InputStream)(_in.getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 111;BA.debugLine="PostBytes(Link, out.ToBytesArray)";
_vvvv6(_link,_out.ToBytesArray());
 }else {
 //BA.debugLineNum = 113;BA.debugLine="req.InitializePost(Link, In, length)";
_vvvvvv4.InitializePost(_link,(java.io.InputStream)(_in.getObject()),_length);
 //BA.debugLineNum = 114;BA.debugLine="CallSubDelayed2(HttpUtils2Service, \"SubmitJob\",";
__c.CallSubDelayed2(getActivityBA(),(Object)(_vvvvvv7.getObject()),"SubmitJob",this);
 };
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return "";
}
public String  _vvvv0(String _link,anywheresoftware.b4a.objects.collections.Map _namevalues,anywheresoftware.b4a.objects.collections.List _files) throws Exception{
String _boundary = "";
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _stream = null;
byte[] _b = null;
String _eol = "";
String _key = "";
String _value = "";
String _s = "";
be.mayeur.home.homemon.httpjob._multipartfiledata _fd = null;
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _in = null;
 //BA.debugLineNum = 47;BA.debugLine="public Sub PostMultipart(Link As String, NameValue";
 //BA.debugLineNum = 48;BA.debugLine="Dim boundary As String = \"-----------------------";
_boundary = "---------------------------1461124740692";
 //BA.debugLineNum = 49;BA.debugLine="Dim stream As OutputStream";
_stream = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 50;BA.debugLine="stream.InitializeToBytesArray(0)";
_stream.InitializeToBytesArray((int) (0));
 //BA.debugLineNum = 51;BA.debugLine="Dim b() As Byte";
_b = new byte[(int) (0)];
;
 //BA.debugLineNum = 52;BA.debugLine="Dim eol As String = Chr(13) & Chr(10)";
_eol = BA.ObjectToString(__c.Chr((int) (13)))+BA.ObjectToString(__c.Chr((int) (10)));
 //BA.debugLineNum = 53;BA.debugLine="If NameValues <> Null And NameValues.IsInitialize";
if (_namevalues!= null && _namevalues.IsInitialized()) { 
 //BA.debugLineNum = 54;BA.debugLine="For Each key As String In NameValues.Keys";
final anywheresoftware.b4a.BA.IterableList group7 = _namevalues.Keys();
final int groupLen7 = group7.getSize();
for (int index7 = 0;index7 < groupLen7 ;index7++){
_key = BA.ObjectToString(group7.Get(index7));
 //BA.debugLineNum = 55;BA.debugLine="Dim value As String = NameValues.Get(key)";
_value = BA.ObjectToString(_namevalues.Get((Object)(_key)));
 //BA.debugLineNum = 56;BA.debugLine="Dim s As String = _ $\"--${boundary} Content-Dis";
_s = ("--"+__c.SmartStringFormatter("",(Object)(_boundary))+"\n"+"Content-Disposition: form-data; name=\""+__c.SmartStringFormatter("",(Object)(_key))+"\"\n"+"\n"+""+__c.SmartStringFormatter("",(Object)(_value))+"\n"+"");
 //BA.debugLineNum = 62;BA.debugLine="b = s.Replace(CRLF, eol).GetBytes(\"UTF8\")";
_b = _s.replace(__c.CRLF,_eol).getBytes("UTF8");
 //BA.debugLineNum = 63;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
 }
;
 };
 //BA.debugLineNum = 66;BA.debugLine="If Files <> Null And Files.IsInitialized Then";
if (_files!= null && _files.IsInitialized()) { 
 //BA.debugLineNum = 67;BA.debugLine="For Each fd As MultipartFileData In Files";
final anywheresoftware.b4a.BA.IterableList group15 = _files;
final int groupLen15 = group15.getSize();
for (int index15 = 0;index15 < groupLen15 ;index15++){
_fd = (be.mayeur.home.homemon.httpjob._multipartfiledata)(group15.Get(index15));
 //BA.debugLineNum = 68;BA.debugLine="Dim s As String = _ $\"--${boundary} Content-Dis";
_s = ("--"+__c.SmartStringFormatter("",(Object)(_boundary))+"\n"+"Content-Disposition: form-data; name=\""+__c.SmartStringFormatter("",(Object)(_fd.KeyName))+"\"; filename=\""+__c.SmartStringFormatter("",(Object)(_fd.FileName))+"\"\n"+"Content-Type: "+__c.SmartStringFormatter("",(Object)(_fd.ContentType))+"\n"+"\n"+"");
 //BA.debugLineNum = 74;BA.debugLine="b = s.Replace(CRLF, eol).GetBytes(\"UTF8\")";
_b = _s.replace(__c.CRLF,_eol).getBytes("UTF8");
 //BA.debugLineNum = 75;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
 //BA.debugLineNum = 76;BA.debugLine="Dim in As InputStream = File.OpenInput(fd.Dir,";
_in = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();
_in = __c.File.OpenInput(_fd.Dir,_fd.FileName);
 //BA.debugLineNum = 77;BA.debugLine="File.Copy2(in, stream)";
__c.File.Copy2((java.io.InputStream)(_in.getObject()),(java.io.OutputStream)(_stream.getObject()));
 //BA.debugLineNum = 78;BA.debugLine="stream.WriteBytes(eol.GetBytes(\"utf8\"), 0, 2)";
_stream.WriteBytes(_eol.getBytes("utf8"),(int) (0),(int) (2));
 }
;
 };
 //BA.debugLineNum = 81;BA.debugLine="s = _ $\" --${boundary}-- \"$";
_s = ("\n"+"--"+__c.SmartStringFormatter("",(Object)(_boundary))+"--\n"+"");
 //BA.debugLineNum = 85;BA.debugLine="b = s.Replace(CRLF, eol).GetBytes(\"UTF8\")";
_b = _s.replace(__c.CRLF,_eol).getBytes("UTF8");
 //BA.debugLineNum = 86;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
 //BA.debugLineNum = 87;BA.debugLine="PostBytes(Link, stream.ToBytesArray)";
_vvvv6(_link,_stream.ToBytesArray());
 //BA.debugLineNum = 88;BA.debugLine="req.SetContentType(\"multipart/form-data; boundary";
_vvvvvv4.SetContentType("multipart/form-data; boundary="+_boundary);
 //BA.debugLineNum = 89;BA.debugLine="req.SetContentEncoding(\"UTF8\")";
_vvvvvv4.SetContentEncoding("UTF8");
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public String  _vvvvv1(String _link,String _text) throws Exception{
 //BA.debugLineNum = 23;BA.debugLine="Public Sub PostString(Link As String, Text As Stri";
 //BA.debugLineNum = 24;BA.debugLine="PostBytes(Link, Text.GetBytes(\"UTF8\"))";
_vvvv6(_link,_text.getBytes("UTF8"));
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public String  _vvvvv2(String _link,byte[] _data) throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Public Sub PutBytes(Link As String, Data() As Byte";
 //BA.debugLineNum = 40;BA.debugLine="req.InitializePut2(Link, Data)";
_vvvvvv4.InitializePut2(_link,_data);
 //BA.debugLineNum = 41;BA.debugLine="CallSubDelayed2(HttpUtils2Service, \"SubmitJob\", M";
__c.CallSubDelayed2(getActivityBA(),(Object)(_vvvvvv7.getObject()),"SubmitJob",this);
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public String  _vvvvv3(String _link,String _text) throws Exception{
 //BA.debugLineNum = 34;BA.debugLine="Public Sub PutString(Link As String, Text As Strin";
 //BA.debugLineNum = 35;BA.debugLine="PutBytes(Link, Text.GetBytes(\"UTF8\"))";
_vvvvv2(_link,_text.getBytes("UTF8"));
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public String  _vvvvv4() throws Exception{
 //BA.debugLineNum = 156;BA.debugLine="Public Sub Release";
 //BA.debugLineNum = 157;BA.debugLine="File.Delete(HttpUtils2Service.TempFolder, taskId)";
__c.File.Delete(_vvvvvv7._v5,_vvvvvv3);
 //BA.debugLineNum = 158;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
