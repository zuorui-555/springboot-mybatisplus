package zr.code.vuespringbootmybatis.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zr.code.vuespringbootmybatis.common.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    @Value("${server.port}")
    private String port;

    private static final String ip = "http://localhost";

    /**
     * 上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename(); //获取源文件名称
        //定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/src/main/resources/files/" + flag + "_" + originalFilename; //获取上传路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath); //把文件写入到上传的路径
        return Result.success(ip + ":" + port + "/files/upload/" + flag); //返回 url
    }

    /**
     * 富文本文件上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/editor/upload")
    public JSON editorUpload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename(); //获取源文件名称
        //定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/src/main/resources/files/" + flag + "_" + originalFilename; //获取上传路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath); //把文件写入到上传的路径
        String url = ip + ":" + port + "/files/" + flag;
        JSONObject json = new JSONObject();
        json.set("errno", 0);
        JSONArray arr = new JSONArray();
        JSONObject data = new JSONObject();
        arr.add(data);
        data.set("url", url);
        json.set("data", arr);
        return json;
    }

    /**
     * 下载接口
     * @param response
     * @param flag
     * @return
     */
    @GetMapping("/upload/{flag}")
    public void getFiles(HttpServletResponse response, @PathVariable String flag) {
        OutputStream os; //新建一个输出流对象
        String basePath = System.getProperty("user.dir") + "/src/main/resources/files/"; //定义文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  //获取所有的文件名称
        String avatar = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  //找到和参数一致的文件
        try {
            if (StrUtil.isNotEmpty(avatar)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(avatar, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + avatar); //通过文件路径读取文件字节流
                os = response.getOutputStream();  //通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("下载失败");
        }
    }
}
