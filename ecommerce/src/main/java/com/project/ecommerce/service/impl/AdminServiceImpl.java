package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.CategoryMapper;
import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.form.SubCategoryForm;
import com.project.ecommerce.service.IAdminService;
import com.project.ecommerce.util.Message;
import com.project.ecommerce.util.MessageAccessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service("adminService")
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private MessageAccessor messageAccessor;

    /**
     * Add sub-category
     * @param subCategoryForm
     * @return
     */
    @Override
    public Message addCategory(SubCategoryForm subCategoryForm) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            SubCategoryDto subCategoryDto = new SubCategoryDto();
            BeanUtils.copyProperties(subCategoryForm, subCategoryDto);
            categoryMapper.addSubCategory(subCategoryDto);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_06_I, ""));
            //commit
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_06_E, ""));
            result.setSuccess(false);
        }
        return result;
    }
}
