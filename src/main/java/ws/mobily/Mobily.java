/**********************************************************************************************/
/**********************************************************************************************/
/************                               OperationNumber                        ************/
/************                               ---------------                        ************/
/************         1.Send SMS                                                   ************/
/************		  2.Send Status	                   							   ************/
/************ 		  3.ChangePassword											   ************/
/************		  4.ForgetPassword											   ************/
/************ 		  5.Balance                                                    ************/
/************ 		  6.Active Sender                                              ************/
/************ 		  7.Check Sender                                               ************/
/************ 		  8.Add Sender                                                 ************/
/**********************************************************************************************/
/**********************************************************************************************/

package ws.mobily;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Mobily {
	private String msg="";
	private String balance="";
	static char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

//Send SMS Method
//userName : اسم المستخدم من موبايلي
//password : الباسورد  من موبايلي

//sender : اسم المرسل الذي سيظهر عند ارسال الرساله، ويتم تشفيره إلى  بشكل تلقائي إلى نوع التشفير (urlencode)

//message : 							SendSMS ارسال الرساله بصيغة اليونيكود يتم تحويل الرسالة داخل الفنكشن 
/*										الرساله العربيه  70 حرف
										الرساله الانجليزيه 160 حرف
										في حال ارسال اكثر من رساله عربيه فان الرساله الواحده تحسب 67
										والرساله الانجليزي 158
*/

//numbers : 							يجب كتابة الرقم بالصيغة الدولية مثل 96650555555 وعند الإرسال إلى أكثر من رقم يجب وضع الفاصلة (,) وهي التي عند حرف الواو بين كل رقمين 
/*										لا يوجد عدد محدد من الأرقام التي يمكنك الإرسال لها في حال تم الإرسال من خلال بوابة fsockpoen  أو بوابة CURL،
										ولكن في حال تم الإرسال من خلال بوابة fOpen ، فإنه يمكنك الإرسال إلى 120 رقم فقط في كل عملية إرسال
*/
// للإطلاع على  نتائج البوابة ، يرجى الرجوع إلى ملف إقراني الموجود مع الملفات
	public void sendMessage(String userName,String password,String sender,String message,String numbers){
		    String para ="mobile=" + userName + "&password=" + password + "&numbers=" + numbers+ "&sender=" + sender + "&msg=" + convertUnicode(message) + "&applicationType=24";
	        sendURL("http://www.mobily.ws/api/msgSend.php",para,1);
	        System.out.println(getMessage());
	}


//Check send Status method
// للإطلاع على  نتائج البوابة ، يرجى الرجوع إلى ملف إقراني الموجود مع الملفات
	public void sendStatus(){
		sendURL("http://www.mobily.ws/api/sendStatus.php","",2);
	}

//Change Password method
//userName : اسم المستخدم لحسابك في موقع موبايلي
//password : كلمة المرور القديمه لحسابك في موقع موبايلي
//newPassword : كلمة المرور الجديد لحسابك في موقع موبايلي
// للإطلاع على  نتائج البوابة ، يرجى الرجوع إلى ملف إقراني الموجود مع الملفات
	public void changePassword(String userName,String password,String newPassword){
		String para="mobile="+userName+"&password="+password+"&newPassword="+newPassword;
		sendURL("http://www.mobily.ws/api/changePassword.php",para,3);
	}


//Forget Password Method 
//userName : اسم المستخدم لحسابك في موقع موبايلي
//type : دالة تحديد مكان إرسال كلمة المرور
//1: إرسال كلمة المرور على الجوال
//2: إرسال كلمة المرور على الإيميل، ويجب توفر الإيميل بالشكل الصحيح ضمن المعلومات الشخصيه في الموقع
// للإطلاع على  نتائج البوابة ، يرجى الرجوع إلى ملف إقراني الموجود مع الملفات
	public void forgetPasswrd(String userName,String type){
		String para="mobile="+userName+"&type="+type;
		sendURL("http://www.mobily.ws/api/forgetPassword.php",para,4);
	}


//check balance method
//userName : اسم المستخدم لحسابك في موقع موبايلي
//password : كلمة المرور لحسابك في موقع موبايلي
// للإطلاع على  نتائج البوابة ، يرجى الرجوع إلى ملف إقراني الموجود مع الملفات
	public String checkBalance(String userName,String password){
		String para="mobile="+userName+"&password="+password;
		sendURL("http://www.mobily.ws/api/balance.php",para,5);
		return balance;
	}


//Active Sender Method
//userName : اسم المستخدم من موبايلي
//Password : الباسورد  من موبايلي
//senderID : القيمة العدديه الناتجه من عملية طلب تفعيل رقم الجوال كإسم مرسل، وبدون الرمز (#)، وكمثال فإن الرقم #110 يجب إرساله على الشكل 110
//activeKey : كود التفعيل الذي تم إستلامه على الجوال
// للإطلاع على  نتائج البوابة ، يرجى الرجوع إلى ملف إقراني الموجود مع الملفات
	public void ActiveSender(String userName,String Password,String senderID,String activeKey){
		String para="mobile="+userName+"&password="+Password+"&senderId="+senderID+"&activeKey="+activeKey;
		sendURL("http://www.mobily.ws/api/activeSender.php",para,6);
	}

//Check Sender Method
//userName : اسم المستخدم من موبايلي
//password : الباسورد  من موبايلي
//senderID : القيمة العدديه الناتجه من عملية طلب تفعيل رقم الجوال كإسم مرسل، وبدون الرمز (#)، وكمثال فإن الرقم #110 يجب إرساله على الشكل 110
// للإطلاع على  نتائج البوابة ، يرجى الرجوع إلى ملف إقراني الموجود مع الملفات
	public void checkSender(String userName,String password,String senderID){
		String para="mobile="+userName+"&password="+password+"&senderId="+senderID;
		sendURL("http://www.mobily.ws/api/checkSender.php",para,7);
	}

	
	public String getMessage(){
		return msg;
	}
	//**********************************************************************************************//
	//*********************************							************************************//
	//*********************************conver to unicode Methods************************************//
	//********************************* 						************************************//
	//**********************************************************************************************//
	
	public static String convertUnicode(String a) {
		int bufSize = 16;
		byte[] buffer = new byte[bufSize];
		String s = null;
		try {
			buffer=a.getBytes();
			s = bytesToHex(buffer,0,buffer.length);
			System.out.println("Hex: "+s);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return s;
	}
	public static String bytesToHex(byte[] b, int off, int len) {
		StringBuffer buf = new StringBuffer();
		for (int j=0; j<len; j++)
			buf.append(byteToHex(b[off+j]));
			return buf.toString();
	}
	public static String byteToHex(byte b) {
		char[] a = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
		return forDigits(new String(a));
	}
	public static String forDigits(String val){
		switch (val.length() ){
			case 1:return "000"+val;
			case 2:return "00"+val;
			case 3:return "0"+val;
			case 4:return ""+val;
			default:return val;
		}
	}
	//**********************************************************************************************//
	//**********************************************************************************************//
	//**********************************************************************************************//
	public void selectedMessage(int value,int operationNumber){
		switch(operationNumber){
			case 1:switch(value){
						case 1:msg= "تمت العملية بنجاح";break;
						case 2:msg="إن رصيدك لدى موبايلي قد إنتهى ولم يعد به أي رسائل. (لحل المشكلة قم بشحن رصيدك من الرسائل لدى موبايلي. لشحن رصيدك إتبع تعليمات شحن الرصيد";break;
						case 3:msg="إن رصيدك الحالي لا يكفي لإتمام عملية الإرسال. (لحل المشكلة قم بشحن رصيدك من الرسائل لدى موبايلي. لشحن رصيدك إتبع تعليمات شحن الرصيد";break;
						case 4:msg="إن إسم المستخدم الذي إستخدمته للدخول إلى حساب الرسائل غير صحيح (تأكد من أن إسم المستخدم الذي إستخدمته هو نفسه الذي تستخدمه عند دخولك إلى موقع موبايلي).";break;
						case 5:msg="هناك خطأ في كلمة المرور (تأكد من أن كلمة المرور التي تم إستخدامها هي نفسها التي تستخدمها عند دخولك موقع موبايلي,إذا نسيت كلمة المرور إضغط على رابط نسيت كلمة المرور لتصلك رسالة على جوالك برقم المرور الخاص بك";break;
						case 6:msg="إن صفحة الإرسال لاتجيب في الوقت الحالي (قد يكون هناك طلب كبير على الصفحة أو توقف مؤقت للصفحة فقط حاول مرة أخرى أو تواصل مع الدعم الفني إذا إستمر الخطأ";break;
						case 12:msg="إن حسابك بحاجة إلى تحديث يرجى مراجعة الدعم الفني";break;
						case 13:msg="إن إسم المرسل الذي إستخدمته في هذه الرسالة لم يتم قبوله. (يرجى إرسال الرسالة بإسم مرسل آخر أو تعريف إسم المرسل لدى موبايلي";break;
						case 14:msg="إن إسم المرسل الذي إستخدمته غير معرف لدى موبايلي. (يمكنك تعريف إسم المرسل من خلال صفحة إضافة إسم مرسل";break;
						case 15:msg="يوجد رقم جوال خاطئ في الأرقام التي قمت بالإرسال لها. (تأكد من صحة الأرقام التي تريد الإرسال لها وأنها بالصيغة الدولية";break;
						case 16:msg="الرسالة التي قمت بإرسالها لا تحتوي على إسم مرسل. (أدخل إسم مرسل عند إرسالك الرسالة";break;
						case 17:msg="م يتم ارسال نص الرسالة. الرجاء التأكد من ارسال نص الرسالة والتأكد من تحويل الرسالة الى يوني كود (الرجاء التأكد من استخدام الدالة";break;
						case -1:msg="لم يتم التواصل مع خادم (Server) الإرسال موبايلي بنجاح. (قد يكون هناك محاولات إرسال كثيرة تمت معا , أو قد يكون هناك عطل مؤقت طرأ على الخادم إذا إستمرت المشكلة يرجى التواصل مع الدعم الفني)";break;
						case -2:msg="لم يتم الربط مع قاعدة البيانات (Database) التي تحتوي على حسابك وبياناتك لدى موبايلي. (قد يكون هناك محاولات إرسال كثيرة تمت معا , أو قد يكون هناك عطل مؤقت طرأ على الخادم إذا إستمرت المشكلة يرجى التواصل مع الدعم الفني)";break;
						default:msg="";break;
				   }break;
			case 2:switch(value){
						case 1:msg= "يمكنك الإرسال الآن.";break;
						default:msg="الإرسال متوقف الآن.";break;
				   }break;
			case 3:switch(value){
						case 1:msg= "رقم الجوال غير متوفر.";break;
						case 2:msg="كلمه المرور القديمه خاطئه.";break;
						case 3:msg="عملية تغيير آلمة المرور تمت بنجاح.";break;
						default:msg="";break;
				   }break;
			case 4:switch(value){
						case 1:msg= "رقم الجوال غير متوفر..";break;
						case 2:msg="الإيميل الخاص بالحساب غير متوفر.";break;
						case 3:msg="تم إرسال آلمة المرور على الجوال بنجاح.";break;
						case 4:msg="رصيدك غير آافي لإتمام عملية الإرسال.";break;
						case 5:msg="تم إرسال آلمة المرور على الإيميل بنجاح.";break;
						case 6:msg="الإيميل الخاص بالحساب غير صحيح.";break;
						case 7:msg="إسم الحساب المستخدم غير صحيح.";break;
						default:msg="";break;
				   }break;
			case 5:switch(value){
						case 1:msg= "رقم الجوال غير متوفر.";break;
						case 2:msg= "آلمة المرور خاطئه.";break;
						default:msg=balance;break;
				   }break;
			
			case 7:switch(value){
						case 0:msg= "اسم المرسل غير مفعل.";break;
						case 1:msg= "إسم المرسل مفعل.";break;
						case 2:msg="إسم المرسل مرفوض (تم ادخال آود التفعيل لهذا المرسل 3 مرات خطأ)";break;
						case 3:msg="رقم الجوال غير متوفر.";break;
						case 4:msg="كلمه المرور خاطئه.";break;
						default:msg="";break;
				   }break;
			}
	}
	public void sendURL(String URL,String parameters,int operationNumber){
		try {
	        URL url;
	        URLConnection urlConnection;
	        DataOutputStream outStream;
	        // Create connection
	        url = new URL(URL);
	        urlConnection = url.openConnection();
	        ((HttpURLConnection)urlConnection).setRequestMethod("POST");
	        urlConnection.setDoInput(true);
	        urlConnection.setDoOutput(true);
	        urlConnection.setUseCaches(false);
	        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        urlConnection.setRequestProperty("Content-Length", ""+ parameters.length());
	        urlConnection.setRequestProperty("User-agent","Mozilla/4.0");
	        // Create I/O streams
	        outStream = new DataOutputStream(urlConnection.getOutputStream());
	        // Send request
	        outStream.writeBytes(parameters);
	        outStream.flush();
	        outStream.close();
	        // Get Response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	        // - For debugging purposes only!
	        String buffer;
	        while((buffer = rd.readLine()) != null) {
	        	try{
	        		selectedMessage(Integer.parseInt(buffer),operationNumber);
	        	}catch(Exception ex){
	        		balance=buffer;
	        	}
	        }
	        // Close I/O streams
	        rd.close();
	        outStream.close();
	    }
	    catch(Exception ex) {
	        System.out.println("Exception cought:\n"+ ex.toString());
	    }
	}
	
	
}