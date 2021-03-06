package ocm.zhangfeng.mybatis.javagenerator;

import java.util.ArrayList;
import java.util.List;
import ocm.zhangfeng.mybatis.JavaGeneratorUtils;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
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
public class QueryJavaGenerator extends AbstractJavaGenerator {

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        String queryPack = JavaGeneratorUtils.getQueryPack(context,introspectedTable);
        String objectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        //TopLevelClass pageQueryClass = createPageQueryClass(queryPack,objectName);


        TopLevelClass queryClass = createQueryClass(queryPack,objectName);

        TopLevelClass baseQueryClase = createBasePageQueryClass(queryPack);

        List answer = new ArrayList();
        //answer.add(pageQueryClass);
        answer.add(queryClass);
        answer.add(baseQueryClase);
        return answer;
    }

    TopLevelClass createBasePageQueryClass(String pack){
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
            new StringBuffer(pack).append(".").
          append("PageQuery").toString());
        TopLevelClass basePageQueryClass = new TopLevelClass(type);
        basePageQueryClass.setVisibility(JavaVisibility.PUBLIC);

        Field sizeField = new Field();

        sizeField.setName("pageSize");
        sizeField.setType(FullyQualifiedJavaType.getIntInstance());
        sizeField.setVisibility(JavaVisibility.PRIVATE);

        Field noField = new Field();

        noField.setName("pageNo");
        noField.setType(FullyQualifiedJavaType.getIntInstance());
        noField.setVisibility(JavaVisibility.PRIVATE);

        basePageQueryClass.addField(sizeField);
        basePageQueryClass.addField(noField);

        basePageQueryClass.addMethod(createGetterMethod(sizeField));
        basePageQueryClass.addMethod(createSetterMethod(sizeField));
        basePageQueryClass.addMethod(createGetterMethod(noField));
        basePageQueryClass.addMethod(createSetterMethod(noField));

        return basePageQueryClass;
    }

    static Method createGetterMethod(Field field){
        Method method = new Method();
        method.setReturnType(field.getType());
        method.setName(JavaBeansUtil.getGetterMethodName(field.getName(),field.getType()));
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addBodyLine(new StringBuilder().
            append(" return ").append(field.getName()).append(";").toString());

        return method;
    }
    static Method createSetterMethod(Field field){
        Method method = new Method();
        method.setName(JavaBeansUtil.getSetterMethodName(field.getName()));
        method.setVisibility(JavaVisibility.PUBLIC);

        Parameter parameter = new Parameter(field.getType(),field.getName());
        method.addParameter(parameter);

        method.addBodyLine(new StringBuilder().
            append(" this.").append(field.getName()).append(" = ").
            append(field.getName()).append(";").toString());

        return method;
    }

    TopLevelClass createPageQueryClass(String pack,String objectName){
        FullyQualifiedJavaType pageType = new FullyQualifiedJavaType(
            new StringBuffer(pack).append(".").
          append(objectName).
          append("PageQuery").toString());
        TopLevelClass queryClassPage = new TopLevelClass(pageType);
        queryClassPage.setVisibility(JavaVisibility.PUBLIC);
        queryClassPage.setSuperClass(pack + ".PageQuery");
        return queryClassPage;
    }


    public static TopLevelClass createQueryClass(String pack,String objectName){
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
            new StringBuffer(pack).append(".").
          append(objectName).
          append("Query").toString());
        TopLevelClass queryClass = new TopLevelClass(type);
        queryClass.setVisibility(JavaVisibility.PUBLIC);
        queryClass.setSuperClass(pack + ".PageQuery");

        Field id = new Field();
        id.setName("id");
        id.setVisibility(JavaVisibility.PRIVATE);
        id.setType(new FullyQualifiedJavaType("java.lang.Long"));
        queryClass.addField(id);
        queryClass.addMethod(createSetterMethod(id));
        queryClass.addMethod(createGetterMethod(id));


        return queryClass;
    }

}
