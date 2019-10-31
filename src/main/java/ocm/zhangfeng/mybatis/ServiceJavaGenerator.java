package ocm.zhangfeng.mybatis;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.JavaBeansUtil;

/**
 * @author zhangfeng
 * @create 2019-10-31-9:44
 **/
public class ServiceJavaGenerator extends AbstractJavaGenerator {


    private final static String METHODSELECTBYID = "selectById";
    private final static String METHODDELETEBYID = "deleteById";
    private final static String METHODSELECTBYPARAM = "selectByParam";
    private final static String METHODADD = "add";
    private final static String METHODUPDATE = "update";

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        Interface serviceInterface = createServiceInterface();

        List answer = new ArrayList();
        answer.add(serviceInterface);
        return answer;
    }

    Interface createServiceInterface() {
        String objectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();

        FullyQualifiedJavaType queryType = JavaGeneratorUtils
            .getQueryJavaType(context, introspectedTable, objectName);
        FullyQualifiedJavaType pageQueryType = JavaGeneratorUtils
            .getPageQueryJavaType(context, introspectedTable, objectName);
        FullyQualifiedJavaType dtoType = JavaGeneratorUtils
            .getDTOJavaType(context, introspectedTable, objectName);
        FullyQualifiedJavaType serviceType = JavaGeneratorUtils
            .getServiceJavaType(context, introspectedTable,
                objectName);
        FullyQualifiedJavaType objectType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        Interface service = new Interface(serviceType);
        service.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable);
        FullyQualifiedJavaType apiListType = JavaGeneratorUtils
            .getApiListJavaType(context, introspectedTable);

        service.addImportedType(queryType);
        service.addImportedType(dtoType);
        service.addImportedType(returnType);
        service.addImportedType(pageQueryType);
        service.addImportedType(apiListType);
        service.addImportedType(objectType);

        addSelectById(service, objectName, queryType, dtoType);

        deleteById(service, objectName, queryType, dtoType);

        selectByParam(service, objectName, pageQueryType, dtoType);

        addObject(service, objectName, objectType, dtoType);

        updateObject(service, objectName, objectType, dtoType);

        return service;
    }

    void selectByParam(Interface service, String objectName,
        FullyQualifiedJavaType queryType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODSELECTBYPARAM);
        method.setVisibility(JavaVisibility.PUBLIC);

        String parameterName =
            JavaBeansUtil.getCamelCaseString(objectName, false) + JavaGeneratorUtils.PAGEQUERY;
        Parameter parameter = new Parameter(queryType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType listType = JavaGeneratorUtils.getApiListJavaType(context,introspectedTable,dtoType);
        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, listType);
        method.setReturnType(returnType);

        JavaGeneratorUtils.methodAnnotation(method,"根据查询条件获取记录列表");
        service.addMethod(method);
    }

    void deleteById(Interface service, String objectName,
        FullyQualifiedJavaType queryType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODDELETEBYID);
        method.setVisibility(JavaVisibility.PUBLIC);

        String parameterName =
            JavaBeansUtil.getCamelCaseString(objectName, false) + JavaGeneratorUtils.QUERY;
        Parameter parameter = new Parameter(queryType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        JavaGeneratorUtils.methodAnnotation(method,"删除记录");
        service.addMethod(method);
    }

    void addSelectById(Interface service, String objectName,
        FullyQualifiedJavaType queryType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODSELECTBYID);
        method.setVisibility(JavaVisibility.PUBLIC);

        String parameterName =
            JavaBeansUtil.getCamelCaseString(objectName, false) + JavaGeneratorUtils.QUERY;
        Parameter parameter = new Parameter(queryType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        JavaGeneratorUtils.methodAnnotation(method,"根据主键获取信息");

        service.addMethod(method);
    }

    void addObject(Interface service, String objectName,
        FullyQualifiedJavaType objectType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODADD+objectName);
        method.setVisibility(JavaVisibility.PUBLIC);

        String parameterName =
            JavaBeansUtil.getCamelCaseString(objectName, false);
        Parameter parameter = new Parameter(objectType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        JavaGeneratorUtils.methodAnnotation(method,"新增记录");
        service.addMethod(method);
    }

    void updateObject(Interface service, String objectName,
        FullyQualifiedJavaType queryType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODUPDATE+objectName);
        method.setVisibility(JavaVisibility.PUBLIC);

        String parameterName =
            JavaBeansUtil.getCamelCaseString(objectName, false);
        Parameter parameter = new Parameter(queryType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        JavaGeneratorUtils.methodAnnotation(method,"修改记录");
        service.addMethod(method);
    }

}
