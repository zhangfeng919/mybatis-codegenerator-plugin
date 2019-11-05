package ocm.zhangfeng.mybatis;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
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

        TopLevelClass serviceClass = createServiceClass();
        answer.add(serviceClass);

        return answer;
    }

    TopLevelClass createServiceClass() {
        String objectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();

        FullyQualifiedJavaType queryType = JavaGeneratorUtils
            .getQueryJavaType(context, introspectedTable);
        FullyQualifiedJavaType dtoType = JavaGeneratorUtils
            .getDTOJavaType(context, introspectedTable);
        FullyQualifiedJavaType serviceInterfaceType = JavaGeneratorUtils
            .getServiceJavaType(context, introspectedTable);
        FullyQualifiedJavaType serviceType = JavaGeneratorUtils
            .getServiceImplJavaType(context, introspectedTable);
        FullyQualifiedJavaType objectType = new FullyQualifiedJavaType(
            introspectedTable.getBaseRecordType());

        TopLevelClass service = new TopLevelClass(serviceType);
        service.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable);
        FullyQualifiedJavaType apiListType = JavaGeneratorUtils
            .getApiListJavaType(context, introspectedTable);

        service.addImportedType(queryType);
        service.addImportedType(dtoType);
        service.addImportedType(returnType);
        service.addImportedType(apiListType);
        service.addImportedType(objectType);
        service.addImportedType("org.springframework.beans.factory.annotation.Autowired;");
        service.addImportedType(introspectedTable.getMyBatis3JavaMapperType());
        service.addImportedType(serviceInterfaceType);
        service.addImportedType("com.zhangfeng.utils.base.utils.ObjectUtil");
        service.addImportedType("com.github.pagehelper.PageHelper");
        service.addImportedType("com.github.pagehelper.PageInfo");
        service.addImportedType("java.util.stream.Collectors");
        service.addImportedType("org.springframework.stereotype.Service");
        service.addImportedType("java.util.List");

        service.addSuperInterface(serviceInterfaceType);
        service.addAnnotation("@Service");

        addMapper(service, introspectedTable);

        addSelectById(service, objectName, queryType, dtoType);

        deleteById(service, objectName, queryType, dtoType);

        selectByParam(service, objectName, queryType, dtoType);

        addObject(service, objectName, objectType, dtoType);

        updateObject(service, objectName, objectType, dtoType);

        return service;
    }

    void addMapper(TopLevelClass service, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType mapperType = new FullyQualifiedJavaType(
            introspectedTable.getMyBatis3JavaMapperType());

        Field field = new Field();
        field.setType(mapperType);
        field.setName(JavaGeneratorUtils.firstCharLower(mapperType.getShortName()));
        field.setVisibility(JavaVisibility.PRIVATE);

        field.addAnnotation("@Autowired");

        service.addField(field);
    }

    Interface createServiceInterface() {
        String objectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();

        FullyQualifiedJavaType queryType = JavaGeneratorUtils
            .getQueryJavaType(context, introspectedTable);
//        FullyQualifiedJavaType pageQueryType = JavaGeneratorUtils
//            .getPageQueryJavaType(context, introspectedTable, objectName);
        FullyQualifiedJavaType dtoType = JavaGeneratorUtils
            .getDTOJavaType(context, introspectedTable);
        FullyQualifiedJavaType serviceType = JavaGeneratorUtils
            .getServiceJavaType(context, introspectedTable);
        FullyQualifiedJavaType objectType = new FullyQualifiedJavaType(
            introspectedTable.getBaseRecordType());

        Interface service = new Interface(serviceType);
        service.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable);
        FullyQualifiedJavaType apiListType = JavaGeneratorUtils
            .getApiListJavaType(context, introspectedTable);

        service.addImportedType(queryType);
        service.addImportedType(dtoType);
        service.addImportedType(returnType);
        //service.addImportedType(pageQueryType);
        service.addImportedType(apiListType);
        service.addImportedType(objectType);

        addSelectById(service, objectName, queryType, dtoType);

        deleteById(service, objectName, queryType, dtoType);

        selectByParam(service, objectName, queryType, dtoType);

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
            JavaGeneratorUtils.firstCharLower(objectName) + JavaGeneratorUtils.QUERY;
        Parameter parameter = new Parameter(queryType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType listType = JavaGeneratorUtils
            .getApiListJavaType(context, introspectedTable, dtoType);
        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, listType);
        method.setReturnType(returnType);

        JavaGeneratorUtils.methodAnnotation(method, "根据查询条件获取记录列表");
        service.addMethod(method);
    }

    void deleteById(Interface service, String objectName,
        FullyQualifiedJavaType queryType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODDELETEBYID);
        method.setVisibility(JavaVisibility.PUBLIC);

        String parameterName =
            JavaGeneratorUtils.firstCharLower(objectName) + JavaGeneratorUtils.QUERY;
        Parameter parameter = new Parameter(queryType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        JavaGeneratorUtils.methodAnnotation(method, "删除记录");
        service.addMethod(method);
    }

    void addSelectById(Interface service, String objectName,
        FullyQualifiedJavaType queryType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODSELECTBYID);
        method.setVisibility(JavaVisibility.PUBLIC);

        String parameterName =
            JavaGeneratorUtils.firstCharLower(objectName) + JavaGeneratorUtils.QUERY;
        Parameter parameter = new Parameter(queryType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        JavaGeneratorUtils.methodAnnotation(method, "根据主键获取信息");

        service.addMethod(method);
    }

    void addObject(Interface service, String objectName,
        FullyQualifiedJavaType objectType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODADD + objectName);
        method.setVisibility(JavaVisibility.PUBLIC);

        String parameterName =
            JavaGeneratorUtils.firstCharLower(objectName);
        Parameter parameter = new Parameter(objectType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        JavaGeneratorUtils.methodAnnotation(method, "新增记录");
        service.addMethod(method);
    }

    void updateObject(Interface service, String objectName,
        FullyQualifiedJavaType queryType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODUPDATE + objectName);
        method.setVisibility(JavaVisibility.PUBLIC);

        String parameterName =
            JavaGeneratorUtils.firstCharLower(objectName);
        Parameter parameter = new Parameter(queryType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        JavaGeneratorUtils.methodAnnotation(method, "修改记录");
        service.addMethod(method);
    }


    void addSelectById(TopLevelClass service, String objectName,
        FullyQualifiedJavaType queryType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODSELECTBYID);
        method.setVisibility(JavaVisibility.PUBLIC);

        String parameterName =
            JavaGeneratorUtils.firstCharLower(objectName) + JavaGeneratorUtils.QUERY;
        Parameter parameter = new Parameter(queryType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        String queryName = JavaGeneratorUtils.firstCharLower(
            JavaGeneratorUtils.getQueryJavaType(context, introspectedTable).getShortName());
        String mapperName = JavaGeneratorUtils.firstCharLower(
            new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType())
                .getShortName());
        String dtoName = dtoType.getShortName();

        method.addBodyLine("APIResultDTO result = new APIResultDTO();");
        method.addBodyLine(String.format("Long id = %s.getId();", queryName));
        method.addBodyLine("if(id == null || id < 1){");
        method.addBodyLine("return result.fail(\"参数有误！\");");
        method.addBodyLine("}");
        method.addBodyLine(
            String.format("%s object = %s.selectByPrimaryKey(id);", objectName, mapperName));
        method.addBodyLine(
            String.format("%s dto = ObjectUtil.parseObject(object, %s.class);", dtoName, dtoName));
        method.addBodyLine("return new APIResultDTO<>().success(dto);");

        JavaGeneratorUtils.methodAnnotation(method, "根据主键获取信息");

        service.addMethod(method);
    }

    void deleteById(TopLevelClass service, String objectName,
        FullyQualifiedJavaType queryType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODDELETEBYID);
        method.setVisibility(JavaVisibility.PUBLIC);

        String parameterName =
            JavaGeneratorUtils.firstCharLower(objectName) + JavaGeneratorUtils.QUERY;
        Parameter parameter = new Parameter(queryType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        String queryName = JavaGeneratorUtils.firstCharLower(
            JavaGeneratorUtils.getQueryJavaType(context, introspectedTable).getShortName());
        String mapperName = JavaGeneratorUtils.firstCharLower(
            new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType())
                .getShortName());

        method.addBodyLine("APIResultDTO result = new APIResultDTO();");
        method.addBodyLine(String.format("Long id = %s.getId();", queryName));
        method.addBodyLine("if(id == null || id < 1){");
        method.addBodyLine("return result.fail(\"参数有误！\");");
        method.addBodyLine("}");
        method.addBodyLine(
            String.format("%s.deleteByPrimaryKey(id);", mapperName));
        method.addBodyLine("return new APIResultDTO<>().success(\"\");");


        JavaGeneratorUtils.methodAnnotation(method, "删除记录");
        service.addMethod(method);
    }


    void selectByParam(TopLevelClass service, String objectName,
        FullyQualifiedJavaType queryType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODSELECTBYPARAM);
        method.setVisibility(JavaVisibility.PUBLIC);

        String parameterName =
            JavaGeneratorUtils.firstCharLower(objectName) + JavaGeneratorUtils.QUERY;
        Parameter parameter = new Parameter(queryType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType listType = JavaGeneratorUtils
            .getApiListJavaType(context, introspectedTable, dtoType);
        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, listType);
        method.setReturnType(returnType);

        String dtoName = dtoType.getShortName();
        String queryName = JavaGeneratorUtils.firstCharLower(queryType.getShortName());
        String mapperName = JavaGeneratorUtils.firstCharLower(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()).getShortName());

        method.addBodyLine(String.format("APIResultDTO<ApiListDTO<%s>> resultDTO = new APIResultDTO<>();",dtoName));
        method.addBodyLine(String.format("int pageNum = %s.getPageNO();",queryName));
        method.addBodyLine(String.format("int pageSize = %s.getPageSize();",queryName));
        method.addBodyLine("if(pageNum < 1 || pageSize < 1){");
        method.addBodyLine("return resultDTO.fail(\"分页参数有误！\");");
        method.addBodyLine("}");
        method.addBodyLine("PageHelper.startPage(pageNum, pageSize);");
        method.addBodyLine(String.format("List<%s> list = %s.selectByParam(%s);",objectName,mapperName,queryName));
        method.addBodyLine("PageInfo pageInfo = new PageInfo(list);");
        method.addBodyLine(String.format("List<%s> dtoList = list.stream().map(object -> ObjectUtil.parseObject(object,%s.class)).collect(Collectors.toList());",dtoName,dtoName));
        method.addBodyLine(String.format("return resultDTO.success(new ApiListDTO<%s>(pageInfo.getTotal(),dtoList));",dtoName));

        JavaGeneratorUtils.methodAnnotation(method, "根据查询条件获取记录列表");
        service.addMethod(method);
    }

    void addObject(TopLevelClass service, String objectName,
        FullyQualifiedJavaType objectType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODADD + objectName);
        method.setVisibility(JavaVisibility.PUBLIC);

        String parameterName =
            JavaGeneratorUtils.firstCharLower(objectName);
        Parameter parameter = new Parameter(objectType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        String objectParamName = JavaGeneratorUtils.firstCharLower(objectName);
        String mapperName = JavaGeneratorUtils.firstCharLower(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()).getShortName());

        method.addBodyLine("APIResultDTO result = new APIResultDTO();");
        method.addBodyLine(String.format("if(%s == null){",objectParamName));
        method.addBodyLine("return result.fail(\"参数有误！\");");
        method.addBodyLine("}");
        method.addBodyLine(String.format("%s.insert(%s);",mapperName,objectParamName));
        method.addBodyLine("return result.success();");

        JavaGeneratorUtils.methodAnnotation(method, "新增记录");
        service.addMethod(method);
    }

    void updateObject(TopLevelClass service, String objectName,
        FullyQualifiedJavaType queryType, FullyQualifiedJavaType dtoType) {
        Method method = new Method();
        method.setName(METHODUPDATE + objectName);
        method.setVisibility(JavaVisibility.PUBLIC);

        String parameterName =
            JavaGeneratorUtils.firstCharLower(objectName);
        Parameter parameter = new Parameter(queryType, parameterName);
        method.addParameter(parameter);

        FullyQualifiedJavaType returnType = JavaGeneratorUtils
            .getApiResultJavaType(context, introspectedTable, dtoType);
        method.setReturnType(returnType);

        String objectParamName = JavaGeneratorUtils.firstCharLower(objectName);
        String mapperName = JavaGeneratorUtils.firstCharLower(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()).getShortName());
        method.addBodyLine(String.format("APIResultDTO<%s> result = new APIResultDTO<>();",dtoType.getShortName()));
        method.addBodyLine(String.format("int row = %s.updateByPrimaryKey(%s);",mapperName,objectParamName));
        method.addBodyLine("if(row == 1){");
        method.addBodyLine("result.success();");
        method.addBodyLine("}");
        method.addBodyLine("return result;");

        JavaGeneratorUtils.methodAnnotation(method, "修改记录");
        service.addMethod(method);
    }
}
