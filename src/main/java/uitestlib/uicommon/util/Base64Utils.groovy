package uitestlib.uicommon.util

import sun.misc.BASE64Decoder
import sun.misc.BASE64Encoder

/**
 * Created by QingHuan on 2019/11/7 1:14
 */
@SuppressWarnings('restriction')
class Base64Utils {



    static String ImageToBase64ByLocal(String imgFile){
        //图片转成字节数组字符串，并进行base64编码
        InputStream inputStream
        byte[] data = null
        //读取图片字节数组
        try{
            inputStream = new FileInputStream(imgFile)
            data = new byte[inputStream.available()]
            inputStream.read(data)
            inputStream.close()
        }catch(IOException e){
            e.printStackTrace()
        }
        //对字节数组base64编码
        def encoder  = new BASE64Encoder()
        return encoder.encode(data)
    }

    /**
     * 在线图片转化成base64字符串
     * @param imgURL
     * @return
     */
    static String ImageToBase64ByOnline(String imgURL){
        ByteArrayOutputStream data =  new ByteArrayOutputStream()
        try{
            //创建url
            URL url =  new URL(imgURL)
            byte[] by = new byte[1024]
            HttpURLConnection conn = (HttpURLConnection)url.openConnection()
            conn.setRequestMethod("GET")
            conn.setConnectTimeout(5000)
            InputStream is =conn.getInputStream()
            int len = -1
            while ((len = is.read(by)) !=-1){
                data.write(by,0,len)
            }
            is.close()
        }catch(IOException e){
            e.printStackTrace()
        }
        def encoder = new BASE64Encoder()
        return encoder.encode(data.toByteArray())
    }



    static boolean Base64ToImage(String imgStr,String imgFilePath){
        //对字节数组字符串进行base64解码并生成图片
        if (imgStr){
            return false
        }
        BASE64Decoder decoder = new BASE64Decoder()
        try{
            byte[] b = decoder.decodeBuffer(imgStr)
            for (i in b){
                if (i<0){
                    i+=256
                }
            }
            OutputStream out = new FileOutputStream(imgFilePath)
            out.write(b)
            out.flush()
            out.close()
            return true
        }catch(Exception e){
            return false
        }
    

    }

}
