package be.mayeur.home.homemon;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class statemanager {
private static statemanager mostCurrent = new statemanager();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.collections.Map _v5 = null;
public static int _v6 = 0;
public static String _v7 = "";
public static String _v0 = "";
public static anywheresoftware.b4a.objects.collections.Map _vv1 = null;
public be.mayeur.home.homemon.main _vvvvvv6 = null;
public be.mayeur.home.homemon.httputils2service _vvvvvv0 = null;
public static Object[]  _vvvvvvv7(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.collections.List _list1) throws Exception{
 //BA.debugLineNum = 203;BA.debugLine="Private Sub getNextItem(list1 As List) As Object()";
 //BA.debugLineNum = 204;BA.debugLine="listPosition = listPosition + 1";
_v6 = (int) (_v6+1);
 //BA.debugLineNum = 205;BA.debugLine="Return list1.Get(listPosition)";
if (true) return (Object[])(_list1.Get(_v6));
 //BA.debugLineNum = 206;BA.debugLine="End Sub";
return null;
}
public static String  _vv2(anywheresoftware.b4a.BA _ba,String _key) throws Exception{
 //BA.debugLineNum = 28;BA.debugLine="Public Sub GetSetting(Key As String) As String";
 //BA.debugLineNum = 29;BA.debugLine="Return GetSetting2(Key, \"\")";
if (true) return _vv3(_ba,_key,"");
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public static String  _vv3(anywheresoftware.b4a.BA _ba,String _key,String _defaultvalue) throws Exception{
String _v = "";
 //BA.debugLineNum = 13;BA.debugLine="Public Sub GetSetting2(Key As String, DefaultValue";
 //BA.debugLineNum = 14;BA.debugLine="If settings.IsInitialized = False Then";
if (_vv1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 16;BA.debugLine="If File.Exists(File.DirInternal, settingsFileNam";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),_v0)) { 
 //BA.debugLineNum = 17;BA.debugLine="settings = File.ReadMap(File.DirInternal, setti";
_vv1 = anywheresoftware.b4a.keywords.Common.File.ReadMap(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),_v0);
 }else {
 //BA.debugLineNum = 19;BA.debugLine="Return DefaultValue";
if (true) return _defaultvalue;
 };
 };
 //BA.debugLineNum = 22;BA.debugLine="Dim v As String";
_v = "";
 //BA.debugLineNum = 23;BA.debugLine="v = settings.GetDefault(Key.ToLowerCase, DefaultV";
_v = BA.ObjectToString(_vv1.GetDefault((Object)(_key.toLowerCase()),(Object)(_defaultvalue)));
 //BA.debugLineNum = 24;BA.debugLine="Return v";
if (true) return _v;
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvv0(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ConcreteViewWrapper _v,anywheresoftware.b4a.objects.collections.List _list1) throws Exception{
Object[] _data = null;
anywheresoftware.b4a.objects.EditTextWrapper _edit = null;
anywheresoftware.b4a.objects.SpinnerWrapper _spinner1 = null;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _check = null;
anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radio = null;
anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _toggle = null;
anywheresoftware.b4a.objects.SeekBarWrapper _seek = null;
anywheresoftware.b4a.objects.TabHostWrapper _th = null;
int _i = 0;
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.objects.PanelWrapper _tabparentpanel = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _sv = null;
anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
 //BA.debugLineNum = 138;BA.debugLine="Private Sub innerRestoreState(v As View, list1 As";
 //BA.debugLineNum = 139;BA.debugLine="Dim data() As Object";
_data = new Object[(int) (0)];
{
int d0 = _data.length;
for (int i0 = 0;i0 < d0;i0++) {
_data[i0] = new Object();
}
}
;
 //BA.debugLineNum = 140;BA.debugLine="If v Is EditText Then";
if (_v.getObjectOrNull() instanceof android.widget.EditText) { 
 //BA.debugLineNum = 141;BA.debugLine="Dim edit As EditText";
_edit = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 142;BA.debugLine="edit = v";
_edit.setObject((android.widget.EditText)(_v.getObject()));
 //BA.debugLineNum = 143;BA.debugLine="data = getNextItem(list1)";
_data = _vvvvvvv7(_ba,_list1);
 //BA.debugLineNum = 144;BA.debugLine="edit.Text = data(0)";
_edit.setText(_data[(int) (0)]);
 //BA.debugLineNum = 145;BA.debugLine="edit.SelectionStart = data(1)";
_edit.setSelectionStart((int)(BA.ObjectToNumber(_data[(int) (1)])));
 }else if(_v.getObjectOrNull() instanceof anywheresoftware.b4a.objects.SpinnerWrapper.B4ASpinner) { 
 //BA.debugLineNum = 147;BA.debugLine="Dim spinner1 As Spinner";
_spinner1 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 148;BA.debugLine="spinner1 = v";
_spinner1.setObject((anywheresoftware.b4a.objects.SpinnerWrapper.B4ASpinner)(_v.getObject()));
 //BA.debugLineNum = 149;BA.debugLine="data = getNextItem(list1)";
_data = _vvvvvvv7(_ba,_list1);
 //BA.debugLineNum = 150;BA.debugLine="spinner1.SelectedIndex = data(0)";
_spinner1.setSelectedIndex((int)(BA.ObjectToNumber(_data[(int) (0)])));
 }else if(_v.getObjectOrNull() instanceof android.widget.CheckBox) { 
 //BA.debugLineNum = 152;BA.debugLine="Dim check As CheckBox";
_check = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 153;BA.debugLine="check = v";
_check.setObject((android.widget.CheckBox)(_v.getObject()));
 //BA.debugLineNum = 154;BA.debugLine="data = getNextItem(list1)";
_data = _vvvvvvv7(_ba,_list1);
 //BA.debugLineNum = 155;BA.debugLine="check.Checked = data(0)";
_check.setChecked(BA.ObjectToBoolean(_data[(int) (0)]));
 }else if(_v.getObjectOrNull() instanceof android.widget.RadioButton) { 
 //BA.debugLineNum = 157;BA.debugLine="Dim radio As RadioButton";
_radio = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 158;BA.debugLine="radio = v";
_radio.setObject((android.widget.RadioButton)(_v.getObject()));
 //BA.debugLineNum = 159;BA.debugLine="data = getNextItem(list1)";
_data = _vvvvvvv7(_ba,_list1);
 //BA.debugLineNum = 160;BA.debugLine="radio.Checked = data(0)";
_radio.setChecked(BA.ObjectToBoolean(_data[(int) (0)]));
 }else if(_v.getObjectOrNull() instanceof android.widget.ToggleButton) { 
 //BA.debugLineNum = 162;BA.debugLine="Dim toggle As ToggleButton";
_toggle = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 163;BA.debugLine="toggle = v";
_toggle.setObject((android.widget.ToggleButton)(_v.getObject()));
 //BA.debugLineNum = 164;BA.debugLine="data = getNextItem(list1)";
_data = _vvvvvvv7(_ba,_list1);
 //BA.debugLineNum = 165;BA.debugLine="toggle.Checked = data(0)";
_toggle.setChecked(BA.ObjectToBoolean(_data[(int) (0)]));
 }else if(_v.getObjectOrNull() instanceof android.widget.SeekBar) { 
 //BA.debugLineNum = 167;BA.debugLine="Dim seek As SeekBar";
_seek = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 168;BA.debugLine="seek = v";
_seek.setObject((android.widget.SeekBar)(_v.getObject()));
 //BA.debugLineNum = 169;BA.debugLine="data = getNextItem(list1)";
_data = _vvvvvvv7(_ba,_list1);
 //BA.debugLineNum = 170;BA.debugLine="seek.Value = data(0)";
_seek.setValue((int)(BA.ObjectToNumber(_data[(int) (0)])));
 }else if(_v.getObjectOrNull() instanceof android.widget.TabHost) { 
 //BA.debugLineNum = 172;BA.debugLine="Dim th As TabHost";
_th = new anywheresoftware.b4a.objects.TabHostWrapper();
 //BA.debugLineNum = 173;BA.debugLine="th = v";
_th.setObject((android.widget.TabHost)(_v.getObject()));
 //BA.debugLineNum = 174;BA.debugLine="data = getNextItem(list1)";
_data = _vvvvvvv7(_ba,_list1);
 //BA.debugLineNum = 175;BA.debugLine="For i = 0 To th.TabCount - 1";
{
final int step37 = 1;
final int limit37 = (int) (_th.getTabCount()-1);
for (_i = (int) (0) ; (step37 > 0 && _i <= limit37) || (step37 < 0 && _i >= limit37); _i = ((int)(0 + _i + step37)) ) {
 //BA.debugLineNum = 176;BA.debugLine="th.CurrentTab = i";
_th.setCurrentTab(_i);
 }
};
 //BA.debugLineNum = 178;BA.debugLine="th.CurrentTab = data(0)";
_th.setCurrentTab((int)(BA.ObjectToNumber(_data[(int) (0)])));
 //BA.debugLineNum = 179;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 180;BA.debugLine="r.Target = th";
_r.Target = (Object)(_th.getObject());
 //BA.debugLineNum = 181;BA.debugLine="Dim tabParentPanel As Panel";
_tabparentpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 182;BA.debugLine="tabParentPanel = r.RunMethod(\"getTabContentView\"";
_tabparentpanel.setObject((android.view.ViewGroup)(_r.RunMethod("getTabContentView")));
 //BA.debugLineNum = 183;BA.debugLine="For i = 0 To tabParentPanel.NumberOfViews - 1";
{
final int step45 = 1;
final int limit45 = (int) (_tabparentpanel.getNumberOfViews()-1);
for (_i = (int) (0) ; (step45 > 0 && _i <= limit45) || (step45 < 0 && _i >= limit45); _i = ((int)(0 + _i + step45)) ) {
 //BA.debugLineNum = 184;BA.debugLine="innerRestoreState(tabParentPanel.GetView(i), li";
_vvvvvvv0(_ba,_tabparentpanel.GetView(_i),_list1);
 }
};
 }else if(_v.getObjectOrNull() instanceof android.widget.ScrollView) { 
 //BA.debugLineNum = 187;BA.debugLine="Dim sv As ScrollView";
_sv = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 188;BA.debugLine="sv = v";
_sv.setObject((android.widget.ScrollView)(_v.getObject()));
 //BA.debugLineNum = 189;BA.debugLine="data = getNextItem(list1)";
_data = _vvvvvvv7(_ba,_list1);
 //BA.debugLineNum = 190;BA.debugLine="sv.ScrollPosition = data(0)";
_sv.setScrollPosition((int)(BA.ObjectToNumber(_data[(int) (0)])));
 //BA.debugLineNum = 191;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 //BA.debugLineNum = 192;BA.debugLine="sv.ScrollPosition = data(0)";
_sv.setScrollPosition((int)(BA.ObjectToNumber(_data[(int) (0)])));
 //BA.debugLineNum = 193;BA.debugLine="innerRestoreState(sv.Panel, list1)";
_vvvvvvv0(_ba,(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_sv.getPanel().getObject())),_list1);
 }else if(_v.getObjectOrNull() instanceof android.view.ViewGroup) { 
 //BA.debugLineNum = 195;BA.debugLine="Dim panel1 As Panel";
_panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 196;BA.debugLine="panel1 = v";
_panel1.setObject((android.view.ViewGroup)(_v.getObject()));
 //BA.debugLineNum = 197;BA.debugLine="For i = 0 To panel1.NumberOfViews - 1";
{
final int step59 = 1;
final int limit59 = (int) (_panel1.getNumberOfViews()-1);
for (_i = (int) (0) ; (step59 > 0 && _i <= limit59) || (step59 < 0 && _i >= limit59); _i = ((int)(0 + _i + step59)) ) {
 //BA.debugLineNum = 198;BA.debugLine="innerRestoreState(panel1.GetView(i), list1)";
_vvvvvvv0(_ba,_panel1.GetView(_i),_list1);
 }
};
 };
 //BA.debugLineNum = 201;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvv1(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ConcreteViewWrapper _v,anywheresoftware.b4a.objects.collections.List _list1) throws Exception{
Object[] _data = null;
anywheresoftware.b4a.objects.EditTextWrapper _edit = null;
anywheresoftware.b4a.objects.SpinnerWrapper _spinner1 = null;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _check = null;
anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radio = null;
anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _toggle = null;
anywheresoftware.b4a.objects.SeekBarWrapper _seek = null;
anywheresoftware.b4a.objects.TabHostWrapper _th = null;
int _i = 0;
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.objects.PanelWrapper _tabparentpanel = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _sv = null;
anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
 //BA.debugLineNum = 79;BA.debugLine="Private Sub innerSaveState(v As View, list1 As Lis";
 //BA.debugLineNum = 80;BA.debugLine="Dim data() As Object";
_data = new Object[(int) (0)];
{
int d0 = _data.length;
for (int i0 = 0;i0 < d0;i0++) {
_data[i0] = new Object();
}
}
;
 //BA.debugLineNum = 81;BA.debugLine="If v Is EditText Then";
if (_v.getObjectOrNull() instanceof android.widget.EditText) { 
 //BA.debugLineNum = 82;BA.debugLine="Dim edit As EditText";
_edit = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 83;BA.debugLine="edit = v";
_edit.setObject((android.widget.EditText)(_v.getObject()));
 //BA.debugLineNum = 84;BA.debugLine="data = Array As Object(edit.Text, edit.Selection";
_data = new Object[]{(Object)(_edit.getText()),(Object)(_edit.getSelectionStart())};
 }else if(_v.getObjectOrNull() instanceof anywheresoftware.b4a.objects.SpinnerWrapper.B4ASpinner) { 
 //BA.debugLineNum = 86;BA.debugLine="Dim spinner1 As Spinner";
_spinner1 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 87;BA.debugLine="spinner1 = v";
_spinner1.setObject((anywheresoftware.b4a.objects.SpinnerWrapper.B4ASpinner)(_v.getObject()));
 //BA.debugLineNum = 88;BA.debugLine="data = Array As Object(spinner1.SelectedIndex)";
_data = new Object[]{(Object)(_spinner1.getSelectedIndex())};
 }else if(_v.getObjectOrNull() instanceof android.widget.CheckBox) { 
 //BA.debugLineNum = 90;BA.debugLine="Dim check As CheckBox";
_check = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 91;BA.debugLine="check = v";
_check.setObject((android.widget.CheckBox)(_v.getObject()));
 //BA.debugLineNum = 92;BA.debugLine="data = Array As Object(check.Checked)";
_data = new Object[]{(Object)(_check.getChecked())};
 }else if(_v.getObjectOrNull() instanceof android.widget.RadioButton) { 
 //BA.debugLineNum = 94;BA.debugLine="Dim radio As RadioButton";
_radio = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 95;BA.debugLine="radio = v";
_radio.setObject((android.widget.RadioButton)(_v.getObject()));
 //BA.debugLineNum = 96;BA.debugLine="data = Array As Object(radio.Checked)";
_data = new Object[]{(Object)(_radio.getChecked())};
 }else if(_v.getObjectOrNull() instanceof android.widget.ToggleButton) { 
 //BA.debugLineNum = 98;BA.debugLine="Dim toggle As ToggleButton";
_toggle = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 99;BA.debugLine="toggle = v";
_toggle.setObject((android.widget.ToggleButton)(_v.getObject()));
 //BA.debugLineNum = 100;BA.debugLine="data = Array As Object(toggle.Checked)";
_data = new Object[]{(Object)(_toggle.getChecked())};
 }else if(_v.getObjectOrNull() instanceof android.widget.SeekBar) { 
 //BA.debugLineNum = 102;BA.debugLine="Dim seek As SeekBar";
_seek = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 103;BA.debugLine="seek = v";
_seek.setObject((android.widget.SeekBar)(_v.getObject()));
 //BA.debugLineNum = 104;BA.debugLine="data = Array As Object(seek.Value)";
_data = new Object[]{(Object)(_seek.getValue())};
 }else if(_v.getObjectOrNull() instanceof android.widget.TabHost) { 
 //BA.debugLineNum = 106;BA.debugLine="Dim th As TabHost";
_th = new anywheresoftware.b4a.objects.TabHostWrapper();
 //BA.debugLineNum = 107;BA.debugLine="th = v";
_th.setObject((android.widget.TabHost)(_v.getObject()));
 //BA.debugLineNum = 108;BA.debugLine="data = Array As Object(th.CurrentTab)";
_data = new Object[]{(Object)(_th.getCurrentTab())};
 //BA.debugLineNum = 109;BA.debugLine="For i = 0 To th.TabCount - 1";
{
final int step30 = 1;
final int limit30 = (int) (_th.getTabCount()-1);
for (_i = (int) (0) ; (step30 > 0 && _i <= limit30) || (step30 < 0 && _i >= limit30); _i = ((int)(0 + _i + step30)) ) {
 //BA.debugLineNum = 110;BA.debugLine="th.CurrentTab = i";
_th.setCurrentTab(_i);
 }
};
 //BA.debugLineNum = 112;BA.debugLine="list1.Add(data)";
_list1.Add((Object)(_data));
 //BA.debugLineNum = 113;BA.debugLine="Dim data() As Object";
_data = new Object[(int) (0)];
{
int d0 = _data.length;
for (int i0 = 0;i0 < d0;i0++) {
_data[i0] = new Object();
}
}
;
 //BA.debugLineNum = 114;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 115;BA.debugLine="r.Target = th";
_r.Target = (Object)(_th.getObject());
 //BA.debugLineNum = 116;BA.debugLine="Dim tabParentPanel As Panel";
_tabparentpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 117;BA.debugLine="tabParentPanel = r.RunMethod(\"getTabContentView\"";
_tabparentpanel.setObject((android.view.ViewGroup)(_r.RunMethod("getTabContentView")));
 //BA.debugLineNum = 118;BA.debugLine="For i = 0 To tabParentPanel.NumberOfViews - 1";
{
final int step39 = 1;
final int limit39 = (int) (_tabparentpanel.getNumberOfViews()-1);
for (_i = (int) (0) ; (step39 > 0 && _i <= limit39) || (step39 < 0 && _i >= limit39); _i = ((int)(0 + _i + step39)) ) {
 //BA.debugLineNum = 119;BA.debugLine="innerSaveState(tabParentPanel.GetView(i), list1";
_vvvvvvvv1(_ba,_tabparentpanel.GetView(_i),_list1);
 }
};
 }else if(_v.getObjectOrNull() instanceof android.widget.ScrollView) { 
 //BA.debugLineNum = 122;BA.debugLine="Dim sv As ScrollView";
_sv = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 123;BA.debugLine="sv = v";
_sv.setObject((android.widget.ScrollView)(_v.getObject()));
 //BA.debugLineNum = 124;BA.debugLine="data = Array As Object(sv.ScrollPosition)";
_data = new Object[]{(Object)(_sv.getScrollPosition())};
 //BA.debugLineNum = 125;BA.debugLine="list1.Add(data)";
_list1.Add((Object)(_data));
 //BA.debugLineNum = 126;BA.debugLine="Dim data() As Object";
_data = new Object[(int) (0)];
{
int d0 = _data.length;
for (int i0 = 0;i0 < d0;i0++) {
_data[i0] = new Object();
}
}
;
 //BA.debugLineNum = 127;BA.debugLine="innerSaveState(sv.Panel, list1)";
_vvvvvvvv1(_ba,(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_sv.getPanel().getObject())),_list1);
 }else if(_v.getObjectOrNull() instanceof android.view.ViewGroup) { 
 //BA.debugLineNum = 129;BA.debugLine="Dim panel1 As Panel";
_panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 130;BA.debugLine="panel1 = v";
_panel1.setObject((android.view.ViewGroup)(_v.getObject()));
 //BA.debugLineNum = 131;BA.debugLine="For i = 0 To panel1.NumberOfViews - 1";
{
final int step52 = 1;
final int limit52 = (int) (_panel1.getNumberOfViews()-1);
for (_i = (int) (0) ; (step52 > 0 && _i <= limit52) || (step52 < 0 && _i >= limit52); _i = ((int)(0 + _i + step52)) ) {
 //BA.debugLineNum = 132;BA.debugLine="innerSaveState(panel1.GetView(i), list1)";
_vvvvvvvv1(_ba,_panel1.GetView(_i),_list1);
 }
};
 };
 //BA.debugLineNum = 135;BA.debugLine="If data.Length > 0 Then list1.Add(data)";
if (_data.length>0) { 
_list1.Add((Object)(_data));};
 //BA.debugLineNum = 136;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvv2(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.randomaccessfile.RandomAccessFile _raf = null;
 //BA.debugLineNum = 237;BA.debugLine="Private Sub loadStateFile";
 //BA.debugLineNum = 239;BA.debugLine="If states.IsInitialized Then Return";
if (_v5.IsInitialized()) { 
if (true) return "";};
 //BA.debugLineNum = 240;BA.debugLine="If File.Exists(File.DirInternal, statesFileName)";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),_v7)) { 
 //BA.debugLineNum = 241;BA.debugLine="Dim raf As RandomAccessFile";
_raf = new anywheresoftware.b4a.randomaccessfile.RandomAccessFile();
 //BA.debugLineNum = 242;BA.debugLine="raf.Initialize(File.DirInternal, statesFileName,";
_raf.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),_v7,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 243;BA.debugLine="states = raf.ReadObject(0)";
_v5.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_raf.ReadObject((long) (0))));
 //BA.debugLineNum = 244;BA.debugLine="raf.Close";
_raf.Close();
 };
 //BA.debugLineNum = 246;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 4;BA.debugLine="Dim states As Map";
_v5 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 5;BA.debugLine="Dim listPosition As Int";
_v6 = 0;
 //BA.debugLineNum = 6;BA.debugLine="Dim statesFileName, settingsFileName As String";
_v7 = "";
_v0 = "";
 //BA.debugLineNum = 7;BA.debugLine="statesFileName = \"state.dat\"";
_v7 = BA.__b (new byte[] {122,98,-20,-19,99,63,-84,-8,99}, 806037);
 //BA.debugLineNum = 8;BA.debugLine="settingsFileName = \"settings.properties\"";
_v0 = BA.__b (new byte[] {122,115,86,-77,111,127,0,-76,57,53,20,-87,118,115,80,-74,97,96,20}, 639619);
 //BA.debugLineNum = 9;BA.debugLine="Dim settings As Map";
_vv1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _vv4(anywheresoftware.b4a.BA _ba,String _activityname) throws Exception{
 //BA.debugLineNum = 52;BA.debugLine="Public Sub ResetState(ActivityName As String)";
 //BA.debugLineNum = 53;BA.debugLine="loadStateFile";
_vvvvvvvv2(_ba);
 //BA.debugLineNum = 54;BA.debugLine="If states.IsInitialized Then";
if (_v5.IsInitialized()) { 
 //BA.debugLineNum = 55;BA.debugLine="states.Remove(ActivityName.ToLowerCase)";
_v5.Remove((Object)(_activityname.toLowerCase()));
 //BA.debugLineNum = 56;BA.debugLine="writeStateToFile";
_vvvvvvvv3(_ba);
 };
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return "";
}
public static boolean  _vv5(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ActivityWrapper _activity,String _activityname,int _validperiodinminutes) throws Exception{
anywheresoftware.b4a.objects.collections.List _list1 = null;
long _time = 0L;
int _i = 0;
 //BA.debugLineNum = 211;BA.debugLine="Public Sub RestoreState(Activity As Activity, Acti";
 //BA.debugLineNum = 212;BA.debugLine="Try";
try { //BA.debugLineNum = 213;BA.debugLine="loadStateFile";
_vvvvvvvv2(_ba);
 //BA.debugLineNum = 214;BA.debugLine="If states.IsInitialized = False Then";
if (_v5.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 215;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 217;BA.debugLine="Dim list1 As List";
_list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 218;BA.debugLine="list1 = states.Get(ActivityName.ToLowerCase)";
_list1.setObject((java.util.List)(_v5.Get((Object)(_activityname.toLowerCase()))));
 //BA.debugLineNum = 219;BA.debugLine="If list1.IsInitialized = False Then Return False";
if (_list1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return anywheresoftware.b4a.keywords.Common.False;};
 //BA.debugLineNum = 220;BA.debugLine="Dim time As Long";
_time = 0L;
 //BA.debugLineNum = 221;BA.debugLine="time = list1.Get(0)";
_time = BA.ObjectToLongNumber(_list1.Get((int) (0)));
 //BA.debugLineNum = 222;BA.debugLine="If ValidPeriodInMinutes > 0 And time + ValidPeri";
if (_validperiodinminutes>0 && _time+_validperiodinminutes*anywheresoftware.b4a.keywords.Common.DateTime.TicksPerMinute<anywheresoftware.b4a.keywords.Common.DateTime.getNow()) { 
 //BA.debugLineNum = 223;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 225;BA.debugLine="listPosition = 0";
_v6 = (int) (0);
 //BA.debugLineNum = 226;BA.debugLine="For i = 0 To Activity.NumberOfViews - 1";
{
final int step15 = 1;
final int limit15 = (int) (_activity.getNumberOfViews()-1);
for (_i = (int) (0) ; (step15 > 0 && _i <= limit15) || (step15 < 0 && _i >= limit15); _i = ((int)(0 + _i + step15)) ) {
 //BA.debugLineNum = 227;BA.debugLine="innerRestoreState(Activity.GetView(i), list1)";
_vvvvvvv0(_ba,_activity.GetView(_i),_list1);
 }
};
 //BA.debugLineNum = 229;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e20) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e20); //BA.debugLineNum = 231;BA.debugLine="Log(\"Error loading state.\")";
anywheresoftware.b4a.keywords.Common.Log("Error loading state.");
 //BA.debugLineNum = 232;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage());
 //BA.debugLineNum = 233;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 235;BA.debugLine="End Sub";
return false;
}
public static String  _vv6(anywheresoftware.b4a.BA _ba) throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Public Sub SaveSettings";
 //BA.debugLineNum = 46;BA.debugLine="If settings.IsInitialized Then";
if (_vv1.IsInitialized()) { 
 //BA.debugLineNum = 47;BA.debugLine="File.WriteMap(File.DirInternal, settingsFileName";
anywheresoftware.b4a.keywords.Common.File.WriteMap(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),_v0,_vv1);
 };
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static String  _vv7(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ActivityWrapper _activity,String _activityname) throws Exception{
anywheresoftware.b4a.objects.collections.List _list1 = null;
int _i = 0;
 //BA.debugLineNum = 60;BA.debugLine="Public Sub SaveState(Activity As Activity, Activit";
 //BA.debugLineNum = 61;BA.debugLine="If states.IsInitialized = False Then states.Initi";
if (_v5.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
_v5.Initialize();};
 //BA.debugLineNum = 62;BA.debugLine="Dim list1 As List";
_list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 63;BA.debugLine="list1.Initialize";
_list1.Initialize();
 //BA.debugLineNum = 64;BA.debugLine="list1.Add(DateTime.Now)";
_list1.Add((Object)(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 65;BA.debugLine="For i = 0 To Activity.NumberOfViews - 1";
{
final int step5 = 1;
final int limit5 = (int) (_activity.getNumberOfViews()-1);
for (_i = (int) (0) ; (step5 > 0 && _i <= limit5) || (step5 < 0 && _i >= limit5); _i = ((int)(0 + _i + step5)) ) {
 //BA.debugLineNum = 66;BA.debugLine="innerSaveState(Activity.GetView(i), list1)";
_vvvvvvvv1(_ba,_activity.GetView(_i),_list1);
 }
};
 //BA.debugLineNum = 68;BA.debugLine="states.Put(ActivityName.ToLowerCase, list1)";
_v5.Put((Object)(_activityname.toLowerCase()),(Object)(_list1.getObject()));
 //BA.debugLineNum = 69;BA.debugLine="writeStateToFile";
_vvvvvvvv3(_ba);
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public static String  _vv0(anywheresoftware.b4a.BA _ba,String _key,String _value) throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Public Sub SetSetting(Key As String, Value As Stri";
 //BA.debugLineNum = 33;BA.debugLine="If settings.IsInitialized = False Then";
if (_vv1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 35;BA.debugLine="If File.Exists(File.DirInternal, settingsFileNam";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),_v0)) { 
 //BA.debugLineNum = 36;BA.debugLine="settings = File.ReadMap(File.DirInternal, setti";
_vv1 = anywheresoftware.b4a.keywords.Common.File.ReadMap(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),_v0);
 }else {
 //BA.debugLineNum = 38;BA.debugLine="settings.Initialize";
_vv1.Initialize();
 };
 };
 //BA.debugLineNum = 41;BA.debugLine="settings.Put(Key.ToLowerCase, Value)";
_vv1.Put((Object)(_key.toLowerCase()),(Object)(_value));
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvv3(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.randomaccessfile.RandomAccessFile _raf = null;
 //BA.debugLineNum = 72;BA.debugLine="Private Sub writeStateToFile";
 //BA.debugLineNum = 73;BA.debugLine="Dim raf As RandomAccessFile";
_raf = new anywheresoftware.b4a.randomaccessfile.RandomAccessFile();
 //BA.debugLineNum = 74;BA.debugLine="raf.Initialize(File.DirInternal, statesFileName,";
_raf.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),_v7,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 75;BA.debugLine="raf.WriteObject(states, True, raf.CurrentPosition";
_raf.WriteObject((Object)(_v5.getObject()),anywheresoftware.b4a.keywords.Common.True,_raf.CurrentPosition);
 //BA.debugLineNum = 76;BA.debugLine="raf.Close";
_raf.Close();
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}
}
