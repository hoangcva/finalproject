package com.project.ecommerce.service;

import com.project.ecommerce.form.SubCategoryForm;
import com.project.ecommerce.util.Message;

public interface IAdminService {
    Message addCategory(SubCategoryForm subCategoryForm);
}
