package com.project.ecommerce.service.impl;

import com.project.ecommerce.dao.TestMapper;
import com.project.ecommerce.dto.TestDto;
import com.project.ecommerce.form.TestForm;
import com.project.ecommerce.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class TestServiceImpl implements ITestService {
    @Autowired
    TestMapper testMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Override
    public void save(TestForm testForm) {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            TestDto testDto = new TestDto();
            testDto.setDescription(testForm.getDescription());
            testDto.setText(testForm.getText());
            Boolean result = testMapper.save(testDto);
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
        }
    }

    @Override
    public TestDto get() {
        TestDto testDto = testMapper.get();
        TestForm testForm = new TestForm();
        return testDto;
    }
}
