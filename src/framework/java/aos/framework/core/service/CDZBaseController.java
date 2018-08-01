package aos.framework.core.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sun.misc.BASE64Decoder;
import aos.framework.core.dao.SqlDao;
import aos.framework.core.id.AOSId;
import aos.framework.core.typewrap.Dto;
import aos.framework.core.utils.AOSCons;
import aos.framework.core.utils.AOSUtils;
import aos.framework.web.router.HttpModel;
import aos.system.common.model.UserModel;
import aos.system.common.utils.SystemCons;
import dao.CommonLogsDao;
import po.CommonLogsPO;

/**
 * 信贷系统服务基类
 * 
 * @author xiongchun
 *
 */
@Service
public class CDZBaseController {

	@Autowired
	protected SqlDao sqlDao;
	
	@Autowired
	public CommonLogsDao commonLogsDao;
	
	//生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
    public String mkFileName(String fileName){
        return UUID.randomUUID().toString()+"_"+"filename"+fileName.substring(fileName.lastIndexOf("."), fileName.length());
    }
    public String mkFileName(){
        return UUID.randomUUID().toString()+"_"+"base64filename";
    }
    public String mkFilePath(String savePath,String fileName){
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = fileName.hashCode();
        int dir1 = hashcode&0xf;
        int dir2 = (hashcode&0xf0)>>4;
        //构造新的保存目录
        String dir = savePath + "\\" + dir1 + "\\" + dir2;
        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        if(!file.exists()){
            file.mkdirs();
        }
        return dir;
    }
    protected Dto uploadFile(HttpServletRequest request, HttpServletResponse response,Dto outDto) throws ServletException, IOException {
        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = request.getServletContext().getRealPath("/myupload");
        //消息提示
        String message = "";
        String filePath="";
        try {
           
       
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
            MultipartFile imageFile = multipartRequest.getFile("img_url"); 
            if(null!=imageFile&&!imageFile.isEmpty()){
            	//如果fileitem中封装的是上传文件，得到上传的文件名称，
                String fileName = imageFile.getOriginalFilename();
                System.out.println(fileName+"--------------------------------------->>>>");
                //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
                //得到上传文件的扩展名
                String fileExtName = fileName.substring(fileName.lastIndexOf(".")+1);
                if("zip".equals(fileExtName)||"rar".equals(fileExtName)||"tar".equals(fileExtName)||"jar".equals(fileExtName)){
                    outDto.setAppCode(SystemCons.ERROR);
        			outDto.setAppMsg("上传文件的类型不符合！！！");
                    return outDto;
                }
                BufferedImage bi =ImageIO.read(imageFile.getInputStream());
                outDto.put("width", bi.getWidth());
                outDto.put("height", bi.getHeight());
                //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                System.out.println("上传文件的扩展名为:"+fileExtName);
                //获取item中的上传文件的输入流
                InputStream is = imageFile.getInputStream();
                //得到文件保存的名称
                fileName = mkFileName(fileName);
                //得到文件保存的路径
                String savePathStr = mkFilePath(savePath, fileName);
                System.out.println("保存路径为:"+savePathStr);
                filePath=savePathStr+File.separator+fileName;
                //创建一个文件输出流
                FileOutputStream fos = new FileOutputStream(filePath);
                //创建一个缓冲区
                byte buffer[] = new byte[1024];
                //判断输入流中的数据是否已经读完的标识
                int length = 0;
                //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                while((length = is.read(buffer))>0){
                    //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                    fos.write(buffer, 0, length);
                }
                //关闭输入流
                is.close();
                //关闭输出流
                fos.close();
                message = "文件上传成功";
                outDto.setAppCode(SystemCons.SUCCESS);
        		outDto.setAppMsg(filePath.replace(savePath, ""));
            }else{
            	 outDto.setAppCode(SystemCons.ERROR);
     			 outDto.setAppMsg("文件上传失败！！！");
            }
           
                
                    
        }catch (Exception e) {
        	e.printStackTrace();
            outDto.setAppCode(SystemCons.ERROR);
			outDto.setAppMsg("文件上传失败！！！");
            return outDto;
        }
        
        return outDto;
    }
    protected Dto base64ToFile(HttpServletRequest request, HttpServletResponse response,String base64String,Dto outDto)  {
        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = request.getServletContext().getRealPath("/myupload");
        String shortBase64Str=getShortBase64Str(base64String);
        String imageSuffix="png";
        try {
			imageSuffix=getImageSuffix(shortBase64Str);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        String filePath="";
	    String fileName = mkFileName()+"."+imageSuffix;
        String savePathStr = mkFilePath(savePath, fileName);
       
        filePath=savePathStr+File.separator+fileName;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try   
        {  
            
        	//Base64解码  
        	base64String=base64String.replace(shortBase64Str, "");
            byte[] b = decoder.decodeBuffer(base64String);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  
            //生成图片  
            out= new FileOutputStream(filePath);      
            out.write(b);  
            out.flush(); 
            outDto.setAppCode(SystemCons.SUCCESS);
    		outDto.setAppMsg(filePath.replace(savePath, ""));
        }   
        catch (Exception e)   
        {  
        	 outDto.setAppCode(SystemCons.ERROR);
			 outDto.setAppMsg("文件上传失败！！！");
        	System.out.println(e.getMessage()); 
        }finally{
        	try {
				if(null!=out){
					out.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage()); 
			}
        }
             
            
           
                
        
        
        return outDto;
    }
    public Map<String,String> getWapDto(HttpModel httpModel){
    	Dto inDto = httpModel.getInDto();
		JSONObject jsonObj = new JSONObject(inDto.getString("data"));
		Iterator<String> nameItr = jsonObj.keys();
		String name;
		Map<String, String> outMap = new HashMap<String, String>();
		while (nameItr.hasNext()) {
			name = nameItr.next();
			outMap.put(name, jsonObj.getString(name));
		}
		return outMap;
    }
    
    public void failMsg(Dto dto,String msg){
    	dto.put(AOSCons.APPCODE_KEY, AOSCons.ERROR);
		dto.put(AOSCons.APPMSG_KEY, msg);
    }
    public void sucessMsg(Dto dto,String msg){
    	dto.put(AOSCons.APPCODE_KEY, AOSCons.SUCCESS);
		dto.put(AOSCons.APPMSG_KEY, msg);
    }
    public String getShortBase64Str(String base64Str){
    	return base64Str.substring(0, base64Str.indexOf(";base64,")+8);
    }
    public String getImageSuffix(String shortBase64Str){
    	String a=shortBase64Str.substring(0, shortBase64Str.indexOf(";base64,"));
    	return a.substring(11, a.length());
    }
    public void saveLogs(String content,UserModel  userModel){
		CommonLogsPO commonLogsPO=new CommonLogsPO();
		commonLogsPO.setLog_id(AOSId.appId(SystemCons.ID.SYSTEM));
		commonLogsPO.setCreate_date(AOSUtils.getDateTime());
		commonLogsPO.setIs_del("0");
		commonLogsPO.setContent(content);
		if(null!=userModel){
			commonLogsPO.setOper_id(userModel.getId_());
			commonLogsPO.setOper_name(AOSUtils.isEmpty(userModel.getUser_name())?userModel.getAccount_():userModel.getUser_name());
		}
		commonLogsDao.insert(commonLogsPO);
	}
    public void saveLogs2(String content,HttpModel httpModel){
    	UserModel  userModel=httpModel.getUserModel();
		CommonLogsPO commonLogsPO=new CommonLogsPO();
		commonLogsPO.setLog_id(AOSId.appId(SystemCons.ID.SYSTEM));
		commonLogsPO.setCreate_date(AOSUtils.getDateTime());
		commonLogsPO.setIs_del("0");
		commonLogsPO.setContent(content);
		if(null!=userModel){
			commonLogsPO.setOper_id(userModel.getId_());
			commonLogsPO.setOper_name(AOSUtils.isEmpty(userModel.getUser_name())?userModel.getAccount_():userModel.getUser_name());
		}
		commonLogsDao.insert(commonLogsPO);
	}
    
}
