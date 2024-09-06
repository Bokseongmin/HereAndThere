package com.mz.hat.controller;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/upload")
public class FileController {
    @Value("${upload.ck}")
    private String ck_upload_path;

    @PostMapping("/ck_upload")
    public void img_upload(MultipartHttpServletRequest multipartHttpServletRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            String ck_path = ck_upload_path + "/";
            File new_folder = new File(ck_path);

            if(!new_folder.exists() || new_folder.isFile()) {
                boolean make_folder = new_folder.mkdirs();
                if(make_folder) {
                    System.out.println("폴더 생성 완료");
                }
            }

            UUID uuid = UUID.randomUUID();
            final Map<String, MultipartFile> files = multipartHttpServletRequest.getFileMap();

            MultipartFile file_load = (MultipartFile) files.get("upload");
            String fileName = file_load.getOriginalFilename();

            final String ck_upload_path = ck_path + File.separator + uuid + "_" + fileName;

            files.get("upload").transferTo(new File(ck_upload_path));

            JsonObject outData = new JsonObject();
            outData.addProperty("uploaded", true);
            outData.addProperty("url", httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":8888/img/ck/" + uuid + "_" + fileName);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.getWriter().print(outData.toString());

        } catch (Exception e) {
            System.out.println("이미지 업로드 중 오류 발생 : " + e);
        }
    }
}
