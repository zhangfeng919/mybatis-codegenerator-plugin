package ocm.zhangfeng.mybatis;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.JavaBeansUtil;

/**
 * @author zhangfeng
 * @create 2019-10-31-9:44
 **/
public class DTOJavaGenerator extends AbstractJavaGenerator {

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        String dtoPack = JavaGeneratorUtils.getDTOPack(context,introspectedTable);
        String objectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();


        TopLevelClass queryClass = createDTOClass(dtoPack,objectName);


        List answer = new ArrayList();
        answer.add(queryClass);
        return answer;
    }


    TopLevelClass createDTOClass(String pack,String objectName){
        FullyQualifiedJavaType dtoType = new FullyQualifiedJavaType(
            new StringBuffer(pack).append(".").
          append(objectName).
          append(JavaGeneratorUtils.DTO).toString());
        TopLevelClass queryClassPage = new TopLevelClass(dtoType);
        queryClassPage.setVisibility(JavaVisibility.PUBLIC);
        queryClassPage.addImportedType(new FullyQualifiedJavaType("java.io.Serializable"));
        queryClassPage.addSuperInterface(new FullyQualifiedJavaType("java.io.Serializable"));

        return queryClassPage;
    }

}
