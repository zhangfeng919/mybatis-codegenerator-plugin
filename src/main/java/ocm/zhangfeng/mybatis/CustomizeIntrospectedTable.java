package ocm.zhangfeng.mybatis;

import java.util.List;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;

/**
 * @author zhangfeng
 * @create 2019-10-30-16:58
 **/
public class CustomizeIntrospectedTable extends IntrospectedTableMyBatis3Impl {

    @Override
    public String getSelectAllStatementId() {
        return "selectByParam";
    }

    @Override
    protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator,
        List<String> warnings, ProgressCallback progressCallback) {
        xmlMapperGenerator = new CustmizeXMLMapperGenerator();
        initializeAbstractGenerator(xmlMapperGenerator, warnings,
            progressCallback);
    }

    @Override
    protected AbstractJavaClientGenerator createJavaClientGenerator() {
        return new CustomizeJavaGenerator();
    }
}
