package ocm.zhangfeng.mybatis;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

/**
 * @author zhangfeng
 * @create 2019-11-05-16:14
 **/
public class ApiJavaGenerator extends AbstractJavaGenerator {

    private final static String METHODSELECTBYID = "info";
    private final static String METHODDELETEBYID = "delete";
    private final static String METHODSELECTBYPARAM = "list";
    private final static String METHODADD = "add";
    private final static String METHODUPDATE = "update";

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List answer = new ArrayList();
        TopLevelClass serviceClass = createApiClass();
        answer.add(serviceClass);

        return answer;
    }

    TopLevelClass createApiClass() {
        String objectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();

        FullyQualifiedJavaType queryType = JavaGeneratorUtils
            .getQueryJavaType(context, introspectedTable);
        FullyQualifiedJavaType dtoType = JavaGeneratorUtils
            .getDTOJavaType(context, introspectedTable);
        FullyQualifiedJavaType serviceInterfaceType = JavaGeneratorUtils
            .getServiceJavaType(context, introspectedTable);
        FullyQualifiedJavaType objectType = new FullyQualifiedJavaType(
            introspectedTable.getBaseRecordType());

        TopLevelClass api = new TopLevelClass(JavaGeneratorUtils.getApiJavaType(context,introspectedTable));
        api.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable);
        FullyQualifiedJavaType apiListType = JavaGeneratorUtils
            .getApiListJavaType(context, introspectedTable);

        api.addImportedType(queryType);
        api.addImportedType(dtoType);
        api.addImportedType(returnType);
        api.addImportedType(apiListType);
        api.addImportedType(objectType);
        api.addImportedType("org.springframework.beans.factory.annotation.Autowired;");
        api.addImportedType(serviceInterfaceType);
        api.addImportedType("com.zhangfeng.utils.base.utils.ObjectUtil");
        api.addImportedType("org.springframework.web.bind.annotation.RequestMapping");
        api.addImportedType("org.springframework.web.bind.annotation.RestController");
        api.addImportedType("org.springframework.web.bind.annotation.RequestBody");
        api.addImportedType("org.springframework.web.bind.annotation.PostMapping");
        api.addImportedType("org.springframework.web.bind.annotation.PathVariable");
        api.addImportedType("org.springframework.web.bind.annotation.GetMapping");

        api.addAnnotation("@RestController");
        api.addAnnotation(String.format("@RequestMapping(\"/%s\")",JavaGeneratorUtils.firstCharLower(objectName)));

        addService(api, introspectedTable);

        addSelectById(api, queryType, dtoType);

        deleteById(api, queryType, dtoType);

        selectByParam(api, objectName, queryType, dtoType);

        addObject(api, objectName, objectType, dtoType);

        updateObject(api, objectName, objectType, dtoType);

        return api;
    }

    void addService(TopLevelClass api, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType serviceType = JavaGeneratorUtils.getServiceJavaType(context,introspectedTable);

        Field field = new Field();
        field.setType(serviceType);
        field.setName(JavaGeneratorUtils.firstCharLower(serviceType.getShortName()));
        field.setVisibility(JavaVisibility.PRIVATE);

        field.addAnnotation("@Autowired");

        api.addField(field);
    }

    void addSelectById(TopLevelClass service,
        FullyQualifiedJavaType queryType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODSELECTBYID);
        method.setVisibility(JavaVisibility.PUBLIC);

        method.addAnnotation("@GetMapping(\"/info/{id}\")");

        String parameterName = "id";
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("java.lang.Long"), parameterName);
        parameter.addAnnotation("@PathVariable(\"id\") ");
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        String queryName = queryType.getShortName();
        String serviceName = JavaGeneratorUtils.firstCharLower(
            JavaGeneratorUtils.getServiceJavaType(context,introspectedTable)
                .getShortName());

        method.addBodyLine(String.format("%s selectByKeyQuery = new %s();",queryName,queryName));
        method.addBodyLine("selectByKeyQuery.setId(id);");
        method.addBodyLine(String.format("return %s.selectById(selectByKeyQuery);",serviceName));

        JavaGeneratorUtils.methodAnnotation(method, "根据主键获取信息");

        service.addMethod(method);
    }

    void deleteById(TopLevelClass service,
        FullyQualifiedJavaType queryType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODDELETEBYID);
        method.setVisibility(JavaVisibility.PUBLIC);

        method.addAnnotation("@PostMapping(\"/delete/{id}\")");

        String parameterName = "id";
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("java.lang.Long"), parameterName);
        parameter.addAnnotation("@PathVariable(\"id\") ");
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        String queryName = queryType.getShortName();
        String serviceName = JavaGeneratorUtils.firstCharLower(
            JavaGeneratorUtils.getServiceJavaType(context,introspectedTable)
                .getShortName());

        method.addBodyLine(String.format("%s sysDicTypeDeleteQuery = new %s();",queryName,queryName));
        method.addBodyLine("sysDicTypeDeleteQuery.setId(id);");
        method.addBodyLine(String.format("return %s.deleteById(sysDicTypeDeleteQuery);",serviceName));

        JavaGeneratorUtils.methodAnnotation(method, "根据主键删除信息");

        service.addMethod(method);
    }


    void selectByParam(TopLevelClass service, String objectName,
        FullyQualifiedJavaType queryType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODSELECTBYPARAM);
        method.setVisibility(JavaVisibility.PUBLIC);

        method.addAnnotation("@GetMapping(\"/list\")");

        String parameterName =
            JavaGeneratorUtils.firstCharLower(objectName) + JavaGeneratorUtils.QUERY;
        Parameter parameter = new Parameter(queryType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType listType = JavaGeneratorUtils
            .getApiListJavaType(context, introspectedTable, dtoType);
        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, listType);
        method.setReturnType(returnType);

        String queryName = JavaGeneratorUtils.firstCharLower(queryType.getShortName());
        String serviceName = JavaGeneratorUtils.firstCharLower(JavaGeneratorUtils.getServiceJavaType(context,introspectedTable).getShortName());

        method.addBodyLine(String.format("return %s.selectByParam(%s);",serviceName, queryName));

        JavaGeneratorUtils.methodAnnotation(method, "根据查询条件获取记录列表");
        service.addMethod(method);
    }

    void addObject(TopLevelClass service, String objectName,
        FullyQualifiedJavaType objectType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODADD + objectName);
        method.setVisibility(JavaVisibility.PUBLIC);

        method.addAnnotation("@PostMapping(\"/add\")");

        String objectParamName =
            JavaGeneratorUtils.firstCharLower(objectName);
        Parameter parameter = new Parameter(objectType, objectParamName);
        parameter.addAnnotation("@RequestBody");
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        String serviceName = JavaGeneratorUtils.firstCharLower(JavaGeneratorUtils.getServiceJavaType(context,introspectedTable).getShortName());

        method.addBodyLine(String.format("return %s.%s(%s);",serviceName,method.getName(), objectParamName));

        JavaGeneratorUtils.methodAnnotation(method, "新增记录");
        service.addMethod(method);
    }

    void updateObject(TopLevelClass service, String objectName,
        FullyQualifiedJavaType queryType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODUPDATE + objectName);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addAnnotation("@PostMapping(\"/update\")");

        String objectParamName =
            JavaGeneratorUtils.firstCharLower(objectName);
        Parameter parameter = new Parameter(queryType, objectParamName);
        parameter.addAnnotation("@RequestBody");
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        String serviceName = JavaGeneratorUtils.firstCharLower(JavaGeneratorUtils.getServiceJavaType(context,introspectedTable).getShortName());
        method.addBodyLine(String.format("return %s.%s(%s);",serviceName,method.getName(), objectParamName));

        JavaGeneratorUtils.methodAnnotation(method, "修改记录");
        service.addMethod(method);
    }
}
