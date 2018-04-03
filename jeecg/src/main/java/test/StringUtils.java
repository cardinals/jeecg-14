package test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import freemarker.template.utility.StringUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class StringUtils extends StringUtil{
	
	protected static Logger logger = LoggerFactory.getLogger(StringUtils.class);
    private static String username = "此处应该是你的企鹅号";
    private static String password = "lxaimaciqhkabbbh";
    private static String smtpHost = "smtp.qq.com";
    private static String from = "此处应该是你的企鹅号@qq.com";
    private static String fromName = "考勤信息统计";
   static  int max ;
   String charData;
   
	public StringUtils() {
		
	}
	
	/**
	 * 判断字符串知否有重复
	 * @param iniString
	 * @return
	 */
	public  static boolean checkDifferent(String iniString) {   
		return !iniString.matches(".*(.)(.*\\1).*");
		}
	
	/**
	 * 判断字符串中出现最多的字符和次数
	 * @param str
	 */
	public  void getMax(String str){
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            int temp=0; 
             Pattern p = Pattern.compile(String.valueOf(c[i])); //TODO 正则判断
            Matcher m = p.matcher(str);
            while (m.find()) {
                temp++;
            }
            if(temp>max){
                max=temp;
                charData = String.valueOf(c[i]);
            }
        }

    }
	/**
	 * map排序
	 * @param map
	 * @return
	 */
	public  static LinkedHashMap<String, Object> MapSort(Map<String, String> map) {
		LinkedHashMap<String, Object> maps=new LinkedHashMap<String,Object>();
		List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,String>>() {
            //升序排序
            public int compare(Entry<String, String> o1,
                    Entry<String, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
            
        });
        
        for(Map.Entry<String,String> mapping:list){ 
        	maps.put(mapping.getKey(), mapping.getValue());
               System.out.println(mapping.getKey()+":"+mapping.getValue()); 
          } 		
        
        return maps;
		}

	
	
	
	/**
     * 发送邮件
     *
     * @param mail 收件人地址
     * @param code 验证码
     * @throws Exception
     */
    public static void Sendmail(String mail,String fileName) throws Exception {
        ImageHtmlEmail email = new ImageHtmlEmail();
        //也可以用HtmlEmail
        email.setSSLOnConnect(true);//开启SSL加密
        email.setCharset("UTF-8");//防止乱码
        email.setStartTLSEnabled(true);//开启TLS加密
        email.setAuthentication(username, password);//设置登录邮箱的账户名和密码（保证邮件服务器POP3/SMTP服务开启）
        email.setFrom(from, fromName);//设置发送方地址和用户名，用户名可以不填
        email.setHostName(smtpHost);//设置邮件服务器
        email.addTo(mail);//接收方邮箱
        //email.addCc("1111@qq.com");//抄送方
        //email.addBcc("2222@qq.com");//秘密抄送方
        email.setSubject("人员考勤月统计信息");//设置主题
        String msgContent = "尊敬的领导：<br/><br/>"
                + "    您好！<br/><br/>"
                + "    请用excel打开该文件！<br/><br/>"
                + "    此为自动发送邮件，请勿直接回复！";
        email.setMsg(msgContent);//设置内容
        //也可以使用email.setHtmlMsg()，可以直接发送html格式的邮件信息
      //创建邮件附件可多个   
        EmailAttachment attachment = new EmailAttachment();//创建附件  
        attachment.setPath("D:\\"+fileName);//本地附件，绝对路径    
        //attachment.setURL(new URL("http://www.baidu.com/moumou附件"));这样可以添加网络上附件  
        attachment.setDisposition(EmailAttachment.ATTACHMENT);  
        attachment.setDescription("考勤信息");//附件描述   
        attachment.setName(fileName);//附件名称  
        email.attach(attachment);//添加附件到邮件,可添加多个  
//        email.attach(attachment);//添加附件到邮件,可添加多个  

        /*email.buildMimeMessage();//构建内容类型 ， 
        //设置内容的字符集为UTF-8,先buildMimeMessage才能设置内容文本 ,但不能发送HTML格式的文本 
        email.getMimeMessage().setText("<font color='red'>测试简单邮件发送功能！</font>","UTF-8");*/
        
        email.send();
    }
    
    public static final String KEY_1 = "6hPc1Xu5EWssUPz7z0S9TTIlz8weuWes";

	/**
	 * 原生经纬度转换成百度经纬度
	 * 
	 * @param point_value
	 * @return
	 * @throws IOException
	 */
	public static String getGeocoderLatitude(String point_value) throws IOException {
		String[] points = point_value.split(",");
		point_value = points[1] + "," + points[0];
		URL url = new URL("http://api.map.baidu.com/geoconv/v1/?coords=" + point_value + "&from=1&to=5&ak=" + KEY_1);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setDoOutput(true);
		InputStream inStream = conn.getInputStream();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		String jsonContent = new String(data, "UTF-8");
		JSONObject json = new JSONObject(jsonContent);
		JSONArray result = json.getJSONArray("result");
		JSONObject j = result.getJSONObject(0);
		String value = j.getDouble("y") + "," + j.getDouble("x");
		
		return value;

	}

	/**
	 * 中文地理位置转换成经纬度
	 * 
	 * @param addr
	 * @return
	 */
	public static String getLatAndLngByAddress(String addr) {
		String address = "";
		String lat = "";
		String lng = "";
		try {
			address = URLDecoder.decode(addr, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String url = String.format("http://api.map.baidu.com/geocoder/v2/?" + "ak=" + KEY_1 + "&output=json&address=%s",
				address);
		URL myURL = null;
		URLConnection httpsConn = null;
		// 进行转码
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {

		}
		try {
			httpsConn = (URLConnection) myURL.openConnection();
			if (httpsConn != null) {
				InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(insr);
				String data = null;
				if ((data = br.readLine()) != null) {
					lat = data.substring(data.indexOf("\"lat\":") + ("\"lat\":").length(),
							data.indexOf("},\"precise\""));
					lng = data.substring(data.indexOf("\"lng\":") + ("\"lng\":").length(), data.indexOf(",\"lat\""));
				}
				insr.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lng + "," + lat;
	}

	/**
	 * 通过经纬度获取中文地理位置  
	 * 
	 * @param point_value
	 * @return
	 * @throws IOException
	 */
	public static String getAddressLatitude(String point_value) throws IOException {
		String[] points = point_value.split(",");
		point_value = points[1] + "," + points[0];
		URL url = new URL("http://api.map.baidu.com/geocoder/v2/?callback=&location=" + point_value
				+ "&output=json&pois=1&ak=" + KEY_1);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setDoOutput(true);
		InputStream inStream = conn.getInputStream();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		String jsonContent = new String(data, "UTF-8");
		JSONObject json = new JSONObject(jsonContent);
		JSONObject result = json.getJSONObject("result");
//		System.out.println(result);
		ArrayList<addr> list = new Gson().fromJson(result.get("pois").toString(), new TypeToken<ArrayList<addr>>() {
		}.getType());
		return list.get(0).getAddr();

	}

	class addr {
		private String addr;

		public String getAddr() {
			return addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}
	}
	
	//该方法使用HS256算法和Secret:bankgl生成signKey
    private static Key getKeyInstance() {
        //We will sign our JavaWebToken with our ApiKey secret
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("mySecret");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    //使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
    public static String createJavaWebToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
    }

    //解析Token，同时也能验证Token，当验证失败返回null
    public static Map<String, Object> parserJavaWebToken(String jwt) {
        try {
            Map<String, Object> jwtClaims =
                    Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
            return jwtClaims;
        } catch (Exception e) {
        	logger.error("json web token verify failed");
            return null;
        }
    }
	
    /**
	 * Excel文档的构成
	 * 
	 * 在工作簿(WorkBook)里面包含了工作表(Sheet) 在工作表里面包含了行(Row) 行里面包含了单元格(Cell)
	 * 
	 * 
	 * 创建一个工作簿的基本步骤
	 * 
	 * 第一步 创建一个 工作簿 第二步 创建一个 工作表 第三步 创建一行 第四步 创建单元格 第五步 写数据 第六步
	 * 将内存中生成的workbook写到文件中 然后释放资源
	 * 
	 */

	public static void testCreateFirstExcel97() throws Exception {
		Workbook wb = new HSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream("D:\\workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}

	public static void testCreateFirstExcel07() throws Exception {
		Workbook wb = new XSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream("D:\\workbook.xlsx");
		wb.write(fileOut);
		fileOut.close();
	}

	public static void createExcelOfData() throws Exception {
		Workbook wb = new HSSFWorkbook();
		// 创建工作表
		Sheet sheet = wb.createSheet("测试Excel");
		// 创建单元格 单元格是隶属于行
		Row row = sheet.createRow(0); // 起始从0开始
		Cell cell = row.createCell(0);
		cell.setCellValue("This is a test");
		FileOutputStream fileOut = new FileOutputStream("D:/test.xls");
		wb.write(fileOut);
		fileOut.close();
	}

	public static void createExcelOfUsers(List<Object[]> dataList, String fileName,HttpServletResponse response) throws Exception {
		Workbook wb = new HSSFWorkbook();
		// 创建工作表
		Sheet sheet = wb.createSheet("考勤信息");
		// 显示标题
		Row title_row = sheet.createRow(0);
		//标题行高
		title_row.setHeight((short) (40 * 20));
		Cell title_cell = title_row.createCell(0);
		//标题抬头
		String headers[] = new String[] { "姓名", "出勤", "迟到", "早退", "缺卡", "旷工", "请假", "外出" };
		Row header_row = sheet.createRow(1);
		//单元格行高
		header_row.setHeight((short) (20 * 24));

		// 创建单元格的 显示样式
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER); // 水平方向上的对其方式
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直方向上的对其方式
		title_cell.setCellStyle(style);
		title_cell.setCellValue("人员考勤详细信息");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));
		for (int i = 0; i < headers.length; i++) {
			// 设置列宽 基数为256
			sheet.setColumnWidth(i, 25 * 180); // 标题行高、宽基数
			Cell cell = header_row.createCell(i);
			// 应用样式到 单元格上
			cell.setCellStyle(style);
			cell.setCellValue(headers[i]);
		}

		for (int i = 0; i < dataList.size(); i++) {
			Row row = sheet.createRow(i + 2);
			row.setHeight((short) (20 * 20)); // 设置行高 基数为20
			for (int j = 0; j < dataList.get(i).length; j++) {
				Cell cell = row.createCell(j);
				// 应用样式到 单元格上
				cell.setCellStyle(style);
				if(dataList.get(i)[j]!=null && !"".equals(dataList.get(i)[j])){
					cell.setCellValue(dataList.get(i)[j].toString());
				}else{
					cell.setCellValue("0次");
				}
			}
		} 
		  OutputStream out = null;    
	        try {        
	            out = response.getOutputStream();    
	            String fileName1 = "enroll.xls";// 文件名    
	            response.setContentType("application/x-msdownload");    
	            response.setHeader("Content-Disposition", "attachment; filename="    
	                                                    + URLEncoder.encode(fileName1, "UTF-8"));    
	            wb.write(out);    
	        } catch (Exception e) {    
	            e.printStackTrace();    
	        } finally {      
	            try {       
	                out.close();      
	            } catch (IOException e) {      
	                e.printStackTrace();    
	            }      
	        }
		
//		// String headStr =new String(title.getBytes("ISO-8859-1"),"GBK");
//		FileOutputStream fileOut = new FileOutputStream("D:\\" + fileName);
//		wb.write(fileOut);
//		fileOut.close();
	}
	
	
     //字母Z使用了两个标签，这里有２７个值  
     //i, u, v都不做声母, 跟随前面的字母  
    private char[] chartable =  
       {  
         '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈',  
         '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然',  
         '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', '座'  
        };  
    private char[] alphatableb =  
      {  
         'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',  
         'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'  
       };  
    private char[] alphatables =  
      {  
         'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',  
         'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'  
       };  
    private int[] table = new int[27];  //初始化  
      {  
             for (int i = 0; i < 27; ++i) {  
                 table[i] = gbValue(chartable[i]);  
             }  
       }  
    //主函数,输入字符,得到他的声母,  
    //英文字母返回对应的大小写字母  
    //其他非简体汉字返回 '0'  按参数  
     public char Char2Alpha(char ch,String type) {  
          if (ch >= 'a' && ch <= 'z')  
              return (char) (ch - 'a' + 'A');//为了按字母排序先返回大写字母  
           // return ch;  
          if (ch >= 'A' && ch <= 'Z')  
              return ch;  

             int gb = gbValue(ch);  
             if (gb < table[0])  
              return '0';  
    
          int i;  
             for (i = 0; i < 26; ++i) {  
              if (match(i, gb))  
                     break;  
          }  
       
             if (i >= 26){  
              return '0';}  
             else{  
                 if("b".equals(type)){//大写  
                     return alphatableb[i];  
                 }else{//小写  
                     return alphatables[i];  
                 }  
             }  
      }  
 //根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串  
 public String String2Alpha(String SourceStr,String type) {  
     String Result = "";  
     int StrLength = SourceStr.length();  
     int i;  
  try {  
      for (i = 0; i < StrLength; i++) {  
             Result += Char2Alpha(SourceStr.charAt(i),type);  
         }  
     } catch (Exception e) {  
      Result = "";  
     }  
  return Result;  
}  
//根据一个包含汉字的字符串返回第一个汉字拼音首字母的字符串  
 public String String2AlphaFirst(String SourceStr,String type) {  
       String Result = "";  
     try {  
       Result += Char2Alpha(SourceStr.charAt(0),type);  
     } catch (Exception e) {  
       Result = "";  
     }  
  return Result;  
}  
 private boolean match(int i, int gb) {  
        if (gb < table[i])  
           return false;  
         int j = i + 1;  
   
         //字母Z使用了两个标签  
         while (j < 26 && (table[j] == table[i]))  
             ++j;  
         if (j == 26)  
             return gb <= table[j];  
        else  
             return gb < table[j];  
      }  
           
 //取出汉字的编码  
 private int gbValue(char ch) {  
     String str = new String();  
     str += ch;  
     try {  
         byte[] bytes = str.getBytes("GBK");  
             if (bytes.length < 2)  
                 return 0;  
             return (bytes[0] << 8 & 0xff00) + (bytes[1] &  
                     0xff);  
         } catch (Exception e) {  
           return 0;  
         }  
     }  
 public Map<String, Object> sort(List<String> list){  
     Map<String, Object> map=new HashMap<String, Object>();  
     ArrayList<String> arraylist=new ArrayList<String>();  
     String[] alphatableb =  
         {  
            "A", "B", "C", "D", "E", "F", "G", "H", "I",  
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"  
           };  
         for(String a:alphatableb){  
             for(int i=0;i<list.size();i++){//为了排序都返回大写字母  
                 if(a.equals(String2AlphaFirst(list.get(i).toString(),"b"))){  
                     arraylist.add(list.get(i).toString());  
                 }  
             }  
             map.put(a,arraylist);  
             arraylist=new ArrayList<String>();  
     }  
     return map;  
 }  
 
 /**
  * 将空格自动替换成%20
  * @param str
  * @return
  */
	public static String replace(String str){
		return str.toString().replaceAll(" " , "%20");
	}
	
	/**
	 * 最后一个字符串长度
	 * @param str
	 * @return
	 */
	public static int strlen(String str){
		String[] len= str.split(" ");
		
		return  len[len.length-1].length();
	}
	
	
	public static int firstStr(String str){
		char[] a=str.toCharArray();
		
		for(int i=0;i<a.length;i++ ){
			if(!str.substring(0,i).contains(a[i]+"")&&!str.substring(i+1).contains(a[i]+"")){
				return i;
				
				
			}
	
		}
		return -1;
	}
	
	
	
	public static void main(String[] args) {
		int a,b=3;
		
//		System.out.println(strlen("welcome to China"));
		
		System.out.println(firstStr("googglecaeaclbb"));
		
		
		
//		System.out.println(replace("ha ha ha a"));
		
//		try {
//			
//			System.out.println(getGeocoderLatitude("116.311209,40.037105"));
//			System.out.println(getLatAndLngByAddress("上地雅美科技园"));
//			System.out.println(getAddressLatitude("116.311209,40.037105"));;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	
		
//	System.out.println(	checkDifferent("abcdefghijklmnopqrstuvwxyzz"));
			
		
//		 StringUtils obj1 = new StringUtils();  
//	        System.out.println("======================");  
//	        List<String> list=new ArrayList<String>();  
//	        list.add("安庆");  
//	        list.add("安定");  
//	        list.add("北京");  
//	        list.add("常州");  
//	        list.add("大庆");  
//	        list.add("鄂城");  
//	        list.add("恩施");  
//	        list.add("房山");  
//	        list.add("刚需");  
//	        list.add("杭州");  
//	        list.add("歼灭");  
//	        list.add("考拉"); 
//	        list.add("兰州");  
//	        list.add("美的");  
//	        list.add("南京");  
//	        list.add("偶像");  
//	        list.add("坪山");  
//	        list.add("企鹅");  
//	        list.add("让一让");  
//	        list.add("让开");  
//	        list.add("生化");  
//	        list.add("生活");  
//	        list.add("圣战");  
//	        list.add("停止"); 
//	        list.add("停职");  
//	        list.add("王佳怡");  
//	        list.add("笑一笑");  
//	        list.add("想法");  
//	        list.add("杨过");  
//	        list.add("杨康");  
//	        list.add("张益达");  
//	        list.add("张伟"); 
//	        Map<String,Object> map=obj1.sort(list);  
//	        System.out.println("-------分组后的输出-----------");  
//	        System.out.println(map.get("A"));  
//	        System.out.println(map.get("B"));  
//	        System.out.println(map.get("C"));  
//	        System.out.println(map.get("D"));  
//	        System.out.println(map.get("Y"));  
	}
}
