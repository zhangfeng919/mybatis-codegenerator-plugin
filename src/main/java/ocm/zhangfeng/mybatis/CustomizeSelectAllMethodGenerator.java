package ocm.zhangfeng.mybatis;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TypeParameter;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.SelectAllMethodGenerator;
import org.mybatis.generator.internal.util.JavaBeansUtil;

/**
 * @author zhangfeng
 * @create 2019-10-31-13:41
 **/
public class CustomizeSelectAllMethodGenerator extends SelectAllMethodGenerator {

    @Override
    public void addMapperAnnotations(Interface interfaze, Method method) {
        String queryPack = JavaGeneratorUtils.getQueryPack(context,introspectedTable);
        String objectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(queryPack+"."+objectName+"Query");
        Parameter parameter = new Parameter(type, JavaBeansUtil.getCamelCaseString(objectName,false) +"Query");

        method.addParameter(parameter);
        interfaze.addImportedType(type);
    }

}
