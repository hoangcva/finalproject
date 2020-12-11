package com.project.ecommerce.controller;

import com.project.ecommerce.dto.ProductImageDto;
import com.project.ecommerce.form.DisplayProductImageForm;
import com.project.ecommerce.form.MyUploadForm;
import com.project.ecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MyFileUploadController {
    @Autowired
    private IProductService productService;

    @RequestMapping(value = "/")
    public String homePage() {

        return "index";
    }

    // GET: Hiển thị trang form upload
    @RequestMapping(value = "/uploadOneFile", method = RequestMethod.GET)
    public String uploadOneFileHandler(Model model) {

        MyUploadForm myUploadForm = new MyUploadForm();
        model.addAttribute("myUploadForm", myUploadForm);

        return "uploadOneFile";
    }

    // POST: Sử lý Upload
    @RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)
    public String uploadOneFileHandlerPOST(HttpServletRequest request,
                                           Model model,
                                           @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {

        return this.doUpload(request, model, myUploadForm);

    }

    // GET: Hiển thị trang form upload
    @RequestMapping(value = "/uploadImage", method = RequestMethod.GET)
    public String uploadMultiFileHandler(Model model) {
        MyUploadForm myUploadForm = new MyUploadForm();
        model.addAttribute("myUploadForm", myUploadForm);
        return "vendor/uploadProductImage";
    }

    // POST: Sử lý Upload
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public String uploadMultiFileHandlerPOST(HttpServletRequest request,
                                             Model model,
                                             @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {
        return this.doUpload(request, model, myUploadForm);
    }

    private String doUpload(HttpServletRequest request, Model model,
                            MyUploadForm myUploadForm) {

        // Thư mục gốc upload file.
        String uploadRootPath = request.getServletContext().getRealPath("upload");

        File uploadRootDir = new File(uploadRootPath);
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        MultipartFile[] imageFiles = myUploadForm.getUploadFiles();
        //
        List<File> uploadedFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<String>();
        List<ProductImageDto> productImageDtoList = new ArrayList<>();

        for (MultipartFile image : imageFiles) {

            // Tên file gốc tại Client.
            String name = StringUtils.cleanPath(image.getOriginalFilename());

            if (name != null && name.length() > 0) {
                try {
                    ProductImageDto productImage = new ProductImageDto();
                    productImage.setName(name);
                    productImage.setContent(image.getBytes());
                    productImage.setProductId(1);
                    productImageDtoList.add(productImage);
//                    // Tạo file tại Server.
//                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
//
//                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
//                    stream.write(image.getBytes());
//                    stream.close();
//                    //
//                    uploadedFiles.add(serverFile);
                } catch (Exception e) {
                    failedFiles.add(name);
                }
            }
        }
        productService.saveProductImage(productImageDtoList);
        model.addAttribute("uploadedFiles", uploadedFiles);
        model.addAttribute("failedFiles", failedFiles);
        return "uploadResult";
    }

    @GetMapping(value = "/getProductImage")
//    public String getProductImage(@ModelAttribute("productId") long productId, Model model) {
    public String getProductImage(Model model) {
        List<DisplayProductImageForm> productImageFormList = new ArrayList<>();
        productImageFormList = productService.getProductImage(1);
        model.addAttribute("productImages", productImageFormList);
        return "displayImage";
    }
}
